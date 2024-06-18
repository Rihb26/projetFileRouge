package io.bootify.credit_offre_habitat.informations.service;

import io.bootify.credit_offre_habitat.informations.domain.Information;
import io.bootify.credit_offre_habitat.informations.model.InformationDTO;
import io.bootify.credit_offre_habitat.informations.repos.InformationRepository;
import io.bootify.credit_offre_habitat.user.domain.User;
import io.bootify.credit_offre_habitat.user.repos.UserRepository;
import io.bootify.credit_offre_habitat.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class InformationService {

    private final InformationRepository informationRepository;
    private final UserRepository userRepository;

    public InformationService(InformationRepository informationRepository, UserRepository userRepository) {
        this.informationRepository = informationRepository;
        this.userRepository = userRepository;
    }

    public List<InformationDTO> findAll() {
        final List<Information> informations = informationRepository.findAll(Sort.by("id"));
        return informations.stream()
                .map(information -> mapToDTO(information, new InformationDTO()))
                .toList();
    }

    public InformationDTO get(final Long id) {
        return informationRepository.findById(id)
                .map(information -> mapToDTO(information, new InformationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final InformationDTO informationDTO) {
        final Information information = new Information();
        mapToEntity(informationDTO, information);
        return informationRepository.save(information).getId();
    }

    public void update(final Long id, final InformationDTO informationDTO) {
        final  Information information = informationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(informationDTO, information);
        informationRepository.save(information);
    }

    public void delete(final Long id) {
        informationRepository.deleteById(id);
    }

    private InformationDTO mapToDTO(final  Information information,
            final InformationDTO informationDTO) {
        informationDTO.setId(information.getId());
        informationDTO.setDateDePublication(information.getDateDePublication());
        informationDTO.setContenu(information.getContenu());
        informationDTO.setTitre(information.getTitre());
        informationDTO.setAuteur(information.getAuteur());
        informationDTO.setUser(information.getUser() == null ? null : information.getUser().getId());
        informationDTO.setImageUrl(information.getImageUrl());
        return informationDTO;
    }

    private Information mapToEntity(final InformationDTO informationDTO,
            final Information information) {
        information.setDateDePublication(informationDTO.getDateDePublication());
        information.setContenu(informationDTO.getContenu());
        information.setTitre(informationDTO.getTitre());
        information.setAuteur(informationDTO.getAuteur());
        final User user = informationDTO.getUser() == null ? null : userRepository.findById(informationDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        information.setUser(user);
        information.setImageUrl(informationDTO.getImageUrl());
        return information;
    }

}
