package co.com.sl.inversiones.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name="investortypes")
public class InvestorType implements Serializable {
    @Id
    @Column(name = "InvestorTypeId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer investorTypeId;
    @Column(name = "Name")
    private String name;
    @Column(name = "MonthlyIncome")
    private String monthlyIncome;
}
