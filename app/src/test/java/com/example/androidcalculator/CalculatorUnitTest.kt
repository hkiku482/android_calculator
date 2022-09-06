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

        override fun setPrimaryDisplay(status: String) {
            pDisplay = status
        }

        override fun getPrimaryDisplay(): String {
            return pDisplay
        }

    }

    @Test
    fun calculateTest() {
        val display = MockDisplay()
        val calc = Calculator(display)
        calc.putNumber('3')
        calc.putOperator(Operator.ADD)
        calc.putNumber('7')
        calc.putOperator(Operator.MULTI)
        calc.putNumber('2')
        calc.putOperator(Operator.ADD)
        calc.putNumber('1')
        calc.putOperator(Operator.EQUAL)
        val actual1 = display.pDisplay
        val expected1: String = 18.0F.toString()
        assertEquals(expected1, actual1)

        val actual2 = display.fDisplay
        val expected2 = "3 + 7 × 2 + 1 ="
        assertEquals(expected2, actual2)
    }

    @Test
    fun calculateTest2() {
        val display = MockDisplay()
        val calc = Calculator(display)
        calc.putNumber('0')
        calc.putNumber('0')
        assertEquals("0", display.pDisplay)
        assertEquals("", display.fDisplay)
        calc.putOperator(Operator.SUB)
        calc.putOperator(Operator.ADD)
        assertEquals("0 +", display.fDisplay)
        calc.putNumber('1')
        calc.putNumber('.')
        calc.putNumber('.')
        calc.putNumber('5')
        calc.putOperator(Operator.EQUAL)
        assertEquals("0 + 1.5 =", display.fDisplay)
        assertEquals("1.5", display.pDisplay)
    }

    @Test
    fun calculateTest3() {
        val display = MockDisplay()
        val calc = Calculator(display)
        calc.putOperator(Operator.ADD)
        calc.putNumber('1')
        calc.putOperator(Operator.EQUAL)
        calc.putOperator(Operator.EQUAL)
        calc.putOperator(Operator.EQUAL)
        assertEquals("3", display.pDisplay)
    }
}