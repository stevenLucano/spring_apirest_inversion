package co.com.sl.inversiones.service.imp;

import co.com.sl.inversiones.model.dao.InvestorTypeDao;
import co.com.sl.inversiones.model.entity.InvestorType;
import co.com.sl.inversiones.service.IInvestorTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvestorTypeImpService implements IInvestorTypeService {
    @Autowired
    private InvestorTypeDao investorTypeDao;

    @Transactional(readOnly = true)
    @Override
    public InvestorType findById(Integer id) {
        return investorTypeDao.findById(id).orElse(null);
    }
}
