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
            if (this.lastCalculationOperator == Operator.EQUAL) {
                return
            }
            this.cFormula = this.cFormula.retry(this.lastCalculationResult)
            this.cFormula.pushOperator(Operator.EQUAL)
            this.lastCalculationResult = this.cFormula.getResult()

            this.presenter.setPrimaryDisplay(this.cFormula.getResult())
            this.presenter.setFormulaDisplay(this.cFormula.getFormula())
        } else {
            this.lateEntry = false
//            entered [sin | cos | tan]
            if (lateEntry) {
                this.cFormula.pushNumber(currentNumber)
            }

            if (operator == Operator.ADD || operator == Operator.SUB || operator == Operator.MUL || operator == Operator.DIV) {
//                four arithmetic operations
                if (this.continuousEqual) {
                    this.cFormula = CalculatorFormula()
                    this.cFormula.pushNumber(this.lastCalculationResult)
                    this.continuousEqual = false
                    this.continuousOperator = false
                } else {
                    this.cFormula.pushNumber(this.currentNumber)
                }
                if (this.continuousOperator) {
                    this.cFormula.replaceLastOperator(operator)
                } else {
                    this.cFormula.pushOperator(operator)
                }
                this.continuousOperator = true
                this.lastCalculationOperator = operator
            } else if (operator == Operator.EQUAL) {
//                equal
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
                this.continuousOperator = true

//                    set presenter
                this.presenter.setPrimaryDisplay(this.cFormula.getResult())
                this.presenter.setFormulaDisplay(this.cFormula.getFormula())

//                    initialization
                this.lastNumber = this.currentNumber
                this.currentNumber = "0"
                this.lateEntry = false

                return
            } else if (operator == Operator.SIN || operator == Operator.COS || operator == Operator.TAN) {
//                trigonometric function
                if (this.continuousEqual) {
                    this.cFormula = CalculatorFormula()
                    this.cFormula.pushOperator(operator)
                    this.cFormula.pushNumber(this.lastCalculationResult)
                    this.continuousEqual = false
                    this.continuousOperator = false
                } else {
                    this.cFormula.pushOperator(operator)
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