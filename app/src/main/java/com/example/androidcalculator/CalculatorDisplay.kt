package com.example.androidcalculator

import android.R
import android.content.Context
import android.widget.TextView

class CalculatorDisplay(context: Context): CalculatorPresenter {
    private var primaryDisplay: String = "0"
    private var formulaDisplay: String = "0"
    private var context: Context

    init {
        this.context = context
    }

    override fun setPrimaryDisplay(status: String) {
        primaryDisplay = status
    }

    override fun setFormulaDisplay(formula: String) {
        formulaDisplay = formula
    }
}