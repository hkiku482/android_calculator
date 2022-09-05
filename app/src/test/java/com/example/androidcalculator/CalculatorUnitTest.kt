package com.example.androidcalculator

import org.junit.Test

import org.junit.Assert.*

class CalculatorUnitTest {
    private class MockDisplay: CalculatorPresenter {
        var pDisplay: String = ""
        var fDisplay: String = ""

        override fun setFormulaDisplay(formula: String) {
            fDisplay = formula
        }

        override fun getFormulaDisplay(): String {
            return fDisplay
        }

        override fun setPrimaryDisplay(result: String) {
            pDisplay = result
        }

        override fun getPrimaryDisplay(): String {
            return pDisplay
        }

    }

    @Test
    fun calculateTest() {
        val display = MockDisplay()
        val calc = Calculator(display)

        calc.putNumber('2')
        calc.putOperator(Operator.ADD)
        calc.putNumber('3')
        calc.putOperator(Operator.EQUAL)
        val actual = display.pDisplay
        val expected: String = 5.0F.toString()
        assertEquals(expected, actual)
    }
}