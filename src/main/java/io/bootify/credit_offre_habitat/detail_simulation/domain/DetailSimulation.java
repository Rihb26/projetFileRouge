package io.bootify.credit_offre_habitat.detail_simulation.domain;

import io.bootify.credit_offre_habitat.simulation_pret.domain.SimulationPret;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "DetailSimulations")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class DetailSimulation {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column
    private String prixDeBien;

    @Column
    private String dureeDePret;

    @Column
    private String mensualite;

    @Column
    private String tauxAnnuelEffectifGlobal;

    @Column
    private String montantTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "simulation_prets_id")
    private SimulationPret simulationPrets;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
