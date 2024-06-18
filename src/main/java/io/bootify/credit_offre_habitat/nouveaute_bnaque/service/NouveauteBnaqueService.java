package io.bootify.credit_offre_habitat.nouveaute_bnaque.service;

import io.bootify.credit_offre_habitat.nouveaute_bnaque.domain.NouveauteBnaque;
import io.bootify.credit_offre_habitat.nouveaute_bnaque.model.NouveauteBnaqueDTO;
import io.bootify.credit_offre_habitat.nouveaute_bnaque.repos.NouveauteBnaqueRepository;
import io.bootify.credit_offre_habitat.user.domain.User;
import io.bootify.credit_offre_habitat.user.repos.UserRepository;
import io.bootify.credit_offre_habitat.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class NouveauteBnaqueService {

    private final NouveauteBnaqueRepository nouveauteBnaqueRepository;
    private final UserRepository userRepository;

    public NouveauteBnaqueService(final NouveauteBnaqueRepository nouveauteBnaqueRepository,
            final UserRepository userRepository) {
        this.nouveauteBnaqueRepository = nouveauteBnaqueRepository;
        this.userRepository = userRepository;
    }

    public List<NouveauteBnaqueDTO> findAll() {
        final List<NouveauteBnaque> nouveauteBnaques = nouveauteBnaqueRepository.findAll(Sort.by("id"));
        return nouveauteBnaques.stream()
                .map(nouveauteBnaque -> mapToDTO(nouveauteBnaque, new NouveauteBnaqueDTO()))
                .toList();
    }

    public NouveauteBnaqueDTO get(final Long id) {
        return nouveauteBnaqueRepository.findById(id)
                .map(nouveauteBnaque -> mapToDTO(nouveauteBnaque, new NouveauteBnaqueDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final NouveauteBnaqueDTO nouveauteBnaqueDTO) {
        final NouveauteBnaque nouveauteBnaque = new NouveauteBnaque();
        mapToEntity(nouveauteBnaqueDTO, nouveauteBnaque);
        return nouveauteBnaqueRepository.save(nouveauteBnaque).getId();
    }

    public void update(final Long id, final NouveauteBnaqueDTO nouveauteBnaqueDTO) {
        final NouveauteBnaque nouveauteBnaque = nouveauteBnaqueRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(nouveauteBnaqueDTO, nouveauteBnaque);
        nouveauteBnaqueRepository.save(nouveauteBnaque);
    }

    public void delete(final Long id) {
        nouveauteBnaqueRepository.deleteById(id);
    }

    private NouveauteBnaqueDTO mapToDTO(final NouveauteBnaque nouveauteBnaque,
            final NouveauteBnaqueDTO nouveauteBnaqueDTO) {
        nouveauteBnaqueDTO.setId(nouveauteBnaque.getId());
        nouveauteBnaqueDTO.setDateDePublication(nouveauteBnaque.getDateDePublication());
        nouveauteBnaqueDTO.setContenu(nouveauteBnaque.getContenu());
        nouveauteBnaqueDTO.setTitre(nouveauteBnaque.getTitre());
        nouveauteBnaqueDTO.setAuteur(nouveauteBnaque.getAuteur());
        nouveauteBnaqueDTO.setUser(nouveauteBnaque.getUser() == null ? null : nouveauteBnaque.getUser().getId());
        nouveauteBnaqueDTO.setImageUrl(nouveauteBnaque.getImageUrl());
        return nouveauteBnaqueDTO;
    }

    private NouveauteBnaque mapToEntity(final NouveauteBnaqueDTO nouveauteBnaqueDTO,
            final NouveauteBnaque nouveauteBnaque) {
        nouveauteBnaque.setDateDePublication(nouveauteBnaqueDTO.getDateDePublication());
        nouveauteBnaque.setContenu(nouveauteBnaqueDTO.getContenu());
        nouveauteBnaque.setTitre(nouveauteBnaqueDTO.getTitre());
        nouveauteBnaque.setAuteur(nouveauteBnaqueDTO.getAuteur());
        final User user = nouveauteBnaqueDTO.getUser() == null ? null : userRepository.findById(nouveauteBnaqueDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        nouveauteBnaque.setUser(user);
        nouveauteBnaque.setImageUrl(nouveauteBnaqueDTO.getImageUrl());
        return nouveauteBnaque;
    }

}
