package co.com.sl.inversiones.service.imp;

import co.com.sl.inversiones.model.dao.ProjectDao;
import co.com.sl.inversiones.model.entity.Project;
import co.com.sl.inversiones.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectImpService implements IProjectService {
    @Autowired
    ProjectDao projectDao;

    @Override
    public List<Project> listAll() {
        return (List<Project>) projectDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Project findById(Integer id) {
        return projectDao.findById(id).orElse(null);
    }
}
