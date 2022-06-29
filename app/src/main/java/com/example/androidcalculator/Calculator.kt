package com.example.androidcalculator

class Calculator {
    enum class State{
        READY, NUM, SYM
    }

    private var currentInput: String = ""
    private var formula: String = ""
    private var state: State = State.READY

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

    fun getFormula(): String {
        return formula
    }

    fun getCurrentInput(): String {
        return currentInput
    }

}