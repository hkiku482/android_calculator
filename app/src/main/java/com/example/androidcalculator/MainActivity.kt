package com.example.androidcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val display: CalculatorDisplay = CalculatorDisplay()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calculator: Calculator = initCalc()

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

        findViewById<Button>(R.id.keypadAC).setOnClickListener {
            calculator.allClear()
            setDisplay()
        }
        findViewById<Button>(R.id.keypadC).setOnClickListener {}
        findViewById<Button>(R.id.keypadBS).setOnClickListener {}
        findViewById<Button>(R.id.keypadCopy).setOnClickListener {}

        findViewById<Button>(R.id.keypadEq).setOnClickListener {
            calculator.putOperator(Operator.EQUAL)
            setDisplay()
        }

        findViewById<Button>(R.id.keypadAdd).setOnClickListener {
            calculator.putOperator(Operator.ADD)
            setDisplay()
        }
        findViewById<Button>(R.id.keypadSub).setOnClickListener {
            calculator.putOperator(Operator.SUB)
            setDisplay()
        }
        findViewById<Button>(R.id.keypadMulti).setOnClickListener {
            calculator.putOperator(Operator.MULTI)
            setDisplay()
        }
        findViewById<Button>(R.id.keypadDiv).setOnClickListener {
            calculator.putOperator(Operator.DIV)
            setDisplay()
        }
    }

    private fun initCalc(): Calculator {
        return Calculator(display)
    }

    private fun setDisplay() {
        findViewById<TextView>(R.id.primaryView).text = display.getPrimaryDisplay()
        findViewById<TextView>(R.id.secondaryView).text = display.getFormulaDisplay()
    }
}