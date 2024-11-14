package org.example;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MathCalcTest {

    private final MathCalc calculator = new MathCalc(System.in);

    @Test
    public void additionTest() throws Exception{
        assertEquals(0.4d, calculator.calc("0.2 + 0.2"));
        assertEquals(4.2d, calculator.calc("4.0 + 0.2"));
        assertEquals(22.8d, calculator.calc("22 + 0.8"));
        assertEquals(10d, calculator.calc(" 9 + 0.5 + 0.5"));
        assertEquals(22.08d, calculator.calc("20 + 0.04 + 1.04 + 1"));
    }

    @Test
    public void subtractionTest() throws Exception{
        assertEquals(0d,calculator.calc("0.2 - 0.2"));
        assertEquals(1d, calculator.calc("0.5 - -0.5"));
        assertEquals(1.5d, calculator.calc(" 2- 0.5"));
        assertEquals(0.5d, calculator.calc(" 1 - 0.5"));
        assertEquals(8d, calculator.calc("  (9 - 0.5) -  0.5"));
    }

    @Test
    public void multiplicationTest() throws Exception{
        assertEquals(0.25d, calculator.calc("0.5 * 0.5"));
        assertEquals(1d, calculator.calc("  2* 0.5"));
        assertEquals(20d,calculator.calc("10 * 2"));
        assertEquals(24d, calculator.calc("  6 * (2 * 2)"));
    }

    @Test
    public void divisionTest() throws Exception{
        assertEquals(4d/2, calculator.calc("4/2"));
        assertEquals(10d, calculator.calc("  100 / 10.0"));
        assertEquals(11d, calculator.calc("22.0/2"));
        assertEquals(6d, calculator.calc("  12/(4/2)"));
    }

    @Test
    public void exponentiationTest() throws Exception{
        assertEquals(4d,calculator.calc("2^2"));
        assertEquals(8d,calculator.calc("2.0^3"));
        assertEquals(-32d,calculator.calc("-2^5"));
        assertEquals(16d,calculator.calc("2^2^2"));
    }

    @Test
    public void bracketsTest() throws Exception{
        assertEquals(-10d,calculator.calc("2 * (2 + 3) * -1"));
        assertEquals(41d, calculator.calc("(12 * 4) + 22 / (4 - 2) - 18"));
        assertEquals(-8d, calculator.calc("(2 * (2 - 2) + 64 / 8) * -1"));
        assertEquals(50d, calculator.calc("2 + 2 * ( 22 + 4/2)"));
    }

    @Test
    void testCalc_InvalidExpression() {
        MathCalc mathCalc = new MathCalc(System.in);

        // Тестирование невалидного выражения
        assertThrows(Exception.class, () -> mathCalc.calc("22 + * 8"));

        // Тестирование выражения с неизвестной операцией
        assertThrows(Exception.class, () -> mathCalc.calc("22 $ 8"));

        // Тестирование выражения с неподдерживаемой операцией
        assertThrows(Exception.class, () -> mathCalc.calc("22 % 8"));
    }

}