package com.example.androidcalculator

import org.junit.Test
import org.junit.Assert.*

class CalculatorFormulaUnitTest {
    @Test
    fun getFormulaTest() {
        var cf = CalculatorFormula()
        cf.pushNumber("2")
        assertEquals("2", cf.getFormula())
        cf.pushOperator(Operator.ADD)
        assertEquals("2 +", cf.getFormula())
        cf.pushNumber("3")
        assertEquals("2 + 3", cf.getFormula())
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
        assertEquals("sin()", cf.getFormula())
        cf.pushNumber("10")
        assertEquals("sin(10°)", cf.getFormula())
        cf.pushOperator(Operator.ADD)
        assertEquals("sin(10°) +", cf.getFormula())
        cf.pushOperator(Operator.COS)
        cf.pushNumber("20")
        cf.pushOperator(Operator.ADD)
        cf.pushOperator(Operator.TAN)
        cf.pushNumber("30")
        cf.pushOperator(Operator.EQUAL)
        assertEquals("sin(10°) + cos(20°) + tan(30°) =", cf.getFormula())
    }

    @Test
    fun calculateTest() {
//        Four arithmetic operation
        var cf = CalculatorFormula()
        cf.pushNumber("2")
        cf.pushOperator(Operator.ADD)
        cf.pushNumber("3")
        cf.pushOperator(Operator.MUL)
        cf.pushNumber("4")
        cf.pushOperator(Operator.EQUAL)
        assertEquals("14.0", cf.getResult())

//        Trigonometric functions
        cf = CalculatorFormula()
        cf.pushOperator(Operator.COS)
        cf.pushNumber("0")
        cf.pushOperator(Operator.ADD)
        cf.pushNumber("3")
        cf.pushOperator(Operator.ADD)
        cf.pushOperator(Operator.TAN)
        cf.pushNumber("45")
        cf.pushOperator(Operator.DIV)
        cf.pushOperator(Operator.SIN)
        cf.pushNumber("90")
        cf.pushOperator(Operator.EQUAL)
        assertEquals("5.0", cf.getResult())

//        without equal
        cf = CalculatorFormula()
        cf.pushOperator(Operator.COS)
        cf.pushNumber("0")
        assertEquals("1.0", cf.getResult())

        cf = CalculatorFormula()
        cf.pushNumber("7")
        assertEquals("7.0", cf.getResult())

        cf = CalculatorFormula()
        assertEquals("0", cf.getResult())
    }
}