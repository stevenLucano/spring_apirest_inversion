package co.com.sl.inversiones.controller;

import co.com.sl.inversiones.model.dto.InvestmentDto;
import co.com.sl.inversiones.model.entity.Customer;
import co.com.sl.inversiones.model.entity.Investment;
import co.com.sl.inversiones.model.entity.Project;
import co.com.sl.inversiones.model.util.ResponseMessage;
import co.com.sl.inversiones.service.ICustomerService;
import co.com.sl.inversiones.service.IInvestmentService;
import co.com.sl.inversiones.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class InvestmentController {

    @Autowired
    IInvestmentService investmentService;

    @Autowired
    ICustomerService customerService;

    @Autowired
    IProjectService projectService;

    @GetMapping("investments")
    public ResponseEntity<?> showAll() {
        List<Investment> listInvestments = investmentService.listAll();

        if (listInvestments == null || listInvestments.isEmpty()) {
            return new ResponseEntity<>(
                    ResponseMessage.builder()
                            .statusCode("01")
                            .message("There are not investments saved.")
                            .result(null)
                            .build(),
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                ResponseMessage.builder()
                        .statusCode("00")
                        .message("")
                        .result(listInvestments)
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping("investment")
    public ResponseEntity<?> doInvestment(@RequestBody InvestmentDto investmentDto) {
        Investment investmentCreated = null;
        Customer customerFind = null;
        Project projectFind = null;
        BigDecimal investmentValue = null;

        // Valida que el value sea un número
        try {
            investmentValue = new BigDecimal(investmentDto.getValue());
        } catch (Exception ex) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .statusCode("07")
                    .message("The value must be a number")
                    .result(null)
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            // Se valida si existe el cliente
            customerFind = customerService.findById(investmentDto.getCustomerId());
            if(customerFind == null) {
                return new ResponseEntity<>(ResponseMessage.builder()
                        .statusCode("05")
                        .message("Customer with ID [" + investmentDto.getCustomerId() + "] doesn't exist.")
                        .result(null)
                        .build(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                investmentDto.setCustomerObj(customerFind);
            }

            // Se valida si existe el proyecto
            projectFind = projectService.findById(investmentDto.getProjectId());
            if(projectFind == null) {
                return new ResponseEntity<>(ResponseMessage.builder()
                        .statusCode("05")
                        .message("Project with ID [" + investmentDto.getProjectId() + "] doesn't exist.")
                        .result(null)
                        .build(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                investmentDto.setProjectObj(projectFind);
            }

            updateCustomerIncome(customerFind, investmentValue);

            // Se guarda la inversion creada
            investmentCreated = investmentService.save(investmentDto);
            InvestmentDto newInvestmentDto = InvestmentDto.builder()
                    .investmentId(investmentCreated.getInvestmentId())
                    .customerId(investmentCreated.getCustomerId().getCustomerId())
                    .projectId(investmentCreated.getProjectId().getProjectId())
                    .value(investmentCreated.getValue())
                    .build();

            return new ResponseEntity<>(ResponseMessage.builder()
                    .statusCode("00")
                    .message("Investment made correctly!.")
                    .result(newInvestmentDto)
                    .build(),
                    HttpStatus.OK);

        } catch (DataAccessException ex) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .statusCode("02")
                    .message("An error occurred.")
                    .result(null)
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(ResponseMessage.builder()
                    .statusCode("08")
                    .message(ex.getMessage())
                    .result(null)
                    .build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void updateCustomerIncome (Customer customerFind, BigDecimal investmentValue) throws Exception {
        // Valida si el cliente tiene un tipo de inversor asignado
        if (customerFind.getInvestorType() == null) {
            throw new Exception("The customer [" + customerFind.getCustomerId() + "] doesn't have investor type assigned.");
        }

        BigDecimal incomeAvailable = new BigDecimal(customerFind.getAvailableIncome());

        // Se calcula el máximo disponible a invertir del usuario
        // Disponible - (1 - permitido por tipo de inversor)
        // 20% -> Basico (1) | 25% -> Completo (2)
        incomeAvailable = incomeAvailable
                .subtract(new BigDecimal(customerFind.getInvestorType().getMonthlyIncome())
                                .multiply(BigDecimal.valueOf(
                                            customerFind.getInvestorType().getInvestorTypeId() == 1 ?
                                            (1 - 0.2) :
                                            (1 - 0.25)
                                        )
                                )
                )
                .setScale(1, RoundingMode.HALF_UP);

        // Valida que el valor de la inversión no sea mayor que el disponible a invertir
        // de acuerdo al tipo de inversor
        if (incomeAvailable.compareTo(BigDecimal.ZERO) == 0) {
            throw new Exception("The customer doesn't have enough income to invest.");
        } else if (incomeAvailable.compareTo(investmentValue) < 0) {
            throw new Exception("The investment value can't be greater than " + String.valueOf(incomeAvailable));
        }

        BigDecimal newCustomerIncome = new BigDecimal(customerFind.getAvailableIncome());
        newCustomerIncome = newCustomerIncome.subtract(investmentValue);
        customerFind.setAvailableIncome(String.valueOf(newCustomerIncome));

    }
}
