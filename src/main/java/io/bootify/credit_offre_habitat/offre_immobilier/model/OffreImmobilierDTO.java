package io.bootify.credit_offre_habitat.offre_immobilier.model;

import io.bootify.credit_offre_habitat.offre_immobilier.domain.enums.*;
import io.bootify.credit_offre_habitat.offre_immobilier.model.image.ImageDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;


@Getter
@Setter
public class OffreImmobilierDTO {

    private Long id;

    @NotNull
    private TypeBien typeBien;

    @NotNull
    @Size(max = 255)
    private String adresse;

    @NotNull
    @Size(max = 255)
    private String prix;

    private String description;

    private Long listFavoris;

    private List<String> imageUrls;

    private StatutPropriete statutPropriete;

    private Ameublement ameublement;

    private Chambres chambres;

    private SallesDeBain sallesDeBain;

    private int surface;

    private AgePropriete agePropriete;
}
