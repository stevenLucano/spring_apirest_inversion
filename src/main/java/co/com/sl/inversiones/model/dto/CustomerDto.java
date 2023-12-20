package co.com.sl.inversiones.model.dto;

import co.com.sl.inversiones.model.entity.InvestorType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@Builder
public class CustomerDto implements Serializable {
    private Integer customerId;
    @Schema(description = "Juanito Perez")
    private String names;
    private String surnames;
    private Integer age;
    private String availableIncome;
    private Integer investorTypeId;
    @JsonIgnore
    private InvestorType investorTypeObj;
}
