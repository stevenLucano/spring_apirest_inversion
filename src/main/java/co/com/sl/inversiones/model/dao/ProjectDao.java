package co.com.sl.inversiones.model.dao;

import co.com.sl.inversiones.model.entity.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectDao extends CrudRepository<Project, Integer> {
}
