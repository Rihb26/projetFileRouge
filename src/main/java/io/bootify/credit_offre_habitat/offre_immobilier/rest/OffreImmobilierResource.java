package io.bootify.credit_offre_habitat.offre_immobilier.rest;

import io.bootify.credit_offre_habitat.list_favoris.repos.ListFavorisRepository;
import io.bootify.credit_offre_habitat.offre_immobilier.domain.OffreImmobilier;
import io.bootify.credit_offre_habitat.offre_immobilier.model.OffreImmobilierDTO;
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
    private final ListFavorisRepository listFavorisRepository;
    private final OffreImmobilierRepository offreImmobilierRepository;

    public OffreImmobilierResource(final OffreImmobilierService offreImmobilierService,
                                   final ListFavorisRepository listFavorisRepository, final OffreImmobilierRepository offreImmobilierRepository, OffreImmobilierRepository offreImmobilierRepository1) {
        this.offreImmobilierService = offreImmobilierService;
        this.listFavorisRepository = listFavorisRepository;
        this.offreImmobilierRepository = offreImmobilierRepository1;
    }

    @GetMapping
    public ResponseEntity<List<OffreImmobilierDTO>> getAllOffreImmobiliers() {
        return ResponseEntity.ok(offreImmobilierService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OffreImmobilierDTO> getOffreImmobilier(
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
    public List<OffreImmobilier> filterOffreImmobilier(@RequestParam(value = "typeBien", required = false) String typeBien,
                                                       @RequestParam(value = "adresse", required = false) String adresse,
                                                       @RequestParam(value = "prix", required = false) String prix,
                                                       @RequestParam(value = "description", required = false) String description) {
        return offreImmobilierRepository.findByTypeBienAndAdresseAndPrixAndDescription(typeBien, adresse, prix, description);
    }

}
