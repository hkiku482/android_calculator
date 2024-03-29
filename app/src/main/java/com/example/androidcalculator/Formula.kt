package com.example.androidcalculator

class Formula(fraction: Fraction) {
    private var fractions: MutableList<Fraction>
    private var symbols: MutableList<Symbol>

    init {
        this.fractions = mutableListOf(fraction)
        this.symbols = mutableListOf()
    }

    private fun getOperationOrder(): List<Symbol> {
        return listOf(
            Symbol.MUL,
            Symbol.ADD,
        )
    }

    fun push(symbol: Symbol, fraction: Fraction) {
        fractions.add(fraction)
        symbols.add(symbol)
    }

    fun replaceFirstFraction(fraction: Fraction) {
        this.fractions[0] = fraction
    }

    fun calculate(): Fraction {
        val order = getOperationOrder()
//        val calcFormula: Formula = this
        val calcFormula = Formula(Fraction(0))

        calcFormula.fractions = mutableListOf()
        calcFormula.fractions.addAll(this.fractions)
        calcFormula.symbols.addAll(this.symbols)

        for (symbol in order) {
            var i = 0
            while (i < calcFormula.symbols.count()) {
                if (symbol == calcFormula.symbols[i]) {
                    when(calcFormula.symbols[i]) {
                        Symbol.MUL -> {
                            calcFormula.fractions[i] = Multiplier(calcFormula.fractions[i], calcFormula.fractions[i + 1]).calculate()
                            calcFormula.fractions.removeAt(i + 1)
                            calcFormula.symbols.removeAt(i)
                        }
                        Symbol.ADD -> {
                            calcFormula.fractions[i] = Adder(calcFormula.fractions[i], calcFormula.fractions[i + 1]).calculate()
                            calcFormula.fractions.removeAt(i + 1)
                            calcFormula.symbols.removeAt(i)
                        }
                    }
                    i--
                }
                i++
            }
        }

        if (calcFormula.fractions.isEmpty()) {
            throw java.lang.ArithmeticException("fractions is empty")
        } else {
            return calcFormula.fractions[0]
        }
    }
}