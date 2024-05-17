package io.bootify.credit_offre_habitat.detail_simulation.rest;

import io.bootify.credit_offre_habitat.detail_simulation.model.DetailSimulationDTO;
import io.bootify.credit_offre_habitat.detail_simulation.service.DetailSimulationService;
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
@RequestMapping(value = "/api/detailSimulations", produces = MediaType.APPLICATION_JSON_VALUE)
public class DetailSimulationResource {

    private final DetailSimulationService detailSimulationService;

    public DetailSimulationResource(final DetailSimulationService detailSimulationService) {
        this.detailSimulationService = detailSimulationService;
    }

    @GetMapping
    public ResponseEntity<List<DetailSimulationDTO>> getAllDetailSimulations() {
        return ResponseEntity.ok(detailSimulationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailSimulationDTO> getDetailSimulation(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(detailSimulationService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDetailSimulation(
            @RequestBody @Valid final DetailSimulationDTO detailSimulationDTO) {
        final Long createdId = detailSimulationService.create(detailSimulationDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDetailSimulation(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final DetailSimulationDTO detailSimulationDTO) {
        detailSimulationService.update(id, detailSimulationDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDetailSimulation(@PathVariable(name = "id") final Long id) {
        detailSimulationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
