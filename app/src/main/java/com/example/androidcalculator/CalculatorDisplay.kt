package com.example.androidcalculator

class CalculatorDisplay: CalculatorPresenter {
    private var primaryDisplay: String = "0"
    private var formulaDisplay: String = "0"

    override fun setFormulaDisplay(formula: String) {
        formulaDisplay = formula
    }

    override fun setPrimaryDisplay(status: String) {
        primaryDisplay = status
    }
}