package io.bootify.credit_offre_habitat.user.service;

import io.bootify.credit_offre_habitat.envoie_dedemande.domain.EnvoieDedemande;
import io.bootify.credit_offre_habitat.envoie_dedemande.repos.EnvoieDedemandeRepository;
import io.bootify.credit_offre_habitat.historique_simulation.domain.HistoriqueSimulation;
import io.bootify.credit_offre_habitat.historique_simulation.repos.HistoriqueSimulationRepository;
import io.bootify.credit_offre_habitat.list_favoris.domain.ListFavoris;
import io.bootify.credit_offre_habitat.list_favoris.repos.ListFavorisRepository;
import io.bootify.credit_offre_habitat.nouveaute_bnaque.domain.NouveauteBnaque;
import io.bootify.credit_offre_habitat.nouveaute_bnaque.repos.NouveauteBnaqueRepository;
import io.bootify.credit_offre_habitat.simulation_pret.domain.SimulationPret;
import io.bootify.credit_offre_habitat.simulation_pret.repos.SimulationPretRepository;
import io.bootify.credit_offre_habitat.user.domain.User;
import io.bootify.credit_offre_habitat.user.model.UserDTO;
import io.bootify.credit_offre_habitat.user.repos.UserRepository;
import io.bootify.credit_offre_habitat.util.NotFoundException;
import io.bootify.credit_offre_habitat.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final NouveauteBnaqueRepository nouveauteBnaqueRepository;
    private final ListFavorisRepository listFavorisRepository;
    private final SimulationPretRepository simulationPretRepository;
    private final HistoriqueSimulationRepository historiqueSimulationRepository;
    private final EnvoieDedemandeRepository envoieDedemandeRepository;

    public UserService(final UserRepository userRepository,
            final NouveauteBnaqueRepository nouveauteBnaqueRepository,
            final ListFavorisRepository listFavorisRepository,
            final SimulationPretRepository simulationPretRepository,
            final HistoriqueSimulationRepository historiqueSimulationRepository,
            final EnvoieDedemandeRepository envoieDedemandeRepository) {
        this.userRepository = userRepository;
        this.nouveauteBnaqueRepository = nouveauteBnaqueRepository;
        this.listFavorisRepository = listFavorisRepository;
        this.simulationPretRepository = simulationPretRepository;
        this.historiqueSimulationRepository = historiqueSimulationRepository;
        this.envoieDedemandeRepository = envoieDedemandeRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setNom(user.getNom());
        userDTO.setPrenom(user.getPrenom());
        userDTO.setEmail(user.getEmail());
        userDTO.setMotDePasse(user.getMotDePasse());
        userDTO.setNumeroTelephone(user.getNumeroTelephone());
        userDTO.setAdresse(user.getAdresse());
        userDTO.setNouveauteBanque(user.getNouveauteBanque());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setNom(userDTO.getNom());
        user.setPrenom(userDTO.getPrenom());
        user.setEmail(userDTO.getEmail());
        user.setMotDePasse(userDTO.getMotDePasse());
        user.setNumeroTelephone(userDTO.getNumeroTelephone());
        user.setAdresse(userDTO.getAdresse());
        user.setNouveauteBanque(userDTO.getNouveauteBanque());
        return user;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final NouveauteBnaque userNouveauteBnaque = nouveauteBnaqueRepository.findFirstByUser(user);
        if (userNouveauteBnaque != null) {
            referencedWarning.setKey("user.nouveauteBnaque.user.referenced");
            referencedWarning.addParam(userNouveauteBnaque.getId());
            return referencedWarning;
        }
        final ListFavoris userListFavoris = listFavorisRepository.findFirstByUser(user);
        if (userListFavoris != null) {
            referencedWarning.setKey("user.listFavoris.user.referenced");
            referencedWarning.addParam(userListFavoris.getId());
            return referencedWarning;
        }
        final SimulationPret userSimulationPret = simulationPretRepository.findFirstByUser(user);
        if (userSimulationPret != null) {
            referencedWarning.setKey("user.simulationPret.user.referenced");
            referencedWarning.addParam(userSimulationPret.getId());
            return referencedWarning;
        }
        final HistoriqueSimulation userHistoriqueSimulation = historiqueSimulationRepository.findFirstByUser(user);
        if (userHistoriqueSimulation != null) {
            referencedWarning.setKey("user.historiqueSimulation.user.referenced");
            referencedWarning.addParam(userHistoriqueSimulation.getId());
            return referencedWarning;
        }
        final EnvoieDedemande userEnvoieDedemande = envoieDedemandeRepository.findFirstByUser(user);
        if (userEnvoieDedemande != null) {
            referencedWarning.setKey("user.envoieDedemande.user.referenced");
            referencedWarning.addParam(userEnvoieDedemande.getId());
            return referencedWarning;
        }
        return null;
    }

}
