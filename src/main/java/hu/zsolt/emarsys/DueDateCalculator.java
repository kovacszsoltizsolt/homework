package hu.zsolt.emarsys;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DueDateCalculator {


    public LocalDateTime calculateDuedate(LocalDateTime submitDate, Integer turnaround) {
        validateParams(submitDate, turnaround);

        LocalDateTime dueDate;
        int turnaroundDays = turnaround / 8;
        int turnaroundHours = turnaround % 8;

        dueDate = addWorkingDays(submitDate, turnaroundDays);

        return dueDate.plusHours(turnaroundHours);
    }

    private LocalDateTime addWorkingDays(LocalDateTime submitDate, int turnaroundDays) {
        for (int i = turnaroundDays; i > 0; i--) {
            submitDate = plusOneWorkingDay(submitDate);
        }
        return submitDate;
    }

    private LocalDateTime plusOneWorkingDay(LocalDateTime date) {
        do {
            date = date.plusDays(1);
        } while (isWeekendDay(date));
        return date;
    }

    private boolean isWeekendDay(LocalDateTime date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY);

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
