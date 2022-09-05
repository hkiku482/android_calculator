package com.example.androidcalculator

class CalculatorDisplay: CalculatorPresenter {
    private var primaryDisplay: String = "0"
    private var formulaDisplay: String = "0"

    override fun setFormulaDisplay(formula: String) {
        formulaDisplay = formula
    }

    override fun getFormulaDisplay(): String {
        return formulaDisplay
    }

    override fun setPrimaryDisplay(status: String) {
        primaryDisplay = status
    }

    override fun getPrimaryDisplay(): String {
        return primaryDisplay
    }
}