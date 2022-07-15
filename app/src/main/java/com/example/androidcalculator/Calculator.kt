package com.example.androidcalculator

class Calculator {
    enum class Symbols {
        NUM, ADD, SUB, MULTI, DIV
    }

    private var calcOrder: List<Symbols> = listOf(
        Symbols.DIV,
        Symbols.MULTI,
        Symbols.SUB,
        Symbols.ADD,
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
            formula = formula.dropLast(2)
        }
        formula += "$currentInput ="
        val r = calculate() ?: return "エラー"

        var resultStr = r.toString()
        if (resultStr.substring(resultStr.length - 2, resultStr.length) == ".0") {
            resultStr = resultStr.dropLast(2)
        }

        allClear()
        return resultStr
    }

    fun getFormula(): String {
        if (formula == "") return getSafetyCurrentInput()
        return formula + getSafetyCurrentInput()
    }

    fun backSpace() {
        currentInput = currentInput.dropLast(1)
    }

    fun allClear() {
        currentInput = ""
        formula = ""
        lastInput = Symbols.NUM
        numbers = mutableListOf()
        symbols = mutableListOf()
    }

    fun clear() {
        currentInput = ""
    }

    private fun calculate(): Float? {
        val tmpNumbers: MutableList<Fraction?> = numbers.toMutableList()
        val tmpSymbols: MutableList<Symbols> = symbols.toMutableList()

        for (s in calcOrder) {
            for ((i, n) in tmpNumbers.withIndex()) {
                if (tmpSymbols.count() == i) {
                    break
                }

                if (s == tmpSymbols[i]) {
                    val next = getNextIndex(tmpNumbers, i)
                    val first = tmpNumbers[i]
                    val second = tmpNumbers[next]
                    if ((first == null) || (second == null)) break
                    when (tmpSymbols[i]) {
                        Symbols.ADD -> {
                            tmpNumbers[next] = first.getAdd(second)
                            tmpNumbers[i] = null
                        }
                        Symbols.SUB -> {
                            tmpSymbols[i] = Symbols.ADD
                            tmpNumbers[next] = second.toggleSymbol()
                        }
                        Symbols.MULTI -> {
                            tmpNumbers[next] = first.getMulti(second)
                            tmpNumbers[i] = null
                        }
                        Symbols.DIV -> {
                            tmpNumbers[next] = first.getDiv(second)
                            if (tmpNumbers[next] == null) return null
                            tmpNumbers[i] = null
                        }
                        Symbols.NUM -> {
                            return null
                        }
                    }
                }
            }
        }
        return tmpNumbers.last()?.getAsFloat()
    }

    private fun getNextIndex(list: MutableList<Fraction?>, currentIndex: Int ): Int {
        var i = currentIndex + 1
        var r = list.count() - 1
        while (i < list.count()) {
            if (list[i] != null) {
                r = i
                break
            }
            i++
        }
        return r
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
            Symbols.NUM -> {
                currentInput
            }
        }
    }
}