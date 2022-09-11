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
    private val controller: CalculatorController = CalculatorController(display)

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        keypad 0-9, .
        findViewById<Button>(R.id.keypad0).setOnClickListener {
            controller.putNumber('0')
        }
        findViewById<Button>(R.id.keypad1).setOnClickListener {
            controller.putNumber('1')
        }
        findViewById<Button>(R.id.keypad2).setOnClickListener {
            controller.putNumber('2')
        }
        findViewById<Button>(R.id.keypad3).setOnClickListener {
            controller.putNumber('3')
        }
        findViewById<Button>(R.id.keypad4).setOnClickListener {
            controller.putNumber('4')
        }
        findViewById<Button>(R.id.keypad5).setOnClickListener {
            controller.putNumber('5')
        }
        findViewById<Button>(R.id.keypad6).setOnClickListener {
            controller.putNumber('6')
        }
        findViewById<Button>(R.id.keypad7).setOnClickListener {
            controller.putNumber('7')
        }
        findViewById<Button>(R.id.keypad8).setOnClickListener {
            controller.putNumber('8')
        }
        findViewById<Button>(R.id.keypad9).setOnClickListener {
            controller.putNumber('9')
        }
        findViewById<Button>(R.id.keypadDot).setOnClickListener {
            controller.putNumber('.')
        }

//        AC, C, Backspace
        findViewById<Button>(R.id.keypadAC).setOnClickListener {
        }
        findViewById<Button>(R.id.keypadC).setOnClickListener {
        }
        findViewById<Button>(R.id.keypadBS).setOnClickListener {
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
                controller.putOperator(Operator.EQUAL)
            } catch (e: java.lang.ArithmeticException) {
                caughtException(e)
            }
        }
        findViewById<Button>(R.id.keypadAdd).setOnClickListener {
            try {
                controller.putOperator(Operator.ADD)
            } catch (e: java.lang.ArithmeticException) {
                caughtException(e)
            }
        }
        findViewById<Button>(R.id.keypadSub).setOnClickListener {
            try {
                controller.putOperator(Operator.SUB)
            } catch (e: java.lang.ArithmeticException) {
                caughtException(e)
            }
        }
        findViewById<Button>(R.id.keypadMulti).setOnClickListener {
            try {
                controller.putOperator(Operator.MUL)
            } catch (e: java.lang.ArithmeticException) {
                caughtException(e)
            }
        }
        findViewById<Button>(R.id.keypadDiv).setOnClickListener {
            try {
                controller.putOperator(Operator.DIV)
            } catch (e: java.lang.ArithmeticException) {
                caughtException(e)
            }
        }
    }

    private fun caughtException(e: Exception) {
        findViewById<TextView>(R.id.primaryView).text = e.message
        findViewById<TextView>(R.id.secondaryView).text = ""
    }
}