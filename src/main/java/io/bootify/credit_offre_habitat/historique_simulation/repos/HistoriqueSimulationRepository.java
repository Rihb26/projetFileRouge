package io.bootify.credit_offre_habitat.historique_simulation.repos;

import io.bootify.credit_offre_habitat.historique_simulation.domain.HistoriqueSimulation;
import io.bootify.credit_offre_habitat.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HistoriqueSimulationRepository extends JpaRepository<HistoriqueSimulation, Long> {

    HistoriqueSimulation findFirstByUser(User user);

}
