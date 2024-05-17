package io.bootify.credit_offre_habitat.simulation_pret.rest;

import io.bootify.credit_offre_habitat.simulation_pret.model.SimulationPretDTO;
import io.bootify.credit_offre_habitat.simulation_pret.service.SimulationPretService;
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
@RequestMapping(value = "/api/simulationPrets", produces = MediaType.APPLICATION_JSON_VALUE)
public class SimulationPretResource {

    private final SimulationPretService simulationPretService;

    public SimulationPretResource(final SimulationPretService simulationPretService) {
        this.simulationPretService = simulationPretService;
    }

    @GetMapping
    public ResponseEntity<List<SimulationPretDTO>> getAllSimulationPrets() {
        return ResponseEntity.ok(simulationPretService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimulationPretDTO> getSimulationPret(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(simulationPretService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSimulationPret(
            @RequestBody @Valid final SimulationPretDTO simulationPretDTO) {
        final Long createdId = simulationPretService.create(simulationPretDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateSimulationPret(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final SimulationPretDTO simulationPretDTO) {
        simulationPretService.update(id, simulationPretDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSimulationPret(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = simulationPretService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        simulationPretService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
