package co.com.sl.inversiones.service;

import co.com.sl.inversiones.model.dto.CustomerDto;
import co.com.sl.inversiones.model.entity.Customer;

import java.util.List;

public interface ICustomerService {
    List<Customer> listAll();
    Customer save(CustomerDto customerDto);
    Customer findById(Integer id);
    void delete(Customer customer);
    boolean existsById(Integer id);
}
