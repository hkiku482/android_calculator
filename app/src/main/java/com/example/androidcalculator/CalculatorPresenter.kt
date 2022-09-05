package com.example.androidcalculator

interface CalculatorPresenter {
    fun setFormulaDisplay(formula: String)
    fun getFormulaDisplay(): String
    fun setPrimaryDisplay(result: String)
    fun getPrimaryDisplay(): String
}