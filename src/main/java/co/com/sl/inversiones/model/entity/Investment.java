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

    @ManyToOne
    @JoinColumn(name = "CustomerId", referencedColumnName = "CustomerId")
    private Customer customerId;

    @ManyToOne
    @JoinColumn(name = "ProjectId", referencedColumnName = "ProjectId")
    private Project projectId;

    @Column(name = "Value")
    private String value;
}
