package co.com.sl.inversiones.model.dao;

import co.com.sl.inversiones.model.entity.Investment;
import org.springframework.data.repository.CrudRepository;

public interface InvestmentDao extends CrudRepository<Investment, Integer> {
}
