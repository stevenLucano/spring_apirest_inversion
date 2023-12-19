package co.com.sl.inversiones.service.imp;

import co.com.sl.inversiones.model.dao.CustomerDao;
import co.com.sl.inversiones.model.dto.CustomerDto;
import co.com.sl.inversiones.model.entity.Customer;
import co.com.sl.inversiones.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerImpService implements ICustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Override
    public List<Customer> listAll() {
        return (List) customerDao.findAll();
    }

    @Transactional
    @Override
    public Customer save(CustomerDto customerDto) {
        Customer customer = Customer.builder()
                .customerId(customerDto.getCustomerId())
                .names(customerDto.getNames())
                .surnames(customerDto.getSurnames())
                .age(customerDto.getAge())
                .availableIncome(customerDto.getInvestorTypeObj().getMonthlyIncome())
                .investorType(customerDto.getInvestorTypeObj())
                .build();
        return customerDao.save(customer);
    }

    @Transactional(readOnly = true)
    @Override
    public Customer findById(Integer id) {
        return customerDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(Customer customer) {
        customerDao.delete(customer);
    }

    @Override
    public boolean existsById(Integer id) {
        return customerDao.existsById(id);
    }
}
