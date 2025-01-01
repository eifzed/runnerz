package dev.eifzed.runnerz.run;

import jakarta.annotation.PostConstruct;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;

@Repository
public class RunRepository {
    private final JdbcClient jdbcClient;
    private static final Logger log = LoggerFactory.getLogger(RunRepository.class);

    public  RunRepository(JdbcClient jdbcClient){
        this.jdbcClient = jdbcClient;
    }

    public List<Run> findAll(){
        return jdbcClient.
                sql("select * from run").
                query(Run.class).
                list();
    }

    public Optional<Run> findById(Integer id){
        return jdbcClient.
                sql("select * from run where id = :id").
                param("id", id).
                query(Run.class).
                optional();
    }

    public void createRun(Run run) {
        var updated = jdbcClient.
                sql("insert into run(id, title, started_on, completed_on, miles, location) values(?,?,?,?,?,?)").
                params(List.of(run.id(), run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString())).
                update();

        Assert.state(updated == 1, "failed to create run "+ run.title());
    }

    public void updateRun(Integer id, Run run){
        var updated = jdbcClient.
                sql("UPDATE run set title = ?, started_on = ?, completed_on = ?, miles = ?, location = ? where id = ?").
                params(List.of(run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString(), id)).
                update();

        Assert.state(updated == 1, "failed to update run id "+ id.toString());
    }

    public void deleteRun(Integer id){
        var updated = jdbcClient.
                sql("delete run where id = :id").
                param("id", id).
                update();
        Assert.state(updated == 1, "failed to delete run id "+ id.toString());

    }

    public void saveAll(List<Run> runs) {
        runs.forEach(this::createRun);
    }

    public List<Run> findByLocation(Location location) {
        return jdbcClient.
                sql("select * from run where location = ?").
                param(location.toString()).
                query(Run.class).
                list();
    }

}
