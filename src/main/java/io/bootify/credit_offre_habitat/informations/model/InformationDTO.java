package io.bootify.credit_offre_habitat.informations.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InformationDTO {

    private Long id;

    private LocalDateTime dateDePublication;

    @NotNull
    @Size(max = 255)
    private String contenu;

    @NotNull
    @Size(max = 255)
    private String titre;

    @NotNull
    @Size(max = 255)
    private String auteur;

    private Long user;

    private String imageUrl;

}
