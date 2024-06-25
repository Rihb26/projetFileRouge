package io.bootify.credit_offre_habitat.user.service;

import io.bootify.credit_offre_habitat.user.domain.User;
import io.bootify.credit_offre_habitat.user.model.UserDTO;
import io.bootify.credit_offre_habitat.user.repos.UserRepository;
import io.bootify.credit_offre_habitat.util.NotFoundException;
import io.bootify.credit_offre_habitat.util.ReferencedWarning;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .collect(Collectors.toList());
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        if (userDTO.getMotDePasse() == null || userDTO.getMotDePasse().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        final User user = new User();
        mapToEntity(userDTO, user);
        user.setMotDePasse(passwordEncoder.encode(userDTO.getMotDePasse()));
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        if (userDTO.getMotDePasse() != null && !userDTO.getMotDePasse().isEmpty()) {
            user.setMotDePasse(passwordEncoder.encode(userDTO.getMotDePasse()));
        }
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
        user.setNumeroTelephone(userDTO.getNumeroTelephone());
        user.setAdresse(userDTO.getAdresse());
        user.setNouveauteBanque(userDTO.getNouveauteBanque());
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("Loading user by email: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        logger.info("User found: {}", email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getMotDePasse(),
                new ArrayList<>());
    }

    public User loadUserEntityByEmail(String email) {
        logger.info("Loading user entity by email: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        // Check for references and set warning if needed
        // Implementation of these checks are omitted for brevity
        return null;
    }

    public UserDTO save(UserDTO userDTO) {
        logger.info("Saving user: {}", userDTO.getEmail());
        User user = new User();
        user.setNom(userDTO.getNom());
        user.setPrenom(userDTO.getPrenom());
        user.setEmail(userDTO.getEmail());
        user.setMotDePasse(userDTO.getMotDePasse());
        user.setNumeroTelephone(userDTO.getNumeroTelephone());
        user.setAdresse(userDTO.getAdresse());
        user.setNouveauteBanque(userDTO.getNouveauteBanque());
        user = userRepository.save(user);
        userDTO.setId(user.getId());
        logger.info("User saved with ID: {}", user.getId());
        return userDTO;
    }
}
