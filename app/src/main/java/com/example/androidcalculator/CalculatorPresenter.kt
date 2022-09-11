package com.example.androidcalculator

interface CalculatorPresenter {
    fun setFormulaDisplay(formula: String)
    fun setPrimaryDisplay(status: String)
}