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
@Table(name="projects")
public class Project implements Serializable {
    @Id
    @Column(name = "ProjectId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectId;
    @Column(name = "Name")
    private String name;
    @Column(name = "Goal")
    private String goal;
    @Column(name = "AvailableAmount")
    private String availableAmount;
    @Column(name = "Status")
    private String status;
}
