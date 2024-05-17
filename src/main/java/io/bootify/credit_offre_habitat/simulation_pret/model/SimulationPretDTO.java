package io.bootify.credit_offre_habitat.simulation_pret.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SimulationPretDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String resultat;

    private Long offreImmobilier;

    private Long user;

    private Long historiqueSimulation;

    private Long envoieDedemande;

}
