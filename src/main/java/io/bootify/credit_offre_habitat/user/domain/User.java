package io.bootify.credit_offre_habitat.user.domain;

import io.bootify.credit_offre_habitat.envoie_dedemande.domain.EnvoieDedemande;
import io.bootify.credit_offre_habitat.historique_simulation.domain.HistoriqueSimulation;
import io.bootify.credit_offre_habitat.nouveaute_bnaque.domain.NouveauteBnaque;
import io.bootify.credit_offre_habitat.offre_immobilier.domain.OffreImmobilier;
import io.bootify.credit_offre_habitat.simulation_pret.domain.SimulationPret;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "Users")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class User {

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
    private String nom;

    @Column
    private String prenom;

    @Column
    private String email;

    @Column
    private String motDePasse;

    @Column
    private Integer numeroTelephone;

    @Column
    private String adresse;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> nouveauteBanque;

    @OneToMany(mappedBy = "user")
    private Set<NouveauteBnaque> nouveauteBnaques;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_favoris",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "offre_id")
    )
    private Set<OffreImmobilier> favoris = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<SimulationPret> simulationPrets;

    @OneToMany(mappedBy = "user")
    private Set<HistoriqueSimulation> historiqueSimulations;

    @OneToMany(mappedBy = "user")
    private Set<EnvoieDedemande> envoieDedemandes;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}