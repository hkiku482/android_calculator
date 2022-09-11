package com.example.androidcalculator

import kotlin.math.sin
import kotlin.math.cos
import kotlin.math.tan

class CalculatorFormula {
    private var numbers: MutableList<CalculatorNumber?> = mutableListOf()
    private var operators: MutableList<Operator?> = mutableListOf()

    fun pushNumber(number: String) {
        this.numbers.add(CalculatorNumber(number))
        this.operators.add(null)
    }

    fun pushOperator(operator: Operator) {
        this.numbers.add(null)
        this.operators.add(operator)
    }

    fun getFormula(): String {
        if (this.numbers.isEmpty()) {
            return "0"
        }

        var str = ""
        var i = 0

        while (i < this.numbers.count()) {
            if (this.numbers[i] != null) {
                str += this.numbers[i]!!.getRowValue()
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
                        if (i + 1 <= this.numbers.count()) {
                            str += "$ope(${this.numbers[i+1]!!.getRowValue()}°)"
                            i++
                        } else {
                            str += "$ope()"
                        }
                    }
                    Operator.COS -> {
                        val ope = "cos"
                        if (i + 1 <= this.numbers.count()) {
                            str += "$ope(${this.numbers[i+1]!!.getRowValue()}°)"
                            i++
                        } else {
                            str += "$ope()"
                        }
                    }
                    Operator.TAN -> {
                        val ope = "tan"
                        if (i + 1 <= this.numbers.count()) {
                            str += "$ope(${this.numbers[i+1]!!.getRowValue()}°)"
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
        return str
    }

    fun calculate(): String {
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
            if (calcOperators[i] != null) {
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
        return formula.calculate().getAsFloat().toString()
    }
}