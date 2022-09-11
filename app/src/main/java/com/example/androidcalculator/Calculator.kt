package com.example.androidcalculator

import kotlin.math.pow

class Calculator(presenter: CalculatorPresenter) {
    private val presenter: CalculatorPresenter
    private var formula: Formula

//    registers
    private var currentNumber: String
    private var lastOperator: Operator
    private var lastSymbol: Symbol
    private var lastNum: Fraction

//    flag
    private var lastInputIsOperator: Boolean
    private var isFirstInput: Boolean
    private var equalEntered: Boolean

    init {
        this.presenter = presenter
        this.formula = Formula(Fraction(0))

        this.currentNumber = "0"
        this.lastOperator = Operator.EQUAL
        this.lastSymbol = Symbol.ADD
        this.lastNum = Fraction(0)

        this.lastInputIsOperator = false
        this.isFirstInput = true
        this.equalEntered = false
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

        if (number != '.' && this.currentNumber == "0") {
            this.currentNumber = ""
        }

        if (this.currentNumber.length == 11) {
            return
        }

        this.currentNumber += number

        this.presenter.setPrimaryDisplay(this.currentNumber)
        this.lastInputIsOperator = false
    }

    fun putOperator(operator: Operator) {
        if (this.currentNumber.last() == '.') {
            this.currentNumber = this.currentNumber.substring(0, this.currentNumber.length -1)
        }

        var display = "${this.presenter.getFormulaDisplay()} "

//        first input
        if (this.isFirstInput) {
            this.formula.replaceFirstFraction(toFraction(this.currentNumber))
            display = "${this.currentNumber} "
            if (operator != Operator.EQUAL) {
                this.lastOperator = operator
            }
        } else {
            display += "${this.currentNumber} "
        }

        if (this.equalEntered) {
            if (operator == Operator.EQUAL) {
                retry()
                return
            } else {
                this.equalEntered = false
                this.currentNumber = lastNum.getAsFloat().toString()
                this.formula = Formula(Fraction(0))
                this.formula.replaceFirstFraction(toFraction(this.currentNumber))
                display = "${this.currentNumber} "
                this.presenter.setFormulaDisplay(display)
            }
        }

        if (this.lastInputIsOperator) {
            display = "${display.substring(0, display.length - 5)} "
        }

        display += setOperator(operator)

        if (!this.isFirstInput) {
            if (!this.lastInputIsOperator) {
                this.updateFormula()
            }
            if (operator == Operator.EQUAL) {
                this.lastNum = this.formula.calculate()
                val result = this.lastNum.getAsFloat().toString()
                initialization()
                this.presenter.setPrimaryDisplay(getResult(result))
                this.presenter.setFormulaDisplay(display)
                this.equalEntered = true
                return
            }
        }

        this.presenter.setPrimaryDisplay(getResult(this.formula.calculate().getAsFloat().toString()))
        this.presenter.setFormulaDisplay(display)

        this.currentNumber = "0"

        if (operator != Operator.EQUAL) {
            this.lastOperator = operator
        }

        this.lastInputIsOperator = true
        this.isFirstInput = false
    }

    fun allClear() {
        this.formula = Formula(Fraction(0))

        this.currentNumber = "0"
        this.isFirstInput = true
        this.lastOperator = Operator.EQUAL

        this.lastInputIsOperator = false
        this.isFirstInput = true
        this.equalEntered = false
        presenter.setPrimaryDisplay("0")
        presenter.setFormulaDisplay("0")
    }

    fun clear() {
        this.currentNumber = "0"
        this.lastInputIsOperator = true
        presenter.setPrimaryDisplay(this.currentNumber)
    }

    fun backSpace() {
        if (this.currentNumber == "0") {
            return
        }

        this.currentNumber = this.currentNumber.substring(0, this.currentNumber.length - 1)

        if (this.currentNumber == "") {
            this.currentNumber = "0"
        }

        presenter.setPrimaryDisplay(this.currentNumber)
    }

    private fun initialization() {
        this.formula = Formula(Fraction(0))

        this.currentNumber = "0"
        this.isFirstInput = true

        this.lastInputIsOperator = false
        this.isFirstInput = true
    }

    private fun retry() {
        val tmp = presenter.getFormulaDisplay().split(' ')

//        val lastResult = getResult(presenter.getPrimaryDisplay())
        val lastResult = this.lastNum.getAsFloat().toString()

        val number = tmp[tmp.count() - 2]

        val newFormula = if (this.lastOperator == Operator.EQUAL) {
            "${this.presenter.getPrimaryDisplay()} ="
        } else {
            "${this.presenter.getPrimaryDisplay()} ${setOperator(this.lastOperator)} $number ="
        }

        this.formula = Formula((Fraction(0)))
        this.formula.replaceFirstFraction(toFraction(lastResult))

        this.currentNumber = lastResult
        this.updateFormula()

        this.lastNum = formula.calculate()
        val result = this.formula.calculate().getAsFloat().toString()

        this.presenter.setPrimaryDisplay(getResult(result))
        this.presenter.setFormulaDisplay(newFormula)
    }

//    1.23456E10
//    1.23456 00000
//    12345600000
//    1.23456E-10
//    0.000000000123456

    private fun toFraction(number: String): Fraction {
        if (number.indexOf('E') != -1) {
            val splitE = number.split('E')
            val dotIndex: Int
            var baseNum: String

            if (splitE[0].indexOf('.') != -1) {
                val splitDot = splitE[0].split('.')
                baseNum = splitDot[0] + splitDot[1]
                dotIndex = splitE[0].indexOf('.') + 1
            } else {
                baseNum = splitE[0]
                dotIndex = 0
            }

            if (splitE[1].toInt() > 0) {
                for (i in 0..splitE[1].toInt() - dotIndex) {
                    baseNum += "0"
                }
            } else {
                var tmp = "0."
                for (i in 0 until splitE[1].toInt()) {
                     tmp += "0"
                }
                baseNum = tmp + baseNum
            }

            return Fraction(baseNum.toLong(), 1)
        } else {
            return if (number.indexOf('.') == -1) {
                Fraction(number.toLong())
            } else {
                val split = number.split('.')
                val n =split[0] + split[1]
                var d = "1"
                for (i in 0..number.length - 2 - number.indexOf('.')) {
                    d += "0"
                }
                Fraction(n.toLong(), d.toLong())
            }
        }
    }

    private fun getResult(result: String): String {
        return if (result.length < 3) {
            result
        } else if (result.substring(result.length - 2, result.length) == ".0") {
            result.substring(0, result.length - 2)
        } else {
            result
        }
    }

    private fun setOperator(operator: Operator): String {
        when(operator) {
            Operator.ADD -> {
                return "+"
            }
            Operator.SUB -> {
                return "-"
            }
            Operator.MULTI -> {
                return "×"
            }
            Operator.DIV -> {
                return "÷"
            }
            Operator.EQUAL -> {
                return "="
            }
        }
    }

    private fun updateFormula() {
        when(this.lastOperator) {
            Operator.ADD -> {
                formula.push(Symbol.ADD, toFraction(currentNumber))
            }
            Operator.SUB -> {
                val f = toFraction(currentNumber)
                formula.push(Symbol.ADD, Fraction(f.getNumerator() * (-1), f.getDenominator()))
            }
            Operator.MULTI -> {
                formula.push(Symbol.MUL, toFraction(currentNumber))
            }
            Operator.DIV -> {
                val f = toFraction(currentNumber)
                if (f.getNumerator() == 0.toLong()) {
                    initialization()
                    presenter.setFormulaDisplay("0")
                    presenter.setPrimaryDisplay("0")
                    throw java.lang.ArithmeticException("エラー")
                }
                formula.push(Symbol.MUL, Fraction(f.getDenominator(), f.getNumerator()))
            }
            Operator.EQUAL -> {}
        }
    }
}