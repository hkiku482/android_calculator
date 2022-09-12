package com.example.androidcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val display: CalculatorDisplay = CalculatorDisplay(this)
    private val controller: CalculatorController = CalculatorController(display)
    private var isFirstMode: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.keypadToggleFunc).setOnClickListener {
            if (this.isFirstMode) {
                findViewById<Button>(R.id.keypadToggleFunc).text = getString(R.string.second)
                findViewById<Button>(R.id.keypadF0).text = getString(R.string.pi)
                findViewById<Button>(R.id.keypadF1).text = getString(R.string.sin)
                findViewById<Button>(R.id.keypadF2).text = getString(R.string.cos)
                findViewById<Button>(R.id.keypadF3).text = getString(R.string.tan)
            } else {
                findViewById<Button>(R.id.keypadToggleFunc).text = getString(R.string.first)
                findViewById<Button>(R.id.keypadF0).text = getString(R.string.pi)
                findViewById<Button>(R.id.keypadF0).text = getString(R.string.eq)
                findViewById<Button>(R.id.keypadF1).text = getString(R.string.ac)
                findViewById<Button>(R.id.keypadF2).text = getString(R.string.c)
                findViewById<Button>(R.id.keypadF3).text = getString(R.string.bs)
            }
            this.isFirstMode = !this.isFirstMode
        }

//        keypad 0-9, .
        findViewById<Button>(R.id.keypad0).setOnClickListener {
            this.controller.putNumber('0')
        }
        findViewById<Button>(R.id.keypad1).setOnClickListener {
            this.controller.putNumber('1')
        }
        findViewById<Button>(R.id.keypad2).setOnClickListener {
            this.controller.putNumber('2')
        }
        findViewById<Button>(R.id.keypad3).setOnClickListener {
            this.controller.putNumber('3')
        }
        findViewById<Button>(R.id.keypad4).setOnClickListener {
            this.controller.putNumber('4')
        }
        findViewById<Button>(R.id.keypad5).setOnClickListener {
            this.controller.putNumber('5')
        }
        findViewById<Button>(R.id.keypad6).setOnClickListener {
            this.controller.putNumber('6')
        }
        findViewById<Button>(R.id.keypad7).setOnClickListener {
            this.controller.putNumber('7')
        }
        findViewById<Button>(R.id.keypad8).setOnClickListener {
            this.controller.putNumber('8')
        }
        findViewById<Button>(R.id.keypad9).setOnClickListener {
            this.controller.putNumber('9')
        }
        findViewById<Button>(R.id.keypadDot).setOnClickListener {
            this.controller.putNumber('.')
        }

//        AC, C, Backspace
        findViewById<Button>(R.id.keypadF1).setOnClickListener {
            if (this.isFirstMode) {
                this.controller.allClear()
            } else {
                this.controller.putOperator(Operator.SIN)
            }
        }
        findViewById<Button>(R.id.keypadF2).setOnClickListener {
            if (this.isFirstMode) {
                this.controller.clear()
            } else {
                this.controller.putOperator(Operator.COS)
            }
        }
        findViewById<Button>(R.id.keypadF3).setOnClickListener {
            if (this.isFirstMode) {
                this.controller.backSpace()
            } else {
                this.controller.putOperator(Operator.TAN)
            }
        }

//        Operators
        findViewById<Button>(R.id.keypadF0).setOnClickListener {
            if (this.isFirstMode) {
                try {
                    this.controller.putOperator(Operator.EQUAL)
                } catch (e: java.lang.ArithmeticException) {
                    findViewById<TextView>(R.id.primaryView).text = getString(R.string.error)
                }
            } else {
                this.controller.putConstantNumber(kotlin.math.PI.toString())
            }
        }
        findViewById<Button>(R.id.keypadAdd).setOnClickListener {
            try {
                this.controller.putOperator(Operator.ADD)
            } catch (e: java.lang.ArithmeticException) {
                findViewById<TextView>(R.id.primaryView).text = getString(R.string.error)
            }
        }
        findViewById<Button>(R.id.keypadSub).setOnClickListener {
            try {
                this.controller.putOperator(Operator.SUB)
            } catch (e: java.lang.ArithmeticException) {
                findViewById<TextView>(R.id.primaryView).text = getString(R.string.error)
            }
        }
        findViewById<Button>(R.id.keypadMulti).setOnClickListener {
            try {
                this.controller.putOperator(Operator.MUL)
            } catch (e: java.lang.ArithmeticException) {
                findViewById<TextView>(R.id.primaryView).text = getString(R.string.error)
            }
        }
        findViewById<Button>(R.id.keypadDiv).setOnClickListener {
            try {
                this.controller.putOperator(Operator.DIV)
            } catch (e: java.lang.ArithmeticException) {
                findViewById<TextView>(R.id.primaryView).text = getString(R.string.error)
            }
        }
    }
}