package com.example.androidcalculator

class Calculator {
    enum class Symbols {
        NUM, ADD, SUB, MULTI, DIV
    }

    private var calcOrder: List<Symbols> = listOf(
        Symbols.DIV,
        Symbols.MULTI,
        Symbols.ADD,
        Symbols.SUB
    )

    private var currentInput: String = ""
    private var formula: String = ""
    private var lastInput: Symbols = Symbols.NUM

    private var numbers: MutableList<Fraction> = mutableListOf()
    private var symbols: MutableList<Symbols> = mutableListOf()

    fun putSymbol(s: Symbols) {
        if (lastInput == Symbols.NUM) {
            setNumber()
            formula += "${getSafetyCurrentInput()} ${getSymbolString(s)} "
        } else {
            symbols.removeLast()
            formula = formula.dropLast(2) + "${getSymbolString(s)} "
        }
        symbols.add(s)
        lastInput = s
        currentInput = ""
    }

    fun putNumber(c: Char) {
        if (c.code !in 48..57 && c != '.') return
        lastInput = Symbols.NUM
        if (c == '.') {
            if (currentIsZero()) {
                currentInput = "0."
            } else if (currentInput.indexOf('.') == -1 ) {
                currentInput += "."
            }
        } else {
            if (currentIsZero()) {
                if (c == '0') return
            }
            currentInput += c.toString()
        }
    }

    fun equal(): String {
        setNumber()
        if (lastInput != Symbols.NUM) {
            symbols.removeLast()
            numbers.removeLast()
        }
        return calculate().toString()
    }

    fun getFormula(): String {
        if (formula == "") return getSafetyCurrentInput()
        return formula + getSafetyCurrentInput()
    }

    private fun calculate(): Float? {
        val tmpNumbers: MutableList<Fraction> = numbers.toMutableList()
        val tmpSymbols: MutableList<Symbols> = symbols.toMutableList()
        for (s in calcOrder) {
//            TODO:optimization
            var i = 0
            while (i < numbers.count()) {
                when (tmpSymbols[i]) {
                    Symbols.ADD -> {
                        tmpNumbers[i + 1] = tmpNumbers[i].getAdd(tmpNumbers[i + 1])
                        tmpNumbers.removeAt(i)
                        tmpSymbols.removeAt(i)
                        i++
                    }
                    Symbols.SUB -> {
                        tmpNumbers[i + 1] = tmpNumbers[i].getSub(tmpNumbers[i + 1])
                        tmpNumbers.removeAt(i)
                        tmpSymbols.removeAt(i)
                        i++
                    }
                    Symbols.MULTI -> {
                        tmpNumbers[i + 1] = tmpNumbers[i].getMulti(tmpNumbers[i + 1])
                        tmpNumbers.removeAt(i)
                        tmpSymbols.removeAt(i)
                        i++
                    }
                    Symbols.DIV -> {
                        val resultDiv = tmpNumbers[i].getDiv(tmpNumbers[i + 1])
                        if (resultDiv == null) {
                            return null
                        } else {
                            tmpNumbers[i + 1] = resultDiv
                        }
                        tmpNumbers.removeAt(i)
                        tmpSymbols.removeAt(i)
                        i++
                    }
                    Symbols.NUM -> {
                        return null
                    }
                }
                i++
            }
        }
        return tmpNumbers[0].getAsFloat()
    }

    private fun currentIsZero(): Boolean {
        if (currentInput == "0.") return false
        if (currentInput == "") return true
        if (currentInput.toFloat() == 0F) return true
        return false
    }

    private fun getSafetyCurrentInput(): String {
        if (currentIsZero()) return "0"
        if (currentInput.substring(currentInput.length) == ".") return currentInput.dropLast(1)
        return currentInput
    }

    private fun setNumber() {
        if (currentIsZero()) currentInput = "0"
        val pointIndex = currentInput.indexOf('.')
        if (pointIndex == -1) {
            numbers.add(Fraction(currentInput.toInt(), 1))
        } else {
            val split = currentInput.split('.')
            val numerator: String = split[0] + split[1]
            var denominator = "1"
            for (i in 0..currentInput.count() - 2 - pointIndex) {
                denominator += "0"
            }
            numbers.add(Fraction(numerator.toInt(), denominator.toInt()))
        }
    }

    private fun getSymbolString(s: Symbols): String {
        return when(s) {
            Symbols.ADD -> {
                "+"
            }
            Symbols.SUB -> {
                "-"
            }
            Symbols.MULTI -> {
                "×"
            }
            Symbols.DIV -> {
                "÷"
            }
            else -> {
                currentInput
            }
        }
    }
}