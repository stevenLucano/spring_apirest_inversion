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
@Table(name = "investments")
public class Investment implements Serializable {
    @Id
    @Column(name = "InvestmentId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer investmentId;
    @Column(name = "CustomerId")
    private Integer customerId;
    @Column(name = "ProjectId")
    private Integer projectId;
    @Column(name = "Value")
    private String value;
}
