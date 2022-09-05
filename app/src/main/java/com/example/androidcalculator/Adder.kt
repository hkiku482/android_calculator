package com.example.androidcalculator

class Adder(first: Fraction, second: Fraction) {
    private val first: Fraction
    private val second: Fraction

    init {
        this.first = first
        this.second = second
    }

    fun calculate(): Fraction {
        val n = first.getNumerator() * second.getDenominator() + first.getDenominator() * second.getNumerator()
        val d = first.getDenominator() * second.getDenominator()
        return Fraction(n, d)
    }
}