package co.com.sl.inversiones.service.imp;

import co.com.sl.inversiones.model.dao.InvestmentDao;
import co.com.sl.inversiones.model.dto.InvestmentDto;
import co.com.sl.inversiones.model.entity.Investment;
import co.com.sl.inversiones.service.IInvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InvestmentImpService implements IInvestmentService {

    @Autowired
    InvestmentDao investmentDao;
    @Override
    public List<Investment> listAll() {
        return (List<Investment>) investmentDao.findAll();
    }

    @Transactional
    @Override
    public Investment save(InvestmentDto investmentDto) {
        Investment investment = Investment.builder()
                .investmentId(investmentDto.getInvestmentId())
                .customerId(investmentDto.getCustomerObj())
                .projectId(investmentDto.getProjectObj())
                .value(investmentDto.getValue())
                .build();
        return investmentDao.save(investment);
    }
}
