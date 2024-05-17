package io.bootify.credit_offre_habitat.offre_immobilier.repos;

import io.bootify.credit_offre_habitat.list_favoris.domain.ListFavoris;
import io.bootify.credit_offre_habitat.offre_immobilier.domain.OffreImmobilier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OffreImmobilierRepository extends JpaRepository<OffreImmobilier, Long> {

    OffreImmobilier findFirstByListFavoris(ListFavoris listFavoris);
    List<OffreImmobilier> findByTypeBienAndAdresseAndPrixAndDescription(String typeBien, String adresse, String prix, String description);

}
