package co.com.sl.inversiones.controller;

import co.com.sl.inversiones.model.entity.Customer;
import co.com.sl.inversiones.model.util.ResponseMessage;
import co.com.sl.inversiones.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @GetMapping("customers")
    public ResponseEntity<?> showAll() {
        List<Customer> listCustomers = customerService.listAll();

        if(listCustomers == null || listCustomers.isEmpty()) {
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

}
