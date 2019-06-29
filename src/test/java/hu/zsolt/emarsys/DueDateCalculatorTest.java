package hu.zsolt.emarsys;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DueDateCalculatorTest {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static DueDateCalculator calculator= new DueDateCalculator();

    @Test
    public void testNullSubmitDate() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateDueDate(null, 8));
    }

    @Test
    public void testNullTurnaround() {
        LocalDateTime submitDate = LocalDateTime.parse("2019-03-04 08:30", formatter);
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateDueDate(submitDate, null));
    }

    @Test
    public void testInvalidSubmitDateBefore() {
        LocalDateTime submitDate = LocalDateTime.parse("2019-03-04 07:30", formatter);
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateDueDate(submitDate, 8));
    }

    @Test
    public void testInvalidSubmitDateAfter() {
        LocalDateTime submitDate = LocalDateTime.parse("2019-03-04 17:30", formatter);
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateDueDate(submitDate, 8));
    }

    @Test
    public void testInvalidTurnaround() {
        LocalDateTime submitDate = LocalDateTime.parse("2019-03-04 08:30", formatter);
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateDueDate(submitDate, -1));
    }

    @Test
    public void testDueDateCalculationNoWeekend() {
        LocalDateTime submitDate = LocalDateTime.parse("2019-03-04 09:30", formatter);
        LocalDateTime dueDate = LocalDateTime.parse("2019-03-05 09:30", formatter);
        assertEquals(dueDate, calculator.calculateDueDate(submitDate, 8));
    }

    @Test
    public void testDueDateCalculationWithWeekend() {
        LocalDateTime submitDate = LocalDateTime.parse("2019-03-04 09:30", formatter);
        LocalDateTime dueDate = LocalDateTime.parse("2019-03-15 09:30", formatter);
        assertEquals(dueDate, calculator.calculateDueDate(submitDate, 72));
    }

    @Test
    public void testDueDateCalculationWithOverflowingHours() {
        LocalDateTime submitDate = LocalDateTime.parse("2019-03-04 12:00", formatter);
        LocalDateTime dueDate = LocalDateTime.parse("2019-03-05 10:00", formatter);
        assertEquals(dueDate, calculator.calculateDueDate(submitDate, 6));
    }

    @Test
    public void testDueDateCalculationWithOverflowingHoursAndWeekend() {
        LocalDateTime submitDate = LocalDateTime.parse("2019-03-14 15:00", formatter);
        LocalDateTime dueDate = LocalDateTime.parse("2019-03-19 11:00", formatter);
        assertEquals(dueDate, calculator.calculateDueDate(submitDate, 20));
    }



}
