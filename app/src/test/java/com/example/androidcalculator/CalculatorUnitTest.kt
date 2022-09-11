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
        val expected1: String = "18"
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
        calc.putOperator(Operator.EQUAL)
        assertEquals("4", display.pDisplay)
        assertEquals("3 + 1 =", display.fDisplay)

        val display2 = MockDisplay()
        val calc2 = Calculator(display2)
        calc2.putNumber('1')
        calc2.putOperator(Operator.ADD)
        calc2.putNumber('1')
        calc2.putOperator(Operator.EQUAL)
        calc2.putOperator(Operator.EQUAL)
        calc2.putOperator(Operator.ADD)
        calc2.putNumber('1')
        calc2.putNumber('0')
        calc2.putOperator(Operator.EQUAL)
        assertEquals("13", display2.pDisplay)
        assertEquals("3 + 10 =", display2.fDisplay)
    }

    @Test
    fun calculatorTest4() {
        val display = MockDisplay()
        val calc = Calculator(display)
        calc.putNumber('1')
        calc.putNumber('2')
        calc.putNumber('3')
        calc.putNumber('4')
        calc.putNumber('5')
        calc.putNumber('6')
        calc.putNumber('7')
        calc.putNumber('8')
        calc.putNumber('9')
        calc.putOperator(Operator.ADD)
        calc.putNumber('8')
        calc.putNumber('7')
        calc.putNumber('6')
        calc.putNumber('5')
        calc.putNumber('4')
        calc.putNumber('3')
        calc.putNumber('2')
        calc.putNumber('1')
        calc.putNumber('0')
        calc.putOperator(Operator.EQUAL)
        assertEquals("1.0E9", display.pDisplay)
        calc.putOperator(Operator.SUB)
        calc.putNumber('1')
        calc.putNumber('0')
        calc.putNumber('0')
        calc.putNumber('0')
        calc.putNumber('0')
        calc.putNumber('0')
        calc.putNumber('0')
        calc.putNumber('0')
        calc.putNumber('0')
        calc.putNumber('0')
        calc.putOperator(Operator.EQUAL)
        assertEquals("1.0E9 - 1000000000 =", display.getFormulaDisplay())
        assertEquals("0", display.pDisplay)
    }

    @Test
    fun allClearTest() {
        val display = MockDisplay()
        val calc = Calculator(display)
        calc.putNumber('1')
        calc.putNumber('2')
        calc.putOperator(Operator.ADD)
        calc.putNumber('3')
        calc.allClear()
        assertEquals("0", display.pDisplay)
        assertEquals("0", display.pDisplay)
        calc.putNumber('2')
        calc.putNumber('2')
        calc.putOperator(Operator.ADD)
        calc.putNumber('3')
        calc.putOperator(Operator.EQUAL)
        assertEquals("25", display.pDisplay)
        assertEquals("22 + 3 =", display.fDisplay)
    }

    @Test
    fun clearTest() {
        val display = MockDisplay()
        val calc = Calculator(display)
        calc.putNumber('3')
        calc.putNumber('7')
        calc.putOperator(Operator.ADD)
        calc.putNumber('3')
        calc.putNumber('3')
        calc.clear()
        calc.putOperator(Operator.SUB)
        calc.putNumber('7')
        calc.putOperator(Operator.EQUAL)
        assertEquals("30", display.pDisplay)
        assertEquals("37 - 7 =", display.fDisplay)
    }

    @Test
    fun backSpaceTest() {
        val display = MockDisplay()
        val calc = Calculator(display)
        calc.putNumber('3')
        calc.putNumber('7')
        calc.putOperator(Operator.ADD)
        calc.putNumber('3')
        calc.putNumber('3')
        calc.backSpace()
        calc.putOperator(Operator.EQUAL)
        assertEquals("40", display.pDisplay)
        assertEquals("37 + 3 =", display.fDisplay)
    }
}