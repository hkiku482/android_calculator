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

    fun allClear() {
        this.cFormula = CalculatorFormula()

        this.currentNumber = "0"
        this.lastNumber = "0"
        this.lastCalculationResult = ""
        this.lastCalculationOperator = Operator.EQUAL
        this.continuousEqual = false
        this.continuousOperator = false
        this.lateEntry = false

        this.presenter.setPrimaryDisplay("0")
        this.presenter.setFormulaDisplay("0")
    }

    fun clear() {
        this.currentNumber = "0"
        if (lateEntry) {
            this.cFormula.popLast()
            this.lateEntry = false
        }

        this.presenter.setPrimaryDisplay(this.currentNumber)
    }

    fun backSpace() {
        if (this.currentNumber != "") {
            this.currentNumber = this.currentNumber.dropLast(1)
        }
        if (this.currentNumber == "") {
            this.currentNumber = "0"
        }
        this.presenter.setPrimaryDisplay(this.currentNumber)
    }

    fun putConstantNumber(constantValue: String) {
        this.currentNumber = constantValue
        this.continuousEqual = false
        this.continuousOperator = false
        this.presenter.setPrimaryDisplay(constantValue)
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
            this.cFormula = this.cFormula.retry(this.lastCalculationResult)
            this.cFormula.pushOperator(Operator.EQUAL)
            this.lastCalculationResult = this.cFormula.getResult()

            this.presenter.setPrimaryDisplay(this.cFormula.getResult())
            this.presenter.setFormulaDisplay(this.cFormula.getFormula())
        } else {
            this.continuousEqual = false
            this.lateEntry = false
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

            if (!this.lateEntry) {
                this.lastNumber = this.currentNumber
                this.currentNumber = "0"

                this.presenter.setPrimaryDisplay(this.cFormula.getResult())
                this.presenter.setFormulaDisplay(this.cFormula.getFormula())
            }
        }

    }
}