package com.example.androidcalculator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val display: CalculatorDisplay = CalculatorDisplay()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calculator: Calculator = initCalc()

//        keypad 0-9, .
        findViewById<Button>(R.id.keypad0).setOnClickListener {
            calculator.putNumber('0')
            setDisplay()
        }
        findViewById<Button>(R.id.keypad1).setOnClickListener {
            calculator.putNumber('1')
            setDisplay()
        }
        findViewById<Button>(R.id.keypad2).setOnClickListener {
            calculator.putNumber('2')
            setDisplay()
        }
        findViewById<Button>(R.id.keypad3).setOnClickListener {
            calculator.putNumber('3')
            setDisplay()
        }
        findViewById<Button>(R.id.keypad4).setOnClickListener {
            calculator.putNumber('4')
            setDisplay()
        }
        findViewById<Button>(R.id.keypad5).setOnClickListener {
            calculator.putNumber('5')
            setDisplay()
        }
        findViewById<Button>(R.id.keypad6).setOnClickListener {
            calculator.putNumber('6')
            setDisplay()
        }
        findViewById<Button>(R.id.keypad7).setOnClickListener {
            calculator.putNumber('7')
            setDisplay()
        }
        findViewById<Button>(R.id.keypad8).setOnClickListener {
            calculator.putNumber('8')
            setDisplay()
        }
        findViewById<Button>(R.id.keypad9).setOnClickListener {
            calculator.putNumber('9')
            setDisplay()
        }
        findViewById<Button>(R.id.keypadDot).setOnClickListener {
            calculator.putNumber('.')
            setDisplay()
        }

//        AC, C, Backspace
        findViewById<Button>(R.id.keypadAC).setOnClickListener {
            calculator.allClear()
            setDisplay()
        }
        findViewById<Button>(R.id.keypadC).setOnClickListener {
            calculator.clear()
            setDisplay()
        }
        findViewById<Button>(R.id.keypadBS).setOnClickListener {
            calculator.backSpace()
            setDisplay()
        }

//        System
        findViewById<Button>(R.id.keypadCopy).setOnClickListener {
            val clipboardManager: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("", "display.getPrimaryDisplay()")
            clipboardManager.setPrimaryClip(clip)
        }

//        Operators
        findViewById<Button>(R.id.keypadEq).setOnClickListener {
            try {
                calculator.putOperator(Operator.EQUAL)
                setDisplay()
            } catch (e: java.lang.ArithmeticException) {
                caughtException(e)
            }
        }
        findViewById<Button>(R.id.keypadAdd).setOnClickListener {
            try {
                calculator.putOperator(Operator.ADD)
                setDisplay()
            } catch (e: java.lang.ArithmeticException) {
                caughtException(e)
            }
        }
        findViewById<Button>(R.id.keypadSub).setOnClickListener {
            try {
                calculator.putOperator(Operator.SUB)
                setDisplay()
            } catch (e: java.lang.ArithmeticException) {
                caughtException(e)
            }
        }
        findViewById<Button>(R.id.keypadMulti).setOnClickListener {
            try {
                calculator.putOperator(Operator.MUL)
                setDisplay()
            } catch (e: java.lang.ArithmeticException) {
                caughtException(e)
            }
        }
        findViewById<Button>(R.id.keypadDiv).setOnClickListener {
            try {
                calculator.putOperator(Operator.DIV)
                setDisplay()
            } catch (e: java.lang.ArithmeticException) {
                caughtException(e)
            }
        }
    }

    private fun initCalc(): Calculator {
        return Calculator(display)
    }

    private fun setDisplay() {}

    private fun caughtException(e: Exception) {
        findViewById<TextView>(R.id.primaryView).text = e.message
        findViewById<TextView>(R.id.secondaryView).text = ""
    }
}