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
        assertEquals("", calc1.getCurrentInput())
        calc1.putRune('.')
        calc1.putRune('0')
        assertEquals("0.0", calc1.getCurrentInput())
        calc1.putRune('1')
        calc1.putRune('+')
        assertEquals("0.01 + ", calc1.getFormula())
        calc1.putRune('*')
        assertEquals("0.01 * ", calc1.getFormula())
        calc1.putRune('2')
        calc1.putRune('*')
        calc1.putRune('3')
        calc1.putRune('=')
        assertEquals("0.01 * 2 * 3", calc1.getFormula())

        val calc2 = Calculator()
        calc2.putRune('+')
        calc2.putRune('9')
        calc2.putRune('-')
        calc2.putRune('=')
        assertEquals("0 + 9 - 0", calc2.getFormula())
    }

    @Test
    fun calculatorMakeData() {
        val calc1 = Calculator()
        val numbers: MutableList<Float> = mutableListOf(3F, 2F, 6F, 2F, 7F)
        val symbols: MutableList<Char> = mutableListOf('*', '/', '+', '+')
        calc1.putRune('2')
        calc1.putRune('+')
        calc1.putRune('3')
        calc1.putRune('*')
        calc1.putRune('2')
        calc1.putRune('/')
        calc1.putRune('6')
        calc1.putRune('+')
        calc1.putRune('1')
        calc1.putRune('7')
        calc1.putRune('+')
        calc1.putRune('=')
        var result: Float? = calc1.calculate()
        assertEquals(20, result!!.toInt())

        calc1.allClear()
        calc1.putRune('1')
        calc1.putRune('*')
        calc1.putRune('1')
        calc1.putRune('0')
        calc1.removeLastInput()
        calc1.removeLastInput()
        calc1.putRune('0')
        calc1.removeLastInput()
        calc1.removeLastInput()
        calc1.putRune('/')
        calc1.putRune('=')
        assertEquals(null, calc1.calculate())

        calc1.allClear()
        calc1.putRune('+')
        calc1.putRune('3')
        calc1.putRune('=')
        result = calc1.calculate()
        assertEquals(3, result!!.toInt())
    }
}