package com.example.androidcalculator

class CalculatorDisplay: CalculatorPresenter {
    private var resultDisplay: String = "0"
    private var formulaDisplay: String = "0"

    override fun setFormulaDisplay(formula: String) {
        formulaDisplay = formula
    }

    override fun getFormulaDisplay(): String {
        return formulaDisplay
    }

    override fun setPrimaryDisplay(result: String) {
        resultDisplay = result
    }

    override fun getPrimaryDisplay(): String {
        return resultDisplay
    }
}