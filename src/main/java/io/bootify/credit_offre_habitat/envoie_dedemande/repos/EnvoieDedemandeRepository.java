package io.bootify.credit_offre_habitat.envoie_dedemande.repos;

import io.bootify.credit_offre_habitat.envoie_dedemande.domain.EnvoieDedemande;
import io.bootify.credit_offre_habitat.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EnvoieDedemandeRepository extends JpaRepository<EnvoieDedemande, Long> {

    EnvoieDedemande findFirstByUser(User user);

}
