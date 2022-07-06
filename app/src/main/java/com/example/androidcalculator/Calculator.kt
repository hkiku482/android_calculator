package com.example.androidcalculator

class Calculator {
    enum class Symbols {
        NUM, ADD, SUB, MULTI, DIV
    }

    private var currentInput: String = ""
    private var formula: String = ""
    private var lastInput: Symbols = Symbols.NUM

    private var numbers: MutableList<Fraction> = mutableListOf()
    private var symbols: MutableList<Symbols> = mutableListOf()

    fun putSymbol(s: Symbols) {
        if (numbers.isEmpty()) {

        }
        if (lastInput == Symbols.NUM) {

        } else {

        }
        currentInput = ""
        lastInput = s
    }

    fun putNumber(c: Char) {
        if (c.code !in 48..57 || c != '.') return
        lastInput = Symbols.NUM
        if (c == '.') {
            if (currentIsZero()) {
                currentInput = "0."
            } else if (currentInput.indexOf('.') == -1 ) {
                currentInput += "."
            }
        } else {
            if (currentIsZero()) {
                if (c == '0') return
                currentInput = c.toString()
            } else {
                currentInput += c.toString()
            }
        }
    }

    fun equal() {
        setNumber()
    }

    private fun currentIsZero(): Boolean {
        if (currentInput == "0.") return false

        if (currentInput == "") return true
        if (currentInput.toFloat() == 0F) return true
        return false
    }

    private fun setNumber() {
        val pointIndex = currentInput.indexOf('.')
        if (pointIndex == -1) {
            numbers.add(Fraction(currentInput.toInt(), 1))
        } else {
            val split = currentInput.split('.')
            val numerator: String = split[0] + split[1]
            var denominator = "1"
            for (i in 0..currentInput.length - 1 - pointIndex) {
                denominator += "0"
            }
            numbers.add(Fraction(numerator.toInt(), denominator.toInt()))
        }
    }
}