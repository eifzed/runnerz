package dev.eifzed.runnerz.run;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record Run(
        @Min(1)
        Integer id,
        @NotEmpty
        String title,
        LocalDateTime startedOn,
        LocalDateTime completedOn,
        @Positive
        Integer miles,
        Location location
) {


}
