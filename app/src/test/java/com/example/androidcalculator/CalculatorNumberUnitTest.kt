package com.example.androidcalculator

import org.junit.Test
import org.junit.Assert.*

class CalculatorNumberUnitTest {
    @Test
    fun initializerTest() {
        var cn = CalculatorNumber("1.0E9")
        var expected = Fraction(1000000000)
        assertEquals(expected.getAsFloat(), cn.getFraction().getAsFloat())

        cn = CalculatorNumber("12E9")
        expected = Fraction(12000000000)
        assertEquals(expected.getAsFloat(), cn.getFraction().getAsFloat())

        cn = CalculatorNumber("1.23E-7")
        expected = Fraction(123,1000000000)
        assertEquals(expected.getAsFloat(), cn.getFraction().getAsFloat())

        cn = CalculatorNumber("1.0E-2")
        expected = Fraction(1,100)
        assertEquals(expected.getAsFloat(), cn.getFraction().getAsFloat())

        cn = CalculatorNumber("12.5")
        expected = Fraction(25,2)
        assertEquals(expected.getAsFloat(), cn.getFraction().getAsFloat())

        cn = CalculatorNumber("14")
        expected = Fraction(14)
        assertEquals(expected.getAsFloat(), cn.getFraction().getAsFloat())
    }
}