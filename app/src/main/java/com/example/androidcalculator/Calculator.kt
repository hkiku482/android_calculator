package com.example.androidcalculator

import android.graphics.Path

class Calculator(presenter: CalculatorPresenter) {
    private val presenter: CalculatorPresenter
    private val formula: Formula

//    registers
    private var currentNumber: String
    private var lastOperator: Operator

//    flag
    private var lastInputIsOperator: Boolean
    private var isFirstInput: Boolean

    init {
        this.presenter = presenter
        this.formula = Formula(Fraction(0))

        this.currentNumber = "0"
        this.isFirstInput = true
        this.lastOperator = Operator.EQUAL

        this.lastInputIsOperator = false
        this.isFirstInput = true
    }

    fun putNumber(number: Char) {
//        number: Char is not 0-9 or .
        if (number.code !in 48..57 && number != '.') {
            throw java.lang.Exception("invalid syntax :$number")
        }
//        . has already entered
        if (number == '.' && this.currentNumber.indexOf('.') != -1) {
            return
        }

        if (currentNumber == "0") {
            currentNumber = ""
        }

        currentNumber += number
        presenter.setPrimaryDisplay(currentNumber)
        lastInputIsOperator = false
    }

    fun putOperator(operator: Operator) {
        if (currentNumber.last() == '.') {
            currentNumber = currentNumber.substring(0, currentNumber.length -1)
        }

        var display = "${presenter.getFormulaDisplay()} "

//        first input
        if (isFirstInput) {
            formula.replaceFirstFraction(toFraction(currentNumber))
            display = "$currentNumber "
            lastOperator = operator
        }

        if (lastInputIsOperator) {
            display = "${display.substring(0, display.length - 2)} "
        }

        when(operator) {
            Operator.ADD -> {
                display += "+"
            }
            Operator.SUB -> {
                display += "-"
            }
            Operator.MULTI -> {
                display += "×"
            }
            Operator.DIV -> {
                display += "÷"
            }
            Operator.EQUAL -> {
                display += "="
            }
        }

        if (!isFirstInput) {
            when(this.lastOperator) {
                Operator.ADD -> {
                    formula.push(Symbol.ADD, toFraction(""))
                }
                Operator.SUB -> {
                    val f = toFraction(currentNumber)
                    formula.push(Symbol.ADD, Fraction(f.getNumerator() * (-1), f.getDenominator()))
                }
                Operator.MULTI -> {
                    formula.push(Symbol.MUL, toFraction(""))
                }
                Operator.DIV -> {
                    val f = toFraction(currentNumber)
                    formula.push(Symbol.MUL, Fraction(f.getNumerator(), f.getDenominator()))
                }
                Operator.EQUAL -> {
                }
            }
        }

        presenter.setPrimaryDisplay(formula.calculate().toString())
        presenter.setFormulaDisplay(display)

        currentNumber = "0"
        lastOperator = operator

        lastInputIsOperator = true
        isFirstInput = false
    }

    private fun toFraction(number: String): Fraction {
        return if (number.indexOf('.') == -1) {
            Fraction(number.toInt())
        } else {
            val split = currentNumber.split('.')
            val n = split[0] + split[1]
            var d = "1"
            for (i in 0..currentNumber.length - 2 - currentNumber.indexOf('.')) {
                d += "0"
            }
            Fraction(n.toInt(), d.toInt())
        }
    }

    private fun calculate() {}
}