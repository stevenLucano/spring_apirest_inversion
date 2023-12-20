package co.com.sl.inversiones.controller;

import co.com.sl.inversiones.model.entity.Project;
import co.com.sl.inversiones.model.util.ResponseMessage;
import co.com.sl.inversiones.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProjectController {

    @Autowired
    IProjectService projectService;

    @GetMapping("projects")
    public ResponseEntity<?> showAll() {
        List<Project> listProjects = projectService.listAll();
        if (listProjects == null || listProjects.isEmpty()) {
            return new ResponseEntity<>(
                    ResponseMessage.builder()
                            .statusCode("01")
                            .message("There are not projects saved.")
                            .result(null)
                            .build(),
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                ResponseMessage.builder()
                        .statusCode("00")
                        .message("")
                        .result(listProjects)
                        .build(),
                HttpStatus.OK
        );
    }
}
