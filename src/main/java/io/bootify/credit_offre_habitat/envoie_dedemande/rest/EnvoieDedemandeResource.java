package io.bootify.credit_offre_habitat.envoie_dedemande.rest;

import io.bootify.credit_offre_habitat.envoie_dedemande.model.EnvoieDedemandeDTO;
import io.bootify.credit_offre_habitat.envoie_dedemande.service.EnvoieDedemandeService;
import io.bootify.credit_offre_habitat.util.ReferencedException;
import io.bootify.credit_offre_habitat.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/envoieDedemandes", produces = MediaType.APPLICATION_JSON_VALUE)
public class EnvoieDedemandeResource {

    private final EnvoieDedemandeService envoieDedemandeService;

    public EnvoieDedemandeResource(final EnvoieDedemandeService envoieDedemandeService) {
        this.envoieDedemandeService = envoieDedemandeService;
    }

    @GetMapping
    public ResponseEntity<List<EnvoieDedemandeDTO>> getAllEnvoieDedemandes() {
        return ResponseEntity.ok(envoieDedemandeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvoieDedemandeDTO> getEnvoieDedemande(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(envoieDedemandeService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createEnvoieDedemande(
            @RequestBody @Valid final EnvoieDedemandeDTO envoieDedemandeDTO) {
        final Long createdId = envoieDedemandeService.create(envoieDedemandeDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateEnvoieDedemande(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final EnvoieDedemandeDTO envoieDedemandeDTO) {
        envoieDedemandeService.update(id, envoieDedemandeDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEnvoieDedemande(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = envoieDedemandeService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        envoieDedemandeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
