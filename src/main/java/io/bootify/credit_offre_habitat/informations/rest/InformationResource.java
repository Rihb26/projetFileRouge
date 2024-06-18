package io.bootify.credit_offre_habitat.informations.rest;

import io.bootify.credit_offre_habitat.informations.service.InformationService;
import io.bootify.credit_offre_habitat.informations.model.InformationDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/informations", produces = MediaType.APPLICATION_JSON_VALUE)
public class InformationResource {
    
    private final InformationService informationService;

    public InformationResource(InformationService informationService) {
        this.informationService = informationService;
    }

    @GetMapping
    public ResponseEntity<List<InformationDTO>> getAllInformations() {
        return ResponseEntity.ok(informationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InformationDTO> getInformation(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(informationService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createInformation(
            @RequestBody @Valid final InformationDTO InformationDTO) {
        final Long createdId = informationService.create(InformationDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateInformation(@PathVariable(name = "id") final Long id,
                                                      @RequestBody @Valid final InformationDTO InformationDTO) {
        informationService.update(id, InformationDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteInformation(@PathVariable(name = "id") final Long id) {
        informationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
