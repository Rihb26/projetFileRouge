package io.bootify.credit_offre_habitat.user.repos;

import io.bootify.credit_offre_habitat.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
