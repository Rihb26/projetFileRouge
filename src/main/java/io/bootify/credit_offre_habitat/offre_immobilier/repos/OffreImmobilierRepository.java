package io.bootify.credit_offre_habitat.offre_immobilier.repos;

import io.bootify.credit_offre_habitat.offre_immobilier.domain.OffreImmobilier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OffreImmobilierRepository extends JpaRepository<OffreImmobilier, Long> {

    List<OffreImmobilier> findByTypeBienAndAdresseAndPrixAndDescription(String typeBien, String adresse, String prix, String description);

}
