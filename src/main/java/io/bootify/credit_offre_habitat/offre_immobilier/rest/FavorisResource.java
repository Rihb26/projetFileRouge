package io.bootify.credit_offre_habitat.offre_immobilier.rest;

import io.bootify.credit_offre_habitat.offre_immobilier.domain.OffreImmobilier;
import io.bootify.credit_offre_habitat.offre_immobilier.service.FavorisService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/favoris")
public class FavorisResource {

    private final FavorisService favorisService;

    public FavorisResource(FavorisService favorisService) {
        this.favorisService = favorisService;
    }

    @PostMapping("/toggle/{userId}/{offreId}")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<Void> toggleFavoris(@PathVariable Long userId, @PathVariable Long offreId) {
        favorisService.toggleFavoris(userId, offreId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/isFavori/{userId}/{offreId}")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<Boolean> isFavori(@PathVariable Long userId, @PathVariable Long offreId) {
        boolean isFavori = favorisService.isFavori(userId, offreId);
        return ResponseEntity.ok(isFavori);
    }

    @GetMapping("/{userId}")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<Set<OffreImmobilier>> getFavoris(@PathVariable Long userId) {
        Set<OffreImmobilier> favoris = favorisService.getFavoris(userId);
        return ResponseEntity.ok(favoris);
    }
}