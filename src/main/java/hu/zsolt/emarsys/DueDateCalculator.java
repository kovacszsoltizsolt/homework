package hu.zsolt.emarsys;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class DueDateCalculator {

    public LocalDateTime calculateDuedate(LocalDateTime submitDate, Integer turnaround) {
        validateParams(submitDate, turnaround);

        int turnaroundDays = turnaround / 8;
        int turnaroundHours = turnaround % 8;

        return submitDate.plusDays(turnaroundDays).plusHours(turnaroundHours);
    }

    private void validateParams(LocalDateTime submitDate, Integer turnaround) throws IllegalArgumentException {
        if (submitDate == null || turnaround == null) {
            throw new IllegalArgumentException("Submit date and turnaround should be filled!");
        }

        if (turnaround < 0) {
            throw new IllegalArgumentException("Turnaround must be greater than zero!");
        }

        validateSubmitDate(submitDate);
    }

    private void validateSubmitDate(LocalDateTime submitDate) throws IllegalArgumentException {
        if (submitDate.toLocalTime().isBefore(LocalTime.of(9, 0))
                || submitDate.toLocalTime().isAfter(LocalTime.of(17, 0))) {
            throw new IllegalArgumentException("Bugs must be reported during the working hours!");
        }
    }
}
