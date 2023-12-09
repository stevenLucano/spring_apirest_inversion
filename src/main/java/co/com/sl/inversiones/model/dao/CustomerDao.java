package co.com.sl.inversiones.model.dao;

import co.com.sl.inversiones.model.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerDao extends CrudRepository<Customer, Integer> {
}
