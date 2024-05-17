package io.bootify.credit_offre_habitat.nouveaute_bnaque.repos;

import io.bootify.credit_offre_habitat.nouveaute_bnaque.domain.NouveauteBnaque;
import io.bootify.credit_offre_habitat.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NouveauteBnaqueRepository extends JpaRepository<NouveauteBnaque, Long> {

    NouveauteBnaque findFirstByUser(User user);

}
