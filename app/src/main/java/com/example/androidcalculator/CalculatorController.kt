package com.example.androidcalculator

class CalculatorController(presenter: CalculatorPresenter) {
    private val presenter: CalculatorPresenter
    private var cFormula: CalculatorFormula = CalculatorFormula()

//    registers
    private var currentNumber: String = "0"
    private var lastNumber: String = "0"
    private var lastCalculationResult: String = ""
    private var lastCalculationOperator: Operator = Operator.EQUAL

//    flags
    private var continuousEqual: Boolean = false
    private var continuousOperator: Boolean = false
    private var lateEntry: Boolean = false

    init {
        this.presenter = presenter
    }

    fun clear() {
        this.currentNumber = "0"
        if (lateEntry) {
            this.cFormula.popLast()
            this.lateEntry = false
        }
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

//        maximum length that the user can enter
        if (this.currentNumber.length == 11) {
            return
        }

        this.currentNumber += number
        this.continuousEqual = false
        this.continuousOperator = false
        this.presenter.setPrimaryDisplay(this.currentNumber)
    }

    fun putOperator(operator: Operator) {
//        Operator.Equal is entered continuously
        if (this.continuousEqual && operator == Operator.EQUAL) {
            this.cFormula = CalculatorFormula()
            this.cFormula.pushNumber(this.lastCalculationResult)
            this.cFormula.pushOperator(this.lastCalculationOperator)
            this.cFormula.pushNumber(this.lastNumber)
            this.cFormula.pushOperator(Operator.EQUAL)

            this.presenter.setPrimaryDisplay(this.cFormula.getResult())
            this.presenter.setFormulaDisplay(this.cFormula.getFormula())
        } else {
            this.continuousEqual = false
//            entered [sin | cos | tan]
            if (lateEntry) {
                this.cFormula.pushNumber(currentNumber)
            }

            when(operator) {
                Operator.ADD -> {
                    val ope = Operator.ADD
                    this.cFormula.pushNumber(this.currentNumber)
                    if (this.continuousOperator) {
                        this.cFormula.replaceLastOperator(ope)
                    } else {
                        this.cFormula.pushOperator(ope)
                    }
                    this.continuousOperator = true
                    this.lastCalculationOperator = ope
                }
                Operator.SUB -> {
                    val ope = Operator.SUB
                    this.cFormula.pushNumber(this.currentNumber)
                    if (this.continuousOperator) {
                        this.cFormula.replaceLastOperator(ope)
                    } else {
                        this.cFormula.pushOperator(ope)
                    }
                    this.continuousOperator = true
                    this.lastCalculationOperator = ope
                }
                Operator.MUL -> {
                    val ope = Operator.MUL
                    this.cFormula.pushNumber(this.currentNumber)
                    if (this.continuousOperator) {
                        this.cFormula.replaceLastOperator(ope)
                    } else {
                        this.cFormula.pushOperator(ope)
                    }
                    this.continuousOperator = true
                    this.lastCalculationOperator = ope
                }
                Operator.DIV -> {
                    val ope = Operator.DIV
                    this.cFormula.pushNumber(this.currentNumber)
                    if (this.continuousOperator) {
                        this.cFormula.replaceLastOperator(ope)
                    } else {
                        this.cFormula.pushOperator(ope)
                    }
                    this.continuousOperator = true
                    this.lastCalculationOperator = ope
                }
                Operator.EQUAL -> {
                    val ope = Operator.EQUAL
                    this.cFormula.pushNumber(this.currentNumber)
                    if (this.continuousOperator) {
                        this.cFormula.replaceLastOperator(ope)
                    } else {
                        this.cFormula.pushOperator(ope)
                    }

//                    prepare to continue equal entering
                    this.lastCalculationResult = this.cFormula.getResult()
                    this.continuousEqual = true

//                    set presenter
                    this.presenter.setPrimaryDisplay(this.cFormula.getResult())
                    this.presenter.setFormulaDisplay(this.cFormula.getFormula())

//                    initialization
                    this.lastNumber = this.currentNumber
                    this.currentNumber = "0"
                    this.continuousOperator = false
                    this.lateEntry = false

                    return
                }
                Operator.SIN -> {
                    this.cFormula.pushOperator(Operator.SIN)
                    this.lateEntry = true
                }
                Operator.COS -> {
                    this.cFormula.pushOperator(Operator.COS)
                    this.lateEntry = true
                }
                Operator.TAN -> {
                    this.cFormula.pushOperator(Operator.TAN)
                    this.lateEntry = true
                }
            }
        }

        this.lastNumber = this.currentNumber
        this.currentNumber = "0"
        this.presenter.setPrimaryDisplay(this.cFormula.getResult())
        this.presenter.setFormulaDisplay(this.cFormula.getFormula())
    }
}