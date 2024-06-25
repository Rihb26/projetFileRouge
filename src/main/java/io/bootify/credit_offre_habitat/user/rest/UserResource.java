package io.bootify.credit_offre_habitat.user.rest;

import io.bootify.credit_offre_habitat.security.model.JwtResponse;
import io.bootify.credit_offre_habitat.security.util.JwtTokenUtil;
import io.bootify.credit_offre_habitat.user.domain.User;
import io.bootify.credit_offre_habitat.user.model.UserDTO;
import io.bootify.credit_offre_habitat.user.service.UserService;
import io.bootify.credit_offre_habitat.util.ReferencedException;
import io.bootify.credit_offre_habitat.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserResource {

    private static final Logger logger = LoggerFactory.getLogger(UserResource.class);

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserResource(UserService userService,
                        AuthenticationManager authenticationManager,
                        JwtTokenUtil jwtTokenUtil,
                        PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @PostMapping("/users")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createUser(@RequestBody @Valid final UserDTO userDTO) {
        final Long createdId = userService.create(userDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<Long> updateUser(@PathVariable(name = "id") final Long id,
                                           @RequestBody @Valid final UserDTO userDTO) {
        userService.update(id, userDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/users/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = userService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDTO userDTO) throws Exception {
        logger.info("Authentication request received for user: {}", userDTO.getEmail());

        authenticate(userDTO.getEmail(), userDTO.getMotDePasse());

        final UserDetails userDetails = userService.loadUserByUsername(userDTO.getEmail());
        final User user = userService.loadUserEntityByEmail(userDTO.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails, user.getId());

        logger.info("Authentication successful for user: {}", userDTO.getEmail());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) throws Exception {
        logger.info("Register request received for user: {}", userDTO.getEmail());
        String rawPassword = userDTO.getMotDePasse();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        userDTO.setMotDePasse(encodedPassword);
        UserDTO savedUser = userService.save(userDTO);
        logger.info("User registered successfully with ID: {}", savedUser.getId());
        return ResponseEntity.ok(savedUser);
    }

    private void authenticate(String email, String password) throws Exception {
        logger.info("Authenticating user: {}", email);

        User user = userService.loadUserEntityByEmail(email);

        if (passwordEncoder.matches(password, user.getMotDePasse())) {
            logger.info("Passwords match");
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
            authenticationManager.authenticate(authenticationToken);
        } else {
            logger.error("Invalid credentials for user: {}", email);
            throw new Exception("INVALID_CREDENTIALS");
        }
    }
}
