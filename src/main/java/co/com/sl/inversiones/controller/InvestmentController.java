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
        }
    }
}
