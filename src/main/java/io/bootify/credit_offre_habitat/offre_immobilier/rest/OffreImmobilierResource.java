package io.bootify.credit_offre_habitat.offre_immobilier.rest;

import io.bootify.credit_offre_habitat.offre_immobilier.domain.OffreImmobilier;
import io.bootify.credit_offre_habitat.offre_immobilier.domain.enums.*;
import io.bootify.credit_offre_habitat.offre_immobilier.model.OffreImmobilierDTO;
import io.bootify.credit_offre_habitat.offre_immobilier.model.OffreImmobilierResponseDTO;
import io.bootify.credit_offre_habitat.offre_immobilier.repos.OffreImmobilierRepository;
import io.bootify.credit_offre_habitat.offre_immobilier.service.OffreImmobilierService;
import io.bootify.credit_offre_habitat.util.ReferencedException;
import io.bootify.credit_offre_habitat.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/offreImmobiliers", produces = MediaType.APPLICATION_JSON_VALUE)
public class OffreImmobilierResource {

    private final OffreImmobilierService offreImmobilierService;
    private final OffreImmobilierRepository offreImmobilierRepository;

    public OffreImmobilierResource(final OffreImmobilierService offreImmobilierService, final OffreImmobilierRepository offreImmobilierRepository, OffreImmobilierRepository offreImmobilierRepository1) {
        this.offreImmobilierService = offreImmobilierService;
        this.offreImmobilierRepository = offreImmobilierRepository1;
    }

    @GetMapping
    public ResponseEntity<List<OffreImmobilierDTO>> getAllOffreImmobiliers() {
        return ResponseEntity.ok(offreImmobilierService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OffreImmobilierResponseDTO> getOffreImmobilier(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(offreImmobilierService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createOffreImmobilier(
            @RequestBody @Valid final OffreImmobilierDTO offreImmobilierDTO) {
        final Long createdId = offreImmobilierService.create(offreImmobilierDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateOffreImmobilier(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final OffreImmobilierDTO offreImmobilierDTO) {
        offreImmobilierService.update(id, offreImmobilierDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteOffreImmobilier(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = offreImmobilierService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        offreImmobilierService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<List<OffreImmobilierDTO>> filterOffreImmobiliers(
            @RequestParam(required = false) String adresse,
            @RequestParam(required = false) List<TypeBien> typeBien,
            @RequestParam(required = false) Integer budgetMin,
            @RequestParam(required = false) Integer budgetMax,
            @RequestParam(required = false) List<Chambres> chambres,
            @RequestParam(required = false) List<SallesDeBain> sallesDeBain,
            @RequestParam(required = false) List<AgePropriete> agePropriete) {
        return ResponseEntity.ok(offreImmobilierService.getFilteredOffres(adresse, typeBien, budgetMin, budgetMax, chambres, sallesDeBain, agePropriete));
    }


}
