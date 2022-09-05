package com.example.androidcalculator

import org.junit.Test

import org.junit.Assert.*

class FormulaUnitTest {
    @Test
    fun calculateTest() {
        var formula = Formula(Fraction(2))
        formula.push(Symbol.ADD, Fraction(3))
        formula.push(Symbol.MUL, Fraction(4))
        var actual = formula.calculate()
        var expected = Fraction(14)
        assertEquals(expected.getAsFloat(), actual.getAsFloat())

        formula = Formula(Fraction(10, 5))
        formula.push(Symbol.MUL, Fraction(1, 4))
        actual = formula.calculate()
        expected = Fraction(1,2)
        assertEquals(expected.getAsFloat(), actual.getAsFloat())

        formula = Formula(Fraction(2))
        formula.push(Symbol.ADD, Fraction(3))
        formula.push(Symbol.MUL, Fraction(2))
        formula.push(Symbol.ADD, Fraction(4))
        formula.push(Symbol.MUL, Fraction(2))
        actual = formula.calculate()
        expected = Fraction(16)
        assertEquals(expected.getAsFloat(), actual.getAsFloat())
    }
}