package com.example.androidcalculator

class Fraction(numerator: Int, denominator: Int) {
    private var numerator: Int
    private var denominator: Int

    init {
        this.numerator = numerator
        this.denominator = denominator
    }

    fun getAsFloat(): Float? {
        if (denominator == 0) return null
        return numerator.toFloat() / denominator.toFloat()
    }

    fun toggleSymbol(): Fraction {
        return Fraction(
            this.numerator * (-1),
            this.denominator
        )
    }

    fun getAdd(add: Fraction): Fraction {
        val f = Fraction(
            (this.numerator * add.denominator) + (this.denominator * add.numerator),
            this.denominator * add.denominator
        )
        return f.reduction()
    }

    fun getMulti(multi: Fraction): Fraction {
        val f = Fraction(
            this.numerator * multi.numerator,
            this.denominator * multi.denominator
        )
        return f.reduction()
    }

    fun getDiv(div: Fraction): Fraction? {
        if (div.numerator == 0) {
            return null
        }
        val f = Fraction(
            this.numerator * div.denominator,
            this.denominator * div.numerator
        )
        return f.reduction()
    }

    private fun reduction(): Fraction {
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
