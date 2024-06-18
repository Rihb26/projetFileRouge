package io.bootify.credit_offre_habitat.offre_immobilier.model;

import io.bootify.credit_offre_habitat.offre_immobilier.domain.enums.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OffreImmobilierResponseDTO {
    private OffreImmobilierDTO offreImmobilier;
    private TypeBien[] typeBienOptions;
    private Ameublement[] ameublementOptions;
    private StatutPropriete[] statutProprieteOptions;
    private Chambres[] chambresOptions;
    private SallesDeBain[] sallesDeBainOptions;
    private AgePropriete[] ageProprieteOptions;
}
