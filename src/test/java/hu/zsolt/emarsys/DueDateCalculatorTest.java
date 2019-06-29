package hu.zsolt.emarsys;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.dnd.DnDConstants;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DueDateCalculatorTest {

    private static DateTimeFormatter formatter;
    private DueDateCalculator calculator;

    @BeforeAll
    public static void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }

    @BeforeEach
    private void init() {
        calculator = new DueDateCalculator();
    }

    @Test
    void testNullSubmitDate() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateDuedate(null, 8));
    }

    @Test
    void testNullTurnaround() {
        LocalDateTime submitDate = LocalDateTime.parse("2019-03-04 08:30", formatter);
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateDuedate(submitDate, null));
    }

    @Test
    public void testInvalidSubmitDateBefore() {
        LocalDateTime submitDate = LocalDateTime.parse("2019-03-04 07:30", formatter);
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateDuedate(submitDate, 8));
    }

    @Test
    public void testInvalidSubmitDateAfter() {
        LocalDateTime submitDate = LocalDateTime.parse("2019-03-04 17:30", formatter);
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateDuedate(submitDate, 8));
    }

    @Test
    public void testInvalidTurnaround() {
        LocalDateTime submitDate = LocalDateTime.parse("2019-03-04 08:30", formatter);
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateDuedate(submitDate, -1));
    }


}