package com.example.androidcalculator

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

class CalculatorFormula {
    private var numbers: MutableList<CalculatorNumber?> = mutableListOf()
    private var operators: MutableList<Operator?> = mutableListOf()

    private fun formatNumber(d: Double): String {
        return if (d == d.toLong().toDouble()) String.format(
            "%d",
            d.toLong()
        ) else String.format("%s", d)
    }

    fun pushNumber(number: String) {
        this.numbers.add(CalculatorNumber(number))
        this.operators.add(null)
    }

    fun pushOperator(operator: Operator) {
        this.numbers.add(null)
        this.operators.add(operator)
    }

    fun popLast(){
        this.numbers.removeLast()
        this.operators.removeLast()
    }

    fun replaceLastOperator(operator: Operator) {
        var i = this.operators.count() - 1
        while (i > -1) {
            if (this.operators[i] != null) {
                this.operators[i] = operator
            }
            i--
        }
    }

    fun retry(lastResult: String): CalculatorFormula {
        val f = CalculatorFormula()

        var i = this.operators.count() - 1
        while (i > -1) {
            if (this.operators[i] != null) {
                if (this.operators[i] == Operator.ADD || this.operators[i] == Operator.SUB || this.operators[i] == Operator.MUL || this.operators[i] == Operator.DIV) {
                    break
                }
            }
            i--
        }

        f.numbers.add(CalculatorNumber(lastResult))
        f.operators.add(null)
        while (i < this.operators.count()) {
            if (this.operators[i] != Operator.EQUAL) {
                f.numbers.add(this.numbers[i])
                f.operators.add(this.operators[i])
            }
            i++
        }
        return f
    }

    fun getFormula(): String {
        if (this.numbers.isEmpty()) {
            return "0"
        }

        var str = ""
        var i = 0

        while (i < this.numbers.count()) {
            if (this.numbers[i] != null) {
                str += formatNumber(this.numbers[i]!!.getRowValue().toDouble())
                i++
            } else if (this.operators[i] != null) {
                when(this.operators[i]!!) {
                    Operator.ADD -> {
                        str += " + "
                    }
                    Operator.SUB -> {
                        str += " - "
                    }
                    Operator.MUL -> {
                        str += " × "
                    }
                    Operator.DIV -> {
                        str += " ÷ "
                    }
                    Operator.EQUAL -> {
                        str += " ="
                    }
                    Operator.SIN -> {
                        val ope = "sin"
                        if (i + 1 < this.numbers.count()) {
                            str += "$ope(${formatNumber(this.numbers[i]!!.getRowValue().toDouble())}°)"
                            i++
                        } else {
                            str += "$ope()"
                        }
                    }
                    Operator.COS -> {
                        val ope = "cos"
                        if (i + 1 < this.numbers.count()) {
                            str += "$ope(${formatNumber(this.numbers[i]!!.getRowValue().toDouble())}°)"
                            i++
                        } else {
                            str += "$ope()"
                        }
                    }
                    Operator.TAN -> {
                        val ope = "tan"
                        if (i + 1 < this.numbers.count()) {
                            str += "$ope(${formatNumber(this.numbers[i]!!.getRowValue().toDouble())}°)"
                            i++
                        } else {
                            str += "$ope()"
                        }
                    }
                }
                i++
            } else {
                throw java.lang.Error("class error")
            }
        }
        if (str.last() == ' ') {
            str = str.substring(0, str.length-1)
        }
        return str
    }

    fun getResult(): String {
        if (this.numbers.isEmpty()) {
            return "0"
        }

        val formula = Formula(Fraction(0))
        var isFirst = true

        val calcNumbers = mutableListOf<CalculatorNumber?>()
        val calcOperators = mutableListOf<Operator?>()
        calcNumbers.addAll(this.numbers)
        calcOperators.addAll(this.operators)

//        Operator first calculation
//        calculate and reset as CalculateNumber array
        var i = 0
        while (i < calcNumbers.count()) {
            if (calcOperators[i] != null) {
                when(calcOperators[i]!!) {
                    Operator.ADD -> {}
                    Operator.SUB -> {}
                    Operator.MUL -> {}
                    Operator.DIV -> {}
                    Operator.EQUAL -> {}
                    Operator.SIN -> {
                        val t = sin(Math.toRadians(calcNumbers[i+1]!!.getFraction().getAsFloat().toDouble()))
                        calcNumbers[i+1] = CalculatorNumber(t.toString())
                        calcNumbers.removeAt(i)
                        calcOperators.removeAt(i)
                        i--
                    }
                    Operator.COS -> {
                        val t = cos(Math.toRadians(calcNumbers[i+1]!!.getFraction().getAsFloat().toDouble()))
                        calcNumbers[i+1] = CalculatorNumber(t.toString())
                        calcNumbers.removeAt(i)
                        calcOperators.removeAt(i)
                        i--
                    }
                    Operator.TAN -> {
                        val t = tan(Math.toRadians(calcNumbers[i+1]!!.getFraction().getAsFloat().toDouble()))
                        calcNumbers[i+1] = CalculatorNumber(t.toString())
                        calcNumbers.removeAt(i)
                        calcOperators.removeAt(i)
                        i--
                    }
                }
            }
            i++
        }

//        Operator last calculation
        i = 0
        if (calcNumbers.isNotEmpty()) {
            formula.replaceFirstFraction(calcNumbers[0]!!.getFraction())
        } else {
            return "0"
        }
        while (i < calcNumbers.count()) {
            if (calcOperators[i] != null && i + 1 < calcNumbers.count()) {
                when(calcOperators[i]!!) {
                    Operator.ADD -> {
                        formula.push(Symbol.ADD, calcNumbers[i + 1]!!.getFraction())
                    }
                    Operator.SUB -> {
                        val f = calcNumbers[i + 1]!!.getFraction()
                        formula.push(Symbol.ADD, Fraction(f.getNumerator() * -1, f.getDenominator()))
                    }
                    Operator.MUL -> {
                        formula.push(Symbol.MUL, calcNumbers[i + 1]!!.getFraction())
                    }
                    Operator.DIV -> {
                        val f = calcNumbers[i + 1]!!.getFraction()
                        formula.push(Symbol.MUL, Fraction(f.getDenominator(), f.getNumerator()))
                    }
                    Operator.EQUAL -> {
                        break
                    }
                    Operator.SIN -> {}
                    Operator.COS -> {}
                    Operator.TAN -> {}
                }
            }
            i++
        }
        return this.formatNumber(formula.calculate().getAsFloat().toDouble())
    }
}