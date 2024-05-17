package io.bootify.credit_offre_habitat.detail_simulation.repos;

import io.bootify.credit_offre_habitat.detail_simulation.domain.DetailSimulation;
import io.bootify.credit_offre_habitat.simulation_pret.domain.SimulationPret;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DetailSimulationRepository extends JpaRepository<DetailSimulation, Long> {

    DetailSimulation findFirstBySimulationPrets(SimulationPret simulationPret);

}
