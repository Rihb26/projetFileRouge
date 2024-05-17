package io.bootify.credit_offre_habitat.envoie_dedemande.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EnvoieDedemandeDTO {

    private Long id;

    private LocalDateTime dateDeDemande;

    @Size(max = 255)
    private String statue;

    private Long user;

}
