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
@Table(name="customers")
public class Customer implements Serializable {
    @Id
    @Column(name = "CustomerId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;
    @Column(name = "Names")
    private String names;
    @Column(name = "Surnames")
    private String surnames;
    @Column(name = "Age")
    private Integer age;
    @Column(name = "AvailableIncome")
    private String availableIncome;
    /*@Column(name = "InvestorTypeId")
    private Integer investorTypeId;*/

    @ManyToOne
    @JoinColumn(name = "InvestorTypeId")
    private InvestorType investorType;
}
