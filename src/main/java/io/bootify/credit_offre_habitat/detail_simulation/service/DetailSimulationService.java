package io.bootify.credit_offre_habitat.detail_simulation.service;

import io.bootify.credit_offre_habitat.detail_simulation.domain.DetailSimulation;
import io.bootify.credit_offre_habitat.detail_simulation.model.DetailSimulationDTO;
import io.bootify.credit_offre_habitat.detail_simulation.repos.DetailSimulationRepository;
import io.bootify.credit_offre_habitat.simulation_pret.domain.SimulationPret;
import io.bootify.credit_offre_habitat.simulation_pret.repos.SimulationPretRepository;
import io.bootify.credit_offre_habitat.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DetailSimulationService {

    private final DetailSimulationRepository detailSimulationRepository;
    private final SimulationPretRepository simulationPretRepository;

    public DetailSimulationService(final DetailSimulationRepository detailSimulationRepository,
            final SimulationPretRepository simulationPretRepository) {
        this.detailSimulationRepository = detailSimulationRepository;
        this.simulationPretRepository = simulationPretRepository;
    }

    public List<DetailSimulationDTO> findAll() {
        final List<DetailSimulation> detailSimulations = detailSimulationRepository.findAll(Sort.by("id"));
        return detailSimulations.stream()
                .map(detailSimulation -> mapToDTO(detailSimulation, new DetailSimulationDTO()))
                .toList();
    }

    public DetailSimulationDTO get(final Long id) {
        return detailSimulationRepository.findById(id)
                .map(detailSimulation -> mapToDTO(detailSimulation, new DetailSimulationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DetailSimulationDTO detailSimulationDTO) {
        final DetailSimulation detailSimulation = new DetailSimulation();
        mapToEntity(detailSimulationDTO, detailSimulation);
        return detailSimulationRepository.save(detailSimulation).getId();
    }

    public void update(final Long id, final DetailSimulationDTO detailSimulationDTO) {
        final DetailSimulation detailSimulation = detailSimulationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(detailSimulationDTO, detailSimulation);
        detailSimulationRepository.save(detailSimulation);
    }

    public void delete(final Long id) {
        detailSimulationRepository.deleteById(id);
    }

    private DetailSimulationDTO mapToDTO(final DetailSimulation detailSimulation,
            final DetailSimulationDTO detailSimulationDTO) {
        detailSimulationDTO.setId(detailSimulation.getId());
        detailSimulationDTO.setPrixDeBien(detailSimulation.getPrixDeBien());
        detailSimulationDTO.setDureeDePret(detailSimulation.getDureeDePret());
        detailSimulationDTO.setMensualite(detailSimulation.getMensualite());
        detailSimulationDTO.setTauxAnnuelEffectifGlobal(detailSimulation.getTauxAnnuelEffectifGlobal());
        detailSimulationDTO.setMontantTotal(detailSimulation.getMontantTotal());
        detailSimulationDTO.setSimulationPrets(detailSimulation.getSimulationPrets() == null ? null : detailSimulation.getSimulationPrets().getId());
        return detailSimulationDTO;
    }

    private DetailSimulation mapToEntity(final DetailSimulationDTO detailSimulationDTO,
            final DetailSimulation detailSimulation) {
        detailSimulation.setPrixDeBien(detailSimulationDTO.getPrixDeBien());
        detailSimulation.setDureeDePret(detailSimulationDTO.getDureeDePret());
        detailSimulation.setMensualite(detailSimulationDTO.getMensualite());
        detailSimulation.setTauxAnnuelEffectifGlobal(detailSimulationDTO.getTauxAnnuelEffectifGlobal());
        detailSimulation.setMontantTotal(detailSimulationDTO.getMontantTotal());
        final SimulationPret simulationPrets = detailSimulationDTO.getSimulationPrets() == null ? null : simulationPretRepository.findById(detailSimulationDTO.getSimulationPrets())
                .orElseThrow(() -> new NotFoundException("simulationPrets not found"));
        detailSimulation.setSimulationPrets(simulationPrets);
        return detailSimulation;
    }

}
