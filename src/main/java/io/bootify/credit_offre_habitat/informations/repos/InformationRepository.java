package io.bootify.credit_offre_habitat.informations.repos;

import io.bootify.credit_offre_habitat.informations.domain.Information;
import io.bootify.credit_offre_habitat.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InformationRepository extends JpaRepository<Information, Long> {

    Information findFirstByUser(User user);

}
