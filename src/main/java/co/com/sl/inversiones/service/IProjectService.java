package co.com.sl.inversiones.service;

import co.com.sl.inversiones.model.entity.Project;

import java.util.List;

public interface IProjectService {
    List<Project> listAll();
    Project findById(Integer id);
}
