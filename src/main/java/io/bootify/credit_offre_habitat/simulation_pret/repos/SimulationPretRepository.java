package io.bootify.credit_offre_habitat.simulation_pret.repos;

import io.bootify.credit_offre_habitat.envoie_dedemande.domain.EnvoieDedemande;
import io.bootify.credit_offre_habitat.offre_immobilier.domain.OffreImmobilier;
import io.bootify.credit_offre_habitat.simulation_pret.domain.SimulationPret;
import io.bootify.credit_offre_habitat.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SimulationPretRepository extends JpaRepository<SimulationPret, Long> {

    SimulationPret findFirstByOffreImmobilier(OffreImmobilier offreImmobilier);

    SimulationPret findFirstByUser(User user);

    SimulationPret findFirstByEnvoieDedemande(EnvoieDedemande envoieDedemande);

}
