package com.example.androidcalculator

interface CalculatorPresenter {
    fun setFormulaDisplay(formula: String)
    fun getFormulaDisplay(): String
    fun setPrimaryDisplay(status: String)
    fun getPrimaryDisplay(): String
}