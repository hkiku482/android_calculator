package com.example.androidcalculator

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
        this.primaryDisplay = status
        (context as MainActivity).findViewById<TextView>(R.id.primaryView).text = this.primaryDisplay
    }

    override fun setFormulaDisplay(formula: String) {
        this.formulaDisplay = formula
        (context as MainActivity).findViewById<TextView>(R.id.secondaryView).text = this.primaryDisplay
    }
}