package com.example.androidcalculator

class CalculatorNumber(number: String) {
    private val rowValue: String
    private val fraction: Fraction

    init {
        val safeNum = number.toBigDecimal().toPlainString()

        this.fraction = if (safeNum.indexOf('.') == -1) {
            Fraction(safeNum.toLong())
        } else {
            val split = safeNum.split('.')
            val n = split[0] + split[1]
            var d = "1"
            for (i in 0..safeNum.length - 2 - safeNum.indexOf('.')) {
                d += "0"
            }
            Fraction(n.toLong(), d.toLong())
        }
        this.rowValue = safeNum
    }

    fun getFraction(): Fraction {
        return fraction
    }

    fun getRowValue(): String {
        return rowValue
    }
}