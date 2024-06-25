package io.bootify.credit_offre_habitat.offre_immobilier.repos;

import io.bootify.credit_offre_habitat.offre_immobilier.domain.OffreImmobilier;
import io.bootify.credit_offre_habitat.offre_immobilier.domain.enums.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface OffreImmobilierRepository extends JpaRepository<OffreImmobilier, Long> {

    @Query("SELECT o FROM OffreImmobilier o " +
            "WHERE (:adresse IS NULL OR LOWER(o.adresse) LIKE %:adresse%) " +
            "AND (:typeBien IS NULL OR o.typeBien IN :typeBien) " +
            "AND (:budgetMin IS NULL OR o.prix >= :budgetMin) " +
            "AND (:budgetMax IS NULL OR o.prix <= :budgetMax) " +
            "AND (:chambres IS NULL OR o.chambres IN :chambres) " +
            "AND (:sallesDeBain IS NULL OR o.sallesDeBain IN :sallesDeBain) " +
            "AND (:agePropriete IS NULL OR o.agePropriete IN :agePropriete)")
    List<OffreImmobilier> findByCriteria(
            @Param("adresse") String adresse,
            @Param("typeBien") List<TypeBien> typeBien,
            @Param("budgetMin") Integer budgetMin,
            @Param("budgetMax") Integer budgetMax,
            @Param("chambres") List<Chambres> chambres,
            @Param("sallesDeBain") List<SallesDeBain> sallesDeBain,
            @Param("agePropriete") List<AgePropriete> agePropriete);

}
