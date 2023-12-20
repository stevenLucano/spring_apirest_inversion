package co.com.sl.inversiones.model.dto;

import co.com.sl.inversiones.model.entity.Customer;
import co.com.sl.inversiones.model.entity.Project;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@Builder
public class InvestmentDto implements Serializable {
    private Integer investmentId;
    private Integer customerId;
    private Integer projectId;
    private String value;

    @JsonIgnore
    private Customer customerObj;
    @JsonIgnore
    private Project projectObj;
}
