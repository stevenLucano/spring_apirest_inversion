package co.com.sl.inversiones.controller;

import co.com.sl.inversiones.model.dto.CustomerDto;
import co.com.sl.inversiones.model.entity.Customer;
import co.com.sl.inversiones.model.entity.InvestorType;
import co.com.sl.inversiones.model.util.ResponseMessage;
import co.com.sl.inversiones.service.ICustomerService;
import co.com.sl.inversiones.service.IInvestorTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IInvestorTypeService investorTypeService;

    @GetMapping("customers")
    public ResponseEntity<?> showAll() {
        List<Customer> listCustomers = customerService.listAll();

        if (listCustomers == null || listCustomers.isEmpty()) {
            return new ResponseEntity<>(
                    ResponseMessage.builder()
                            .statusCode("01")
                            .message("There are not customers saved.")
                            .result(null)
                            .build(),
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                ResponseMessage.builder()
                        .statusCode("00")
                        .message("")
                        .result(listCustomers)
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping("customer")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerDto customerDto) {
        Customer newCustomer = null;
        try {
            newCustomer = customerService.save(customerDto);
            CustomerDto newCustomerDto = CustomerDto.builder()
                    .customerId(newCustomer.getCustomerId())
                    .names(newCustomer.getNames())
                    .surnames(newCustomer.getSurnames())
                    .age(newCustomer.getAge())
                    .availableIncome(newCustomer.getAvailableIncome())
                    .build();

            return new ResponseEntity<>(ResponseMessage.builder()
                    .statusCode("00")
                    .message("Customer created.")
                    .result(newCustomerDto)
                    .build(),
                    HttpStatus.OK);
        } catch (DataAccessException ex) {
            return new ResponseEntity<>(
                    ResponseMessage.builder()
                            .statusCode("02")
                            .message(ex.getMessage())
                            .result(null)
                            .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

    }

    @PutMapping("customer/{id}")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerDto customerDto, @PathVariable Integer id) {
        Customer customerUpdate = null;

        try {
            // Valida que el ID de la URL y el enviado coincidan
            if (customerDto.getCustomerId() != null && !String.valueOf(customerDto.getCustomerId()).equals(String.valueOf(id))) {
                return new ResponseEntity<>(
                        ResponseMessage.builder()
                                .statusCode("04")
                                .message("The ID sent in the URL doesn't match with the ID in the request body.")
                                .result(null)
                                .build(),
                        HttpStatus.NOT_FOUND
                );
            }
            System.out.println("id a buscar : " + id);
            // Valida que el usuario exista en base de datos
            Customer oldCustomer = customerService.findById(customerDto.getCustomerId());

            if (oldCustomer == null) {
                return new ResponseEntity<>(
                        ResponseMessage.builder()
                                .statusCode("03")
                                .message("The customer with id [" + id + "] doesn't exist.")
                                .result(null)
                                .build(),
                        HttpStatus.NOT_FOUND
                );
            }

            // Valida que solo actualice el tipo de inversionista
            if (!validateDataCustomer(oldCustomer, customerDto)) {
                return new ResponseEntity<>(
                        ResponseMessage.builder()
                                .statusCode("04")
                                .message("Only InvestorType is allowed to be updated")
                                .result(null)
                                .build(),
                        HttpStatus.METHOD_NOT_ALLOWED
                );
            } else {
                // Actualiza customerDto
                customerDto.setNames(oldCustomer.getNames());
                customerDto.setSurnames(oldCustomer.getSurnames());
                customerDto.setAge(oldCustomer.getAge());
            }

            InvestorType investorType = null;
            try {
                investorType = investorTypeService.findById(customerDto.getInvestorTypeId());
                if(investorType == null) {
                    return new ResponseEntity<>(ResponseMessage.builder()
                            .statusCode("05")
                            .message("Investor Type [" + customerDto.getInvestorTypeId() + "] doesn't exist.")
                            .result(null)
                            .build(),
                            HttpStatus.INTERNAL_SERVER_ERROR);
                } else {
                    customerDto.setInvestorTypeObj(investorType);
                }
            } catch (Exception ex) {
                return new ResponseEntity<>(ResponseMessage.builder()
                        .statusCode("06")
                        .message("An error occurred.")
                        .result(null)
                        .build(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }

            customerUpdate = customerService.save(customerDto);
            CustomerDto newCustomerDto = CustomerDto.builder()
                    .customerId(customerUpdate.getCustomerId())
                    .names(customerUpdate.getNames())
                    .surnames(customerUpdate.getSurnames())
                    .age(customerUpdate.getAge())
                    .availableIncome(customerUpdate.getAvailableIncome())
                    .investorTypeId(customerUpdate.getInvestorType().getInvestorTypeId())
                    .build();

            return new ResponseEntity<>(ResponseMessage.builder()
                    .statusCode("00")
                    .message("Customer updated.")
                    .result(newCustomerDto)
                    .build(),
                    HttpStatus.OK);

        } catch (DataAccessException ex) {
            return new ResponseEntity<>(
                    ResponseMessage.builder()
                            .statusCode("02")
                            .message(ex.getMessage())
                            .result(null)
                            .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean validateDataCustomer(Customer oldCostumer, CustomerDto newCostumer) {
        System.out.println("oldCostumer : " + oldCostumer.toString());
        System.out.println("newCostumer : " + newCostumer.toString());
        return (newCostumer.getNames() == null || newCostumer.getNames().equals(oldCostumer.getNames()))
                && (newCostumer.getSurnames() == null || newCostumer.getSurnames().equals(oldCostumer.getSurnames()))
                && (newCostumer.getAge() == null || newCostumer.getAge().equals(oldCostumer.getAge()))
                && (newCostumer.getAvailableIncome() == null); // El valor availableIncome solo cambia mediante lógica interna
    }
}
