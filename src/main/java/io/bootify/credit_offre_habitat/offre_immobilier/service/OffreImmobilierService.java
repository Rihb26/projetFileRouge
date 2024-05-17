package io.bootify.credit_offre_habitat.offre_immobilier.service;

import io.bootify.credit_offre_habitat.list_favoris.domain.ListFavoris;
import io.bootify.credit_offre_habitat.list_favoris.repos.ListFavorisRepository;
import io.bootify.credit_offre_habitat.offre_immobilier.domain.OffreImmobilier;
import io.bootify.credit_offre_habitat.offre_immobilier.model.OffreImmobilierDTO;
import io.bootify.credit_offre_habitat.offre_immobilier.repos.OffreImmobilierRepository;
import io.bootify.credit_offre_habitat.simulation_pret.domain.SimulationPret;
import io.bootify.credit_offre_habitat.simulation_pret.repos.SimulationPretRepository;
import io.bootify.credit_offre_habitat.util.NotFoundException;
import io.bootify.credit_offre_habitat.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OffreImmobilierService {

    private final OffreImmobilierRepository offreImmobilierRepository;
    private final ListFavorisRepository listFavorisRepository;
    private final SimulationPretRepository simulationPretRepository;

    public OffreImmobilierService(final OffreImmobilierRepository offreImmobilierRepository,
            final ListFavorisRepository listFavorisRepository,
            final SimulationPretRepository simulationPretRepository) {
        this.offreImmobilierRepository = offreImmobilierRepository;
        this.listFavorisRepository = listFavorisRepository;
        this.simulationPretRepository = simulationPretRepository;
    }

    public List<OffreImmobilierDTO> findAll() {
        final List<OffreImmobilier> offreImmobiliers = offreImmobilierRepository.findAll(Sort.by("id"));
        return offreImmobiliers.stream()
                .map(offreImmobilier -> mapToDTO(offreImmobilier, new OffreImmobilierDTO()))
                .toList();
    }

    public OffreImmobilierDTO get(final Long id) {
        return offreImmobilierRepository.findById(id)
                .map(offreImmobilier -> mapToDTO(offreImmobilier, new OffreImmobilierDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final OffreImmobilierDTO offreImmobilierDTO) {
        final OffreImmobilier offreImmobilier = new OffreImmobilier();
        mapToEntity(offreImmobilierDTO, offreImmobilier);
        return offreImmobilierRepository.save(offreImmobilier).getId();
    }

    public void update(final Long id, final OffreImmobilierDTO offreImmobilierDTO) {
        final OffreImmobilier offreImmobilier = offreImmobilierRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(offreImmobilierDTO, offreImmobilier);
        offreImmobilierRepository.save(offreImmobilier);
    }

    public void delete(final Long id) {
        offreImmobilierRepository.deleteById(id);
    }

    private OffreImmobilierDTO mapToDTO(final OffreImmobilier offreImmobilier,
            final OffreImmobilierDTO offreImmobilierDTO) {
        offreImmobilierDTO.setId(offreImmobilier.getId());
        offreImmobilierDTO.setTypeBien(offreImmobilier.getTypeBien());
        offreImmobilierDTO.setAdresse(offreImmobilier.getAdresse());
        offreImmobilierDTO.setPrix(offreImmobilier.getPrix());
        offreImmobilierDTO.setDescription(offreImmobilier.getDescription());
        offreImmobilierDTO.setListFavoris(offreImmobilier.getListFavoris() == null ? null : offreImmobilier.getListFavoris().getId());
        return offreImmobilierDTO;
    }

    private OffreImmobilier mapToEntity(final OffreImmobilierDTO offreImmobilierDTO,
            final OffreImmobilier offreImmobilier) {
        offreImmobilier.setTypeBien(offreImmobilierDTO.getTypeBien());
        offreImmobilier.setAdresse(offreImmobilierDTO.getAdresse());
        offreImmobilier.setPrix(offreImmobilierDTO.getPrix());
        offreImmobilier.setDescription(offreImmobilierDTO.getDescription());
        final ListFavoris listFavoris = offreImmobilierDTO.getListFavoris() == null ? null : listFavorisRepository.findById(offreImmobilierDTO.getListFavoris())
                .orElseThrow(() -> new NotFoundException("listFavoris not found"));
        offreImmobilier.setListFavoris(listFavoris);
        return offreImmobilier;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final OffreImmobilier offreImmobilier = offreImmobilierRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final SimulationPret offreImmobilierSimulationPret = simulationPretRepository.findFirstByOffreImmobilier(offreImmobilier);
        if (offreImmobilierSimulationPret != null) {
            referencedWarning.setKey("offreImmobilier.simulationPret.offreImmobilier.referenced");
            referencedWarning.addParam(offreImmobilierSimulationPret.getId());
            return referencedWarning;
        }
        return null;
    }

}
