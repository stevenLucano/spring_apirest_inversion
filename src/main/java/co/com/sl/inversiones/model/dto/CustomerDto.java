package co.com.sl.inversiones.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class CustomerDto {
    private Integer customerId;
    private String names;
    private String surnames;
    private Integer age;
    private Double monthlyIncome;
    private Integer InvestorTypeId;
    private Integer projectId;
}
