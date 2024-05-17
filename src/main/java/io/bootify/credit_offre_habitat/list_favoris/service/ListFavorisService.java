package io.bootify.credit_offre_habitat.list_favoris.service;

import io.bootify.credit_offre_habitat.list_favoris.domain.ListFavoris;
import io.bootify.credit_offre_habitat.list_favoris.model.ListFavorisDTO;
import io.bootify.credit_offre_habitat.list_favoris.repos.ListFavorisRepository;
import io.bootify.credit_offre_habitat.offre_immobilier.domain.OffreImmobilier;
import io.bootify.credit_offre_habitat.offre_immobilier.repos.OffreImmobilierRepository;
import io.bootify.credit_offre_habitat.user.domain.User;
import io.bootify.credit_offre_habitat.user.repos.UserRepository;
import io.bootify.credit_offre_habitat.util.NotFoundException;
import io.bootify.credit_offre_habitat.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ListFavorisService {

    private final ListFavorisRepository listFavorisRepository;
    private final UserRepository userRepository;
    private final OffreImmobilierRepository offreImmobilierRepository;

    public ListFavorisService(final ListFavorisRepository listFavorisRepository,
            final UserRepository userRepository,
            final OffreImmobilierRepository offreImmobilierRepository) {
        this.listFavorisRepository = listFavorisRepository;
        this.userRepository = userRepository;
        this.offreImmobilierRepository = offreImmobilierRepository;
    }

    public List<ListFavorisDTO> findAll() {
        final List<ListFavoris> listFavorises = listFavorisRepository.findAll(Sort.by("id"));
        return listFavorises.stream()
                .map(listFavoris -> mapToDTO(listFavoris, new ListFavorisDTO()))
                .toList();
    }

    public ListFavorisDTO get(final Long id) {
        return listFavorisRepository.findById(id)
                .map(listFavoris -> mapToDTO(listFavoris, new ListFavorisDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ListFavorisDTO listFavorisDTO) {
        final ListFavoris listFavoris = new ListFavoris();
        mapToEntity(listFavorisDTO, listFavoris);
        return listFavorisRepository.save(listFavoris).getId();
    }

    public void update(final Long id, final ListFavorisDTO listFavorisDTO) {
        final ListFavoris listFavoris = listFavorisRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(listFavorisDTO, listFavoris);
        listFavorisRepository.save(listFavoris);
    }

    public void delete(final Long id) {
        listFavorisRepository.deleteById(id);
    }

    private ListFavorisDTO mapToDTO(final ListFavoris listFavoris,
            final ListFavorisDTO listFavorisDTO) {
        listFavorisDTO.setId(listFavoris.getId());
        listFavorisDTO.setListFavoris(listFavoris.getListFavoris());
        listFavorisDTO.setUser(listFavoris.getUser() == null ? null : listFavoris.getUser().getId());
        return listFavorisDTO;
    }

    private ListFavoris mapToEntity(final ListFavorisDTO listFavorisDTO,
            final ListFavoris listFavoris) {
        listFavoris.setListFavoris(listFavorisDTO.getListFavoris());
        final User user = listFavorisDTO.getUser() == null ? null : userRepository.findById(listFavorisDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        listFavoris.setUser(user);
        return listFavoris;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final ListFavoris listFavoris = listFavorisRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final OffreImmobilier listFavorisOffreImmobilier = offreImmobilierRepository.findFirstByListFavoris(listFavoris);
        if (listFavorisOffreImmobilier != null) {
            referencedWarning.setKey("listFavoris.offreImmobilier.listFavoris.referenced");
            referencedWarning.addParam(listFavorisOffreImmobilier.getId());
            return referencedWarning;
        }
        return null;
    }

}
