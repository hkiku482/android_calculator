package com.example.androidcalculator

class Calculator(presenter: CalculatorPresenter) {
    private val presenter: CalculatorPresenter
    private var formula: Formula

//    registers
    private var currentNumber: String
    private var lastOperator: Operator
    private var lastSymbol: Symbol
    private var lastNum: String

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
        this.lastNum = ""

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

        if (number != '.' && currentNumber == "0") {
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
            if (operator != Operator.EQUAL) {
                lastOperator = operator
            }
        } else {
            display += "$currentNumber "
        }

        if (equalEntered) {
            if (operator == Operator.EQUAL) {
                retry()
                return
            } else {
                this.equalEntered = false
                currentNumber = getResult(presenter.getPrimaryDisplay())
                formula = Formula(Fraction(0))
                formula.replaceFirstFraction(toFraction(currentNumber))
                display = "$currentNumber "
                presenter.setFormulaDisplay(display)
            }
        }

        if (lastInputIsOperator) {
            display = "${display.substring(0, display.length - 5)} "
        }

        display += setOperator(operator)

        if (!isFirstInput) {
            if (!this.lastInputIsOperator) {
                this.updateFormula()
            }
            if (operator == Operator.EQUAL) {
                this.lastNum = this.currentNumber
                val result = formula.calculate().getAsFloat().toString()
                initialization()
                presenter.setPrimaryDisplay(getResult(result))
                presenter.setFormulaDisplay(display)
                equalEntered = true
                return
            }
        }

        presenter.setPrimaryDisplay(getResult(formula.calculate().getAsFloat().toString()))
        presenter.setFormulaDisplay(display)

        currentNumber = "0"

        if (operator != Operator.EQUAL) {
            lastOperator = operator
        }

        lastInputIsOperator = true
        isFirstInput = false
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

        val lastResult = getResult(presenter.getPrimaryDisplay())
        val number = tmp[tmp.count() - 2]
        val newFormula = "${presenter.getPrimaryDisplay()} ${setOperator(lastOperator)} $number ="

        formula = Formula((Fraction(0)))
        formula.replaceFirstFraction(toFraction(lastResult))

        currentNumber = this.lastNum

        this.updateFormula()

        val result = formula.calculate().getAsFloat().toString()

        presenter.setPrimaryDisplay(getResult(result))
        presenter.setFormulaDisplay(newFormula)
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
                formula.push(Symbol.MUL, Fraction(f.getDenominator(), f.getNumerator()))
            }
            Operator.EQUAL -> {}
        }
    }
}