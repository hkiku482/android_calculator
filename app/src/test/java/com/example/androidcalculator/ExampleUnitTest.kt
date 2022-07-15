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
    fun inputTest() {
        val c = Calculator()
        c.putNumber('0')
        c.putNumber('0')
        assertEquals("0", c.getFormula())
        c.putSymbol(Calculator.Symbols.ADD)
        c.putNumber('2')
        c.putNumber('0')
        assertEquals("0 + 20", c.getFormula())
        c.putSymbol(Calculator.Symbols.SUB)
        c.putSymbol(Calculator.Symbols.DIV)
        c.putNumber('2')
        assertEquals("0 + 20 ÷ 2", c.getFormula())
        c.putSymbol(Calculator.Symbols.ADD)
        c.putNumber('.')
        c.putNumber('5')
        c.putSymbol(Calculator.Symbols.SUB)
        c.putNumber('1')
        c.putNumber('.')
        c.putNumber('.')
        c.putNumber('5')
        assertEquals("0 + 20 ÷ 2 + 0.5 - 1.5", c.getFormula())
        c.putSymbol(Calculator.Symbols.ADD)
        c.putNumber('1')
        c.putNumber('0')
        c.putSymbol(Calculator.Symbols.MULTI)
        c.putNumber('5')
        c.putSymbol(Calculator.Symbols.ADD)
        assertEquals("0 + 20 ÷ 2 + 0.5 - 1.5 + 10 × 5 + 0", c.getFormula())
        assertEquals("59", c.equal())
    }

    @Test
    fun errorTest() {
        val c = Calculator()
        c.putNumber('4')
        c.putSymbol(Calculator.Symbols.DIV)
        c.putNumber('0')
        assertEquals("エラー", c.equal())
    }

    @Test
    fun backSpaceTest() {
        val c = Calculator()
        c.putNumber('2')
        c.putNumber('0')
        c.putSymbol(Calculator.Symbols.DIV)
        c.putNumber('2')
        c.putNumber('0')
        c.backSpace()
        assertEquals("10", c.equal())
    }

    @Test
    fun clearTest() {
        val c = Calculator()
        c.putNumber('2')
        c.putNumber('0')
        c.putSymbol(Calculator.Symbols.DIV)
        c.putNumber('2')
        c.putNumber('0')
        c.clear()
        c.putNumber('2')
        assertEquals("10", c.equal())
    }

    @Test
    fun allClearTest() {
        val c = Calculator()
        c.putNumber('3')
        c.putSymbol(Calculator.Symbols.MULTI)
        c.putNumber('7')
        c.allClear()
        c.putNumber('2')
        c.putNumber('0')
        c.putSymbol(Calculator.Symbols.DIV)
        c.putNumber('2')
        assertEquals("10", c.equal())
    }
}