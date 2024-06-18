package io.bootify.credit_offre_habitat.offre_immobilier.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.bootify.credit_offre_habitat.offre_immobilier.domain.enums.*;
import io.bootify.credit_offre_habitat.offre_immobilier.domain.image.Image;
import io.bootify.credit_offre_habitat.simulation_pret.domain.SimulationPret;
import io.bootify.credit_offre_habitat.user.domain.User;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "OffreImmobiliers")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class OffreImmobilier {

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeBien typeBien;

    @Column(nullable = false)
    private String adresse;

    @Column(nullable = false)
    private String prix;

    @Column(columnDefinition ="text")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutPropriete statutPropriete;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Ameublement ameublement;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Chambres chambres;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SallesDeBain sallesDeBain;

    @Column(nullable = false)
    private int surface;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AgePropriete agePropriete;

    @JsonIgnore
    @ManyToMany(mappedBy = "favoris")
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "offreImmobilier",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SimulationPret> simulationPrets;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    @OneToMany(mappedBy = "offreImmobilier", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Image> images = new HashSet<>();

}
