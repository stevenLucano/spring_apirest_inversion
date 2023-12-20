package co.com.sl.inversiones.service;

import co.com.sl.inversiones.model.dto.InvestmentDto;
import co.com.sl.inversiones.model.entity.Investment;

import java.util.List;

public interface IInvestmentService {
    List<Investment> listAll();
    Investment save(InvestmentDto investmentDto);
}
