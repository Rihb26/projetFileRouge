package io.bootify.credit_offre_habitat.nouveaute_bnaque.rest;

import io.bootify.credit_offre_habitat.nouveaute_bnaque.model.NouveauteBnaqueDTO;
import io.bootify.credit_offre_habitat.nouveaute_bnaque.service.NouveauteBnaqueService;
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
@RequestMapping(value = "/api/nouveauteBnaques", produces = MediaType.APPLICATION_JSON_VALUE)
public class NouveauteBnaqueResource {

    private final NouveauteBnaqueService nouveauteBnaqueService;

    public NouveauteBnaqueResource(final NouveauteBnaqueService nouveauteBnaqueService) {
        this.nouveauteBnaqueService = nouveauteBnaqueService;
    }

    @GetMapping
    public ResponseEntity<List<NouveauteBnaqueDTO>> getAllNouveauteBnaques() {
        return ResponseEntity.ok(nouveauteBnaqueService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NouveauteBnaqueDTO> getNouveauteBnaque(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(nouveauteBnaqueService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createNouveauteBnaque(
            @RequestBody @Valid final NouveauteBnaqueDTO nouveauteBnaqueDTO) {
        final Long createdId = nouveauteBnaqueService.create(nouveauteBnaqueDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateNouveauteBnaque(@PathVariable(name = "id") final Long id,
                                                      @RequestBody @Valid final NouveauteBnaqueDTO nouveauteBnaqueDTO) {
        nouveauteBnaqueService.update(id, nouveauteBnaqueDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteNouveauteBnaque(@PathVariable(name = "id") final Long id) {
        nouveauteBnaqueService.delete(id);
        return ResponseEntity.noContent().build();
    }

}