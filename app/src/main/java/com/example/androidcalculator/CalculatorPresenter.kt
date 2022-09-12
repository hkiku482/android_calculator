package com.example.androidcalculator

interface CalculatorPresenter {
    fun setPrimaryDisplay(status: String)
    fun setFormulaDisplay(formula: String)
}