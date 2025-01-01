package dev.eifzed.runnerz.run;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/runs")
public class RunController {

    private final RunRepository runRepository;

    public  RunController(RunRepository runRepository){
        this.runRepository = runRepository;
    }

    @GetMapping("")
    List<Run> findAll(){
        return runRepository.findAll();
    }

    @GetMapping("/id/{id}")
    Run getById(@PathVariable Integer id) {
        Optional<Run> run = runRepository.findById(id);
        if (run.isEmpty()) {
            throw new RunNotFoundException();
        }
        return run.get();
    }
    @GetMapping("/location/{location}")
    List<Run> getByLocation(@PathVariable Location location) {
        return runRepository.findByLocation(location);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void addRun(@Valid @RequestBody Run run) {
        runRepository.createRun(run);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void updateRun(@PathVariable Integer id, @Valid @RequestBody Run run) {
        runRepository.updateRun(id, run);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void deleteRun(@PathVariable Integer id) {
        runRepository.deleteRun(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/multiple")
    void saveAll(@RequestBody List<Run> runs) {
        runRepository.saveAll(runs);
    }
}