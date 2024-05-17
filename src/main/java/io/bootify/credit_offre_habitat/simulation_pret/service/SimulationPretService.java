package io.bootify.credit_offre_habitat.simulation_pret.service;

import io.bootify.credit_offre_habitat.detail_simulation.domain.DetailSimulation;
import io.bootify.credit_offre_habitat.detail_simulation.repos.DetailSimulationRepository;
import io.bootify.credit_offre_habitat.envoie_dedemande.domain.EnvoieDedemande;
import io.bootify.credit_offre_habitat.envoie_dedemande.repos.EnvoieDedemandeRepository;
import io.bootify.credit_offre_habitat.historique_simulation.domain.HistoriqueSimulation;
import io.bootify.credit_offre_habitat.historique_simulation.repos.HistoriqueSimulationRepository;
import io.bootify.credit_offre_habitat.offre_immobilier.domain.OffreImmobilier;
import io.bootify.credit_offre_habitat.offre_immobilier.repos.OffreImmobilierRepository;
import io.bootify.credit_offre_habitat.simulation_pret.domain.SimulationPret;
import io.bootify.credit_offre_habitat.simulation_pret.model.SimulationPretDTO;
import io.bootify.credit_offre_habitat.simulation_pret.repos.SimulationPretRepository;
import io.bootify.credit_offre_habitat.user.domain.User;
import io.bootify.credit_offre_habitat.user.repos.UserRepository;
import io.bootify.credit_offre_habitat.util.NotFoundException;
import io.bootify.credit_offre_habitat.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SimulationPretService {

    private final SimulationPretRepository simulationPretRepository;
    private final OffreImmobilierRepository offreImmobilierRepository;
    private final UserRepository userRepository;
    private final HistoriqueSimulationRepository historiqueSimulationRepository;
    private final EnvoieDedemandeRepository envoieDedemandeRepository;
    private final DetailSimulationRepository detailSimulationRepository;

    public SimulationPretService(final SimulationPretRepository simulationPretRepository,
            final OffreImmobilierRepository offreImmobilierRepository,
            final UserRepository userRepository,
            final HistoriqueSimulationRepository historiqueSimulationRepository,
            final EnvoieDedemandeRepository envoieDedemandeRepository,
            final DetailSimulationRepository detailSimulationRepository) {
        this.simulationPretRepository = simulationPretRepository;
        this.offreImmobilierRepository = offreImmobilierRepository;
        this.userRepository = userRepository;
        this.historiqueSimulationRepository = historiqueSimulationRepository;
        this.envoieDedemandeRepository = envoieDedemandeRepository;
        this.detailSimulationRepository = detailSimulationRepository;
    }

    public List<SimulationPretDTO> findAll() {
        final List<SimulationPret> simulationPrets = simulationPretRepository.findAll(Sort.by("id"));
        return simulationPrets.stream()
                .map(simulationPret -> mapToDTO(simulationPret, new SimulationPretDTO()))
                .toList();
    }

    public SimulationPretDTO get(final Long id) {
        return simulationPretRepository.findById(id)
                .map(simulationPret -> mapToDTO(simulationPret, new SimulationPretDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SimulationPretDTO simulationPretDTO) {
        final SimulationPret simulationPret = new SimulationPret();
        mapToEntity(simulationPretDTO, simulationPret);
        return simulationPretRepository.save(simulationPret).getId();
    }

    public void update(final Long id, final SimulationPretDTO simulationPretDTO) {
        final SimulationPret simulationPret = simulationPretRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(simulationPretDTO, simulationPret);
        simulationPretRepository.save(simulationPret);
    }

    public void delete(final Long id) {
        simulationPretRepository.deleteById(id);
    }

    private SimulationPretDTO mapToDTO(final SimulationPret simulationPret,
            final SimulationPretDTO simulationPretDTO) {
        simulationPretDTO.setId(simulationPret.getId());
        simulationPretDTO.setResultat(simulationPret.getResultat());
        simulationPretDTO.setOffreImmobilier(simulationPret.getOffreImmobilier() == null ? null : simulationPret.getOffreImmobilier().getId());
        simulationPretDTO.setUser(simulationPret.getUser() == null ? null : simulationPret.getUser().getId());
        simulationPretDTO.setHistoriqueSimulation(simulationPret.getHistoriqueSimulation() == null ? null : simulationPret.getHistoriqueSimulation().getId());
        simulationPretDTO.setEnvoieDedemande(simulationPret.getEnvoieDedemande() == null ? null : simulationPret.getEnvoieDedemande().getId());
        return simulationPretDTO;
    }

    private SimulationPret mapToEntity(final SimulationPretDTO simulationPretDTO,
            final SimulationPret simulationPret) {
        simulationPret.setResultat(simulationPretDTO.getResultat());
        final OffreImmobilier offreImmobilier = simulationPretDTO.getOffreImmobilier() == null ? null : offreImmobilierRepository.findById(simulationPretDTO.getOffreImmobilier())
                .orElseThrow(() -> new NotFoundException("offreImmobilier not found"));
        simulationPret.setOffreImmobilier(offreImmobilier);
        final User user = simulationPretDTO.getUser() == null ? null : userRepository.findById(simulationPretDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        simulationPret.setUser(user);
        final HistoriqueSimulation historiqueSimulation = simulationPretDTO.getHistoriqueSimulation() == null ? null : historiqueSimulationRepository.findById(simulationPretDTO.getHistoriqueSimulation())
                .orElseThrow(() -> new NotFoundException("historiqueSimulation not found"));
        simulationPret.setHistoriqueSimulation(historiqueSimulation);
        final EnvoieDedemande envoieDedemande = simulationPretDTO.getEnvoieDedemande() == null ? null : envoieDedemandeRepository.findById(simulationPretDTO.getEnvoieDedemande())
                .orElseThrow(() -> new NotFoundException("envoieDedemande not found"));
        simulationPret.setEnvoieDedemande(envoieDedemande);
        return simulationPret;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final SimulationPret simulationPret = simulationPretRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final DetailSimulation simulationPretsDetailSimulation = detailSimulationRepository.findFirstBySimulationPrets(simulationPret);
        if (simulationPretsDetailSimulation != null) {
            referencedWarning.setKey("simulationPret.detailSimulation.simulationPrets.referenced");
            referencedWarning.addParam(simulationPretsDetailSimulation.getId());
            return referencedWarning;
        }
        return null;
    }

}
