package io.bootify.credit_offre_habitat.simulation_pret.domain;

import io.bootify.credit_offre_habitat.detail_simulation.domain.DetailSimulation;
import io.bootify.credit_offre_habitat.envoie_dedemande.domain.EnvoieDedemande;
import io.bootify.credit_offre_habitat.historique_simulation.domain.HistoriqueSimulation;
import io.bootify.credit_offre_habitat.offre_immobilier.domain.OffreImmobilier;
import io.bootify.credit_offre_habitat.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "SimulationPrets")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class SimulationPret {

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

    @Column(nullable = false)
    private String resultat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offre_immobilier_id")
    private OffreImmobilier offreImmobilier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "simulationPrets")
    private Set<DetailSimulation> detailSimulations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "historique_simulation_id")
    private HistoriqueSimulation historiqueSimulation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "envoie_dedemande_id")
    private EnvoieDedemande envoieDedemande;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;
}
