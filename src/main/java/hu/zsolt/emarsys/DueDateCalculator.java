package hu.zsolt.emarsys;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DueDateCalculator {

    private static final LocalTime START_OF_WORKING_DAY = LocalTime.of(9, 0);
    private static final LocalTime END_OF_WORKING_DAY = LocalTime.of(17, 0);


    public LocalDateTime calculateDuedate(LocalDateTime submitDate, Integer turnaround) {
        validateParams(submitDate, turnaround);

        LocalDateTime dueDate;
        int turnaroundDays = turnaround / 8;
        int turnaroundHours = turnaround % 8;

        dueDate = addWorkingDays(submitDate, turnaroundDays);
        dueDate = addWorkingHours(dueDate, turnaroundHours);

        return dueDate;
    }

    private LocalDateTime addWorkingHours(LocalDateTime dueDate, int turnaroundHours) {
        dueDate = dueDate.plusHours(turnaroundHours);
        LocalTime time = dueDate.toLocalTime();

        if (time.isAfter(END_OF_WORKING_DAY)) {
            LocalTime newTime = START_OF_WORKING_DAY.plusMinutes(getMinutesAfterEndOfDay(time));
            dueDate = plusOneWorkingDay(dueDate.with(newTime));
        }

        return dueDate;
    }

    private int getMinutesAfterEndOfDay(LocalTime start) {
        return (start.getHour() - DueDateCalculator.END_OF_WORKING_DAY.getHour()) * 60 + start.getMinute();
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
        if (submitDate.toLocalTime().isBefore(START_OF_WORKING_DAY)
                || submitDate.toLocalTime().isAfter(END_OF_WORKING_DAY)) {
            throw new IllegalArgumentException("Bugs must be reported during the working hours!");
        }
    }
}
