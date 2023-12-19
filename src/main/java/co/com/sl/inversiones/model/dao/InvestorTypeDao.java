package co.com.sl.inversiones.model.dao;

import co.com.sl.inversiones.model.entity.InvestorType;
import org.springframework.data.repository.CrudRepository;

public interface InvestorTypeDao extends CrudRepository<InvestorType, Integer> {
}
