package com.example.androidcalculator

import org.junit.Test
import org.junit.Assert.*

class CalculatorControllerUnitTest {
    class PresenterStub: CalculatorPresenter {
        var p: String = ""
        var f: String = ""

        override fun setPrimaryDisplay(status: String) {
            this.p = status
        }

        override fun setFormulaDisplay(formula: String) {
            this.f = formula
        }
    }

    @Test
    fun calculateTest() {
        val display = PresenterStub()
        val cc = CalculatorController(display)
        cc.putNumber('2')
        assertEquals("2", display.p)
        assertEquals("", display.f)
        cc.putOperator(Operator.ADD)
        assertEquals("2", display.p)
        assertEquals("2 +", display.f)
        cc.putNumber('3')
        assertEquals("3", display.p)
        assertEquals("2 +", display.f)
        cc.putOperator(Operator.MUL)
        assertEquals("5", display.p)
        assertEquals("2 + 3 ×", display.f)
        cc.putNumber('4')
        cc.putOperator(Operator.EQUAL)
        assertEquals("14", display.p)
        assertEquals("2 + 3 × 4 =", display.f)
    }

    @Test
    fun calculateTest2() {
        val display = PresenterStub()
        val cc = CalculatorController(display)
        cc.putOperator(Operator.TAN)
        cc.putNumber('4')
        cc.putNumber('5')
        cc.putOperator(Operator.MUL)
        assertEquals("1", display.p)
        cc.putNumber('3')
        cc.putOperator(Operator.ADD)
        cc.putOperator(Operator.COS)
        cc.putNumber('0')
        cc.putOperator(Operator.EQUAL)
        assertEquals("4", display.p)
        assertEquals("tan(45°) × 3 + cos(0°) =", display.f)
    }

    @Test
    fun calculateTest3() {
        val display = PresenterStub()
        val cc = CalculatorController(display)
        cc.putOperator(Operator.ADD)
        cc.putOperator(Operator.TAN)
        cc.putNumber('4')
        cc.putNumber('5')
        cc.putOperator(Operator.EQUAL)
        cc.putOperator(Operator.EQUAL)
        cc.putOperator(Operator.EQUAL)
        assertEquals("3", display.p)
    }

    @Test
    fun calculateTest4() {
        val display = PresenterStub()
        val cc = CalculatorController(display)
        cc.putOperator(Operator.TAN)
        cc.putNumber('4')
        cc.putNumber('5')
        cc.putOperator(Operator.EQUAL)
        cc.putOperator(Operator.EQUAL)
        assertEquals("1", display.p)
    }
}