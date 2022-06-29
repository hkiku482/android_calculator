package com.example.androidcalculator

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun calculatorInput() {
        val calc1 = Calculator()
        calc1.putRune('0')
        calc1.putRune('0')
        assertEquals(calc1.getCurrentInput(), "")
        calc1.putRune('.')
        calc1.putRune('0')
        assertEquals(calc1.getCurrentInput(), "0.0")
        calc1.putRune('1')
        calc1.putRune('+')
        assertEquals(calc1.getFormula(), "0.01 + ")
        calc1.putRune('*')
        assertEquals(calc1.getFormula(), "0.01 * ")
        calc1.putRune('2')
        calc1.putRune('*')
        calc1.putRune('3')
        calc1.putRune('=')
        assertEquals(calc1.getFormula(), "0.01 * 2 * 3")

        val calc2 = Calculator()
        calc2.putRune('+')
        calc2.putRune('9')
        calc2.putRune('-')
        calc2.putRune('=')
        assertEquals(calc2.getFormula(), "0 + 9 - 0")
    }
}