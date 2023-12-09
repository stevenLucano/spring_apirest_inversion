package co.com.sl.inversiones.service.imp;

import co.com.sl.inversiones.model.dao.CustomerDao;
import co.com.sl.inversiones.model.dto.CustomerDto;
import co.com.sl.inversiones.model.entity.Customer;
import co.com.sl.inversiones.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerImpService implements ICustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Override
    public List<Customer> listAll() {
        return (List) customerDao.findAll();
    }

    @Override
    public Customer save(CustomerDto customer) {
        return null;
    }

    @Override
    public Customer findById(Integer id) {
        return null;
    }

    @Override
    public void delete(Customer customer) {

    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }
}
