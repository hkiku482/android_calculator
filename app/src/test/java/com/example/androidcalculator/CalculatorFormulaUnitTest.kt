package com.example.androidcalculator

import org.junit.Test
import org.junit.Assert.*

class CalculatorFormulaUnitTest {
    @Test
    fun getFormulaTest() {
        var cf: CalculatorFormula = CalculatorFormula()
        cf.pushNumber("2")
        cf.pushOperator(Operator.ADD)
        cf.pushNumber("3")
        cf.pushOperator(Operator.MUL)
        cf.pushNumber("4")
        cf.pushOperator(Operator.SUB)
        cf.pushNumber("5")
        cf.pushOperator(Operator.DIV)
        cf.pushNumber("6")
        cf.pushOperator(Operator.EQUAL)
        assertEquals("2 + 3 × 4 - 5 ÷ 6 =", cf.getFormula())

        cf = CalculatorFormula()
        cf.pushOperator(Operator.SIN)
        cf.pushNumber("10")
        cf.pushOperator(Operator.ADD)
        cf.pushOperator(Operator.COS)
        cf.pushNumber("20")
        cf.pushOperator(Operator.ADD)
        cf.pushOperator(Operator.TAN)
        cf.pushNumber("30")
        cf.pushOperator(Operator.EQUAL)
        assertEquals("sin(10°) + cos(20°) + tan(30°) =", cf.getFormula())
    }
}