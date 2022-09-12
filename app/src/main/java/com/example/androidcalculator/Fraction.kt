package com.example.androidcalculator

class Fraction(numerator: Long, denominator: Long = 1) {
    private val numerator: Long
    private val denominator: Long

    init {
        if (denominator == 0.toLong()) {
            throw java.lang.ArithmeticException("denominator cannot be zero")
        }

        if (denominator == 1.toLong()) {
            this.numerator = numerator
            this.denominator = 1
        } else {
//            reduction
            var a: Long = numerator
            var b: Long = denominator
            var r: Long

            while (true) {
                r = a % b
                if (r == 0.toLong()){
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

    fun getNumerator(): Long {
        return numerator
    }

    fun getDenominator(): Long {
        return denominator
    }
}
