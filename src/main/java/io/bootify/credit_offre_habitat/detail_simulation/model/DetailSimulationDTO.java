package io.bootify.credit_offre_habitat.detail_simulation.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DetailSimulationDTO {

    private Long id;

    @Size(max = 255)
    private String prixDeBien;

    @Size(max = 255)
    private String dureeDePret;

    @Size(max = 255)
    private String mensualite;

    @Size(max = 255)
    private String tauxAnnuelEffectifGlobal;

    @Size(max = 255)
    private String montantTotal;

    private Long simulationPrets;

}
