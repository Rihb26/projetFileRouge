package io.bootify.credit_offre_habitat.offre_immobilier.service;

import io.bootify.credit_offre_habitat.offre_immobilier.domain.enums.*;
import io.bootify.credit_offre_habitat.offre_immobilier.domain.image.Image;
import io.bootify.credit_offre_habitat.offre_immobilier.domain.OffreImmobilier;
import io.bootify.credit_offre_habitat.offre_immobilier.model.OffreImmobilierDTO;
import io.bootify.credit_offre_habitat.offre_immobilier.model.OffreImmobilierResponseDTO;
import io.bootify.credit_offre_habitat.offre_immobilier.repos.OffreImmobilierRepository;
import io.bootify.credit_offre_habitat.simulation_pret.domain.SimulationPret;
import io.bootify.credit_offre_habitat.simulation_pret.repos.SimulationPretRepository;
import io.bootify.credit_offre_habitat.util.NotFoundException;
import io.bootify.credit_offre_habitat.util.ReferencedWarning;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OffreImmobilierService {

    private final OffreImmobilierRepository offreImmobilierRepository;
    private final SimulationPretRepository simulationPretRepository;

    public OffreImmobilierService(final OffreImmobilierRepository offreImmobilierRepository,
            final SimulationPretRepository simulationPretRepository) {
        this.offreImmobilierRepository = offreImmobilierRepository;
        this.simulationPretRepository = simulationPretRepository;
    }

    public List<OffreImmobilierDTO> findAll() {
        final List<OffreImmobilier> offreImmobiliers = offreImmobilierRepository.findAll(Sort.by("id"));
        return offreImmobiliers.stream()
                .map(this::mapToDTO)
                .toList();
    }

    public OffreImmobilierResponseDTO get(final Long id) {
        OffreImmobilier offreImmobilier = offreImmobilierRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("OffreImmobilier not found"));

        OffreImmobilierDTO dto = mapToDTO(offreImmobilier);

        OffreImmobilierResponseDTO responseDTO = new OffreImmobilierResponseDTO();
        responseDTO.setOffreImmobilier(dto);
        responseDTO.setTypeBienOptions(TypeBien.values());
        responseDTO.setAmeublementOptions(Ameublement.values());
        responseDTO.setStatutProprieteOptions(StatutPropriete.values());
        responseDTO.setChambresOptions(Chambres.values());
        responseDTO.setSallesDeBainOptions(SallesDeBain.values());
        responseDTO.setAgeProprieteOptions(AgePropriete.values());

        return responseDTO;
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

    public List<OffreImmobilierDTO> getFilteredOffres(String adresse, List<TypeBien> typeBien, Integer budgetMin, Integer budgetMax, List<Chambres> chambres, List<SallesDeBain> sallesDeBain, List<AgePropriete> agePropriete) {
        List<OffreImmobilier> offres = offreImmobilierRepository.findByCriteria(adresse, typeBien, budgetMin, budgetMax, chambres, sallesDeBain, agePropriete);
        return offres.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private OffreImmobilierDTO mapToDTO(OffreImmobilier offreImmobilier) {
        OffreImmobilierDTO dto = new OffreImmobilierDTO();
        dto.setId(offreImmobilier.getId());
        dto.setTypeBien(offreImmobilier.getTypeBien());
        dto.setAdresse(offreImmobilier.getAdresse());
        dto.setPrix(offreImmobilier.getPrix());
        dto.setDescription(offreImmobilier.getDescription());
        dto.setStatutPropriete(offreImmobilier.getStatutPropriete());
        dto.setAmeublement(offreImmobilier.getAmeublement());
        dto.setChambres(offreImmobilier.getChambres());
        dto.setSallesDeBain(offreImmobilier.getSallesDeBain());
        dto.setSurface(offreImmobilier.getSurface());
        dto.setAgePropriete(offreImmobilier.getAgePropriete());
        dto.setImageUrls(offreImmobilier.getImages().stream().map(Image::getUrl).collect(Collectors.toList()));
        return dto;
    }

    private OffreImmobilier mapToEntity(final OffreImmobilierDTO offreImmobilierDTO, final OffreImmobilier offreImmobilier) {
        offreImmobilier.setTypeBien(offreImmobilierDTO.getTypeBien());
        offreImmobilier.setAdresse(offreImmobilierDTO.getAdresse());
        offreImmobilier.setPrix(offreImmobilierDTO.getPrix());
        offreImmobilier.setDescription(offreImmobilierDTO.getDescription());
        offreImmobilier.setStatutPropriete(offreImmobilierDTO.getStatutPropriete());
        offreImmobilier.setAmeublement(offreImmobilierDTO.getAmeublement());
        offreImmobilier.setChambres(offreImmobilierDTO.getChambres());
        offreImmobilier.setSallesDeBain(offreImmobilierDTO.getSallesDeBain());
        offreImmobilier.setSurface(offreImmobilierDTO.getSurface());
        offreImmobilier.setAgePropriete(offreImmobilierDTO.getAgePropriete());
        Set<Image> images = offreImmobilierDTO.getImageUrls().stream()
                .map(url -> {
                    Image image = new Image();
                    image.setUrl(url);
                    image.setOffreImmobilier(offreImmobilier);
                    return image;
                }).collect(Collectors.toSet());
        offreImmobilier.setImages(images);
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
