package com.example.androidcalculator

class Calculator {
    enum class State{
        READY, NUM, SYM
    }

    private var currentInput: String = ""
    private var formula: String = ""
    private var state: State = State.READY

    private var numbers: MutableList<Float> = mutableListOf()
    private var symbols: MutableList<Char> = mutableListOf()

    fun getFormula(): String {
        return formula
    }

    fun getCurrentInput(): String {
        return currentInput
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
                    formula = formula.dropLast(2) + "$c "
                }
                else -> {
                    formula = "0 $c "
                }
            }
            currentInput = ""
        }
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
}