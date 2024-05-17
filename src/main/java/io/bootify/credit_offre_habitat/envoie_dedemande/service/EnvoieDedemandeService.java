package io.bootify.credit_offre_habitat.envoie_dedemande.service;

import io.bootify.credit_offre_habitat.envoie_dedemande.domain.EnvoieDedemande;
import io.bootify.credit_offre_habitat.envoie_dedemande.model.EnvoieDedemandeDTO;
import io.bootify.credit_offre_habitat.envoie_dedemande.repos.EnvoieDedemandeRepository;
import io.bootify.credit_offre_habitat.simulation_pret.domain.SimulationPret;
import io.bootify.credit_offre_habitat.simulation_pret.repos.SimulationPretRepository;
import io.bootify.credit_offre_habitat.user.domain.User;
import io.bootify.credit_offre_habitat.user.repos.UserRepository;
import io.bootify.credit_offre_habitat.util.NotFoundException;
import io.bootify.credit_offre_habitat.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EnvoieDedemandeService {

    private final EnvoieDedemandeRepository envoieDedemandeRepository;
    private final UserRepository userRepository;
    private final SimulationPretRepository simulationPretRepository;

    public EnvoieDedemandeService(final EnvoieDedemandeRepository envoieDedemandeRepository,
            final UserRepository userRepository,
            final SimulationPretRepository simulationPretRepository) {
        this.envoieDedemandeRepository = envoieDedemandeRepository;
        this.userRepository = userRepository;
        this.simulationPretRepository = simulationPretRepository;
    }

    public List<EnvoieDedemandeDTO> findAll() {
        final List<EnvoieDedemande> envoieDedemandes = envoieDedemandeRepository.findAll(Sort.by("id"));
        return envoieDedemandes.stream()
                .map(envoieDedemande -> mapToDTO(envoieDedemande, new EnvoieDedemandeDTO()))
                .toList();
    }

    public EnvoieDedemandeDTO get(final Long id) {
        return envoieDedemandeRepository.findById(id)
                .map(envoieDedemande -> mapToDTO(envoieDedemande, new EnvoieDedemandeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EnvoieDedemandeDTO envoieDedemandeDTO) {
        final EnvoieDedemande envoieDedemande = new EnvoieDedemande();
        mapToEntity(envoieDedemandeDTO, envoieDedemande);
        return envoieDedemandeRepository.save(envoieDedemande).getId();
    }

    public void update(final Long id, final EnvoieDedemandeDTO envoieDedemandeDTO) {
        final EnvoieDedemande envoieDedemande = envoieDedemandeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(envoieDedemandeDTO, envoieDedemande);
        envoieDedemandeRepository.save(envoieDedemande);
    }

    public void delete(final Long id) {
        envoieDedemandeRepository.deleteById(id);
    }

    private EnvoieDedemandeDTO mapToDTO(final EnvoieDedemande envoieDedemande,
            final EnvoieDedemandeDTO envoieDedemandeDTO) {
        envoieDedemandeDTO.setId(envoieDedemande.getId());
        envoieDedemandeDTO.setDateDeDemande(envoieDedemande.getDateDeDemande());
        envoieDedemandeDTO.setStatue(envoieDedemande.getStatue());
        envoieDedemandeDTO.setUser(envoieDedemande.getUser() == null ? null : envoieDedemande.getUser().getId());
        return envoieDedemandeDTO;
    }

    private EnvoieDedemande mapToEntity(final EnvoieDedemandeDTO envoieDedemandeDTO,
            final EnvoieDedemande envoieDedemande) {
        envoieDedemande.setDateDeDemande(envoieDedemandeDTO.getDateDeDemande());
        envoieDedemande.setStatue(envoieDedemandeDTO.getStatue());
        final User user = envoieDedemandeDTO.getUser() == null ? null : userRepository.findById(envoieDedemandeDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        envoieDedemande.setUser(user);
        return envoieDedemande;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final EnvoieDedemande envoieDedemande = envoieDedemandeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final SimulationPret envoieDedemandeSimulationPret = simulationPretRepository.findFirstByEnvoieDedemande(envoieDedemande);
        if (envoieDedemandeSimulationPret != null) {
            referencedWarning.setKey("envoieDedemande.simulationPret.envoieDedemande.referenced");
            referencedWarning.addParam(envoieDedemandeSimulationPret.getId());
            return referencedWarning;
        }
        return null;
    }

}
