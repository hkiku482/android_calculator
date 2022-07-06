package com.example.androidcalculator

class Fraction(numerator: Int, denominator: Int) {
    private var numerator: Int
    private var denominator: Int

    init {
        this.numerator = numerator
        this.denominator = denominator
    }

    fun getAsInt(): Int {
        return numerator / denominator
    }

    fun reduction(): Fraction {
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

        return Fraction(this.numerator / b, this.denominator / b)
    }
}
