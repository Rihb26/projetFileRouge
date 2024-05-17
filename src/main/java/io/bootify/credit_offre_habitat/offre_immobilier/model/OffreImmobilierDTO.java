package io.bootify.credit_offre_habitat.offre_immobilier.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OffreImmobilierDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String typeBien;

    @NotNull
    @Size(max = 255)
    private String adresse;

    @NotNull
    @Size(max = 255)
    private String prix;

    @Size(max = 255)
    private String description;

    private Long listFavoris;

}
