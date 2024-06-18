package io.bootify.credit_offre_habitat.offre_immobilier.service;

import io.bootify.credit_offre_habitat.offre_immobilier.domain.OffreImmobilier;
import io.bootify.credit_offre_habitat.offre_immobilier.repos.OffreImmobilierRepository;
import io.bootify.credit_offre_habitat.user.domain.User;
import io.bootify.credit_offre_habitat.user.repos.UserRepository;
import io.bootify.credit_offre_habitat.util.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class FavorisService {

    private final UserRepository userRepository;
    private final OffreImmobilierRepository offreImmobilierRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public FavorisService(UserRepository userRepository, OffreImmobilierRepository offreImmobilierRepository) {
        this.userRepository = userRepository;
        this.offreImmobilierRepository = offreImmobilierRepository;
    }

    @Transactional
    public void toggleFavoris(Long userId, Long offreId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        OffreImmobilier offre = offreImmobilierRepository.findById(offreId).orElseThrow(() -> new NotFoundException("Offre not found"));

        if (user.getFavoris().contains(offre)) {
            user.getFavoris().remove(offre);
        } else {
            user.getFavoris().add(offre);
        }

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public boolean isFavori(Long userId, Long offreId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        return user.getFavoris().stream().anyMatch(offre -> offre.getId().equals(offreId));
    }

    @Transactional(readOnly = true)
    public Set<OffreImmobilier> getFavoris(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        user.getFavoris().size();  // Force initialization
        return user.getFavoris();
    }
}