package io.bootify.credit_offre_habitat.offre_immobilier.domain.image;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.bootify.credit_offre_habitat.offre_immobilier.domain.OffreImmobilier;
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
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "Images")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Image {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "image_sequence",
            sequenceName = "image_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "image_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offre_immobilier_id", nullable = false)
    @JsonBackReference
    private OffreImmobilier offreImmobilier;

}

