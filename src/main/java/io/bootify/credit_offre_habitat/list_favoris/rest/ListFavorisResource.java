package io.bootify.credit_offre_habitat.list_favoris.rest;

import io.bootify.credit_offre_habitat.list_favoris.model.ListFavorisDTO;
import io.bootify.credit_offre_habitat.list_favoris.service.ListFavorisService;
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
@RequestMapping(value = "/api/listFavorises", produces = MediaType.APPLICATION_JSON_VALUE)
public class ListFavorisResource {

    private final ListFavorisService listFavorisService;

    public ListFavorisResource(final ListFavorisService listFavorisService) {
        this.listFavorisService = listFavorisService;
    }

    @GetMapping
    public ResponseEntity<List<ListFavorisDTO>> getAllListFavorises() {
        return ResponseEntity.ok(listFavorisService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListFavorisDTO> getListFavoris(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(listFavorisService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createListFavoris(
            @RequestBody @Valid final ListFavorisDTO listFavorisDTO) {
        final Long createdId = listFavorisService.create(listFavorisDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateListFavoris(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ListFavorisDTO listFavorisDTO) {
        listFavorisService.update(id, listFavorisDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteListFavoris(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = listFavorisService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        listFavorisService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
