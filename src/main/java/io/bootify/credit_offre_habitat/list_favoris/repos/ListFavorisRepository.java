package io.bootify.credit_offre_habitat.list_favoris.repos;

import io.bootify.credit_offre_habitat.list_favoris.domain.ListFavoris;
import io.bootify.credit_offre_habitat.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ListFavorisRepository extends JpaRepository<ListFavoris, Long> {

    ListFavoris findFirstByUser(User user);

}
