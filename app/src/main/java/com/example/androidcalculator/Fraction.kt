package com.example.androidcalculator

class Fraction(numerator: Int, denominator: Int = 1) {
    private val numerator: Int
    private val denominator:Int

    init {
        if (denominator == 0) {
            throw java.lang.ArithmeticException("denominator cannot be zero")
        }

        if (denominator == 1) {
            this.numerator = numerator
            this.denominator = 1
        } else {
//            reduction
            var a: Int = numerator
            var b: Int = denominator
            var r: Int

            while (true) {
                r = a % b
                if (r == 0){
                    break
                }
                a = b
                b = r
            }

            this.numerator = numerator / b
            this.denominator = denominator / b
        }
    }

    fun getAsFloat(): Float {
        return numerator.toFloat() / denominator.toFloat()
    }

    fun getNumerator(): Int {
        return numerator
    }

    fun getDenominator(): Int {
        return denominator
    }
}
