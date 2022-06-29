package com.example.androidcalculator

class Calculator {
    enum class State{
        NUM, SYM
    }

    enum class Symbols {
        ADD, SUB, MULTI, DIV
    }

    private var currentInput: String = "0"
    private var formula: String = ""
    private var state: State = State.SYM

    private var numbersInputOrder: MutableList<Float> = mutableListOf()
    private var symbolsInputOrder: MutableList<Symbols> = mutableListOf()

    private var numbers: MutableList<Float> = mutableListOf()
    private var symbols: MutableList<Char> = mutableListOf()

    fun getFormula(): String {
        return formula
    }

    fun getCurrentInput(): String {
        return currentInput
    }

    fun putSymbol(s: Symbols) {
        if (numbersInputOrder.isEmpty()) {
            numbersInputOrder.add(0F)
        }
        when(state) {
            State.SYM -> {
                if (symbolsInputOrder.isNotEmpty()) {
                    symbolsInputOrder.removeLast()
                }
            }
            State.NUM -> {
                numbersInputOrder.add(currentInput.toFloat())
            }
        }
        when(s) {
            Symbols.ADD -> {
                symbolsInputOrder.add(s)

            }
            Symbols.SUB -> {
                symbolsInputOrder.add(s)

            }
            Symbols.MULTI -> {
                symbolsInputOrder.add(s)

            }
            Symbols.DIV -> {
                symbolsInputOrder.add(s)

            }
        }
        currentInput = "0"
        state = State.SYM
    }

    fun putNumber(c: Char) {
        if (c.code !in 48..57 || c != '.') return
        state = State.NUM
        if (c == '.') {
            if (currentIsZero()) {
                currentInput += "0."
            } else if (currentInput.indexOf('.') == -1 ) {
                currentInput += "."
            }
        } else {
            if (currentIsZero() && c == '0') return
            currentInput += c.toString()
        }
    }

    fun equal(): Float? {
        if (state == State.SYM || currentIsZero()) {
            numbersInputOrder.add(0F)
        } else {
            numbersInputOrder.add(currentInput.toFloat())
        }
        currentInput = "0"
        return calculate()
    }

    fun putRune(c: Char) {
        if (c.code in 48..57) {
//          (c: Char) is 0-9
//          if user input 0 -> 0...
            if (currentInput == "" && c == '0') {
                return
            }
            state = State.NUM
            currentInput += c.toString()
        } else if (c == '.') {
//          (C: Char) is dot
            state = State.NUM
            if (currentInput == "") {
                currentInput = "0."
            } else if (currentInput.indexOf('.') == -1){
                currentInput += "."
            }
        } else if (c == '=') {
            if (state == State.SYM) {
                currentInput = "0"
            } else if (currentInput == "") {
                currentInput = "0"
            }
            formula += currentInput
            currentInput = ""
        } else {
//          any symbol has been inputted
            when (state) {
                State.NUM -> {
                    state = State.SYM
                    formula += currentInput
                    formula += " $c "
                }
                State.SYM -> {
                    if (formula == "") {
                        formula = "0 $c "
                    }
                    formula = formula.dropLast(2) + "$c "
                }
            }
            currentInput = ""
        }
    }

    fun removeLastInput() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
        }
        if (currentInput.isEmpty() && formula.isNotEmpty()) {
            state = State.SYM
        }
    }

    fun allClear() {
        currentInput = ""
        formula = ""
        state = State.SYM
        numbers = mutableListOf()
        symbols = mutableListOf()
    }

    fun calculate(): Float? {
        makeData()
        for (i in 0 until numbers.size - 1) {
            val n1 = numbers.removeFirst()
            val n2 = numbers.removeFirst()
            val symbol = symbols.removeFirst()

            val tmp: Float?
            when (symbol) {
                '+' -> {
                    tmp = calcAdd(n1, n2)
                }
                '-' -> {
                    tmp = calcSub(n1, n2)
                }
                '*' -> {
                    tmp = calcMulti(n1, n2)
                }
                '/' -> {
                    tmp = calcDiv(n1, n2)
                    if (tmp == null) return null
                }
                else -> {
                    return null
                }
            }
            numbers.add(0, tmp)
        }
        return numbers[0]
    }

    private fun convertToChar(s: Symbols): Char {
        return when(s) {
            Symbols.ADD -> {
                '+'
            }
            Symbols.SUB -> {
                '-'
            }
            Symbols.MULTI -> {
                '*'
            }
            Symbols.DIV -> {
                '/'
            }
        }
    }

    private fun makeData() {
        val numbersAndSymbols: List<String> = formula.split(' ').map { it.trim() }

        var tmpNum = ""
        var lastUsed = ""
        val addSub: MutableList<String> = mutableListOf()
        val multiDiv: MutableList<String> = mutableListOf()
        val addSubSymbols: MutableList<String> = mutableListOf()
        val multiDivSymbols: MutableList<String> = mutableListOf()

        for (v in numbersAndSymbols) {
            when (v) {
                "+", "-" -> {
                    addSubSymbols.add(v)
                    if (lastUsed == "*" || lastUsed == "/") {
                        multiDiv.add(tmpNum)
                    } else {
                        addSub.add(tmpNum)
                    }
                    lastUsed = v
                }
                "*", "/" -> {
                    multiDiv.add(tmpNum)
                    multiDivSymbols.add(v)
                    lastUsed = v
                }
                else -> {
//                  v is numeric
                    tmpNum = v
                }
            }
        }

        if (lastUsed == "+" || lastUsed == "-") {
            addSub.add(tmpNum)
        } else {
            multiDiv.add(tmpNum)
        }

        for (n in multiDiv) {
            numbers.add(n.toFloat())
        }
        for (n in addSub) {
            numbers.add(n.toFloat())
        }
        for (n in multiDivSymbols) {
            symbols.add(n.toCharArray()[0])
        }
        for (n in addSubSymbols) {
            symbols.add(n.toCharArray()[0])
        }
    }

    private fun calcAdd(n1: Float, n2: Float) :Float {
        return n1 + n2
    }

    private fun calcSub(n1: Float, n2: Float) :Float {
        return n1 - n2
    }

    private fun calcMulti(n1: Float, n2: Float) :Float {
        return n1 * n2
    }

    private fun calcDiv(n1: Float, n2: Float) :Float? {
        return if (n2 == 0F) {
            null
        } else {
            n1 / n2
        }
    }

    private fun currentIsZero(): Boolean {
        if (currentInput == "") return true
        if (currentInput.toInt() == 0) return true
        return false
    }
}