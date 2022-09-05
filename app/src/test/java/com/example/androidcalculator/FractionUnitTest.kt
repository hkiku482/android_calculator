package com.example.androidcalculator

import org.junit.Test

import org.junit.Assert.*

class FractionUnitTest {
    @Test
    fun getAsFloatTest() {
        var f:Fraction = Fraction(8)
        var expected: Float = 8F
        assertEquals(expected, f.getAsFloat())

        f = Fraction(1,2)
        expected = 0.5F
        assertEquals(expected, f.getAsFloat())
    }
}