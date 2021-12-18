package com.example.calculus

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : AppCompatActivity() {

    private lateinit var txtInput: TextView
    private var lastNumeric: Boolean = false
    private var stateError: Boolean = false
    private var lastDot: Boolean = false

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("str", txtInput.text.toString())
        outState.putBoolean("stateError", stateError)
        outState.putBoolean("lastDot", lastDot)
        outState.putBoolean("lastNumeric", lastNumeric)


    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        txtInput.text = savedInstanceState.getString("str")
        lastNumeric = savedInstanceState.getBoolean("lastNumeric")
        stateError = savedInstanceState.getBoolean("stateError")
        lastDot = savedInstanceState.getBoolean("lastDot")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtInput = findViewById(R.id.txtInput)
    }

    fun onDigit(view: View) {
        Log.e("onDigit", (view as Button).text.toString())
        Log.e("onDigit", stateError.toString())
        Log.e("onDigit", txtInput.text.toString())
        if (stateError) {
            txtInput.text = (view as Button).text
            stateError = false
        } else {
            txtInput.append((view as Button).text)
            Log.e("onDigit", txtInput.text.toString())
        }
        lastNumeric = true
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !stateError && !lastDot) {
            txtInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        Log.e("onOperator", stateError.toString())
        if (lastNumeric && !stateError) {
            Log.e("onOperator", txtInput.text.toString())
            txtInput.append((view as Button).text.toString())
            lastNumeric = false
            lastDot = false
            Log.e("onOperator", txtInput.text.toString())
        }
    }

    fun onClear(view: View) {
        this.txtInput.text = ""
        lastNumeric = false
        stateError = false
        lastDot = false
    }

    fun onEqual(view: View) {
        Log.e("onEqual", stateError.toString())
        Log.e("onEqual", lastNumeric.toString())
        if (lastNumeric && !stateError) {
            val txt = txtInput.text.toString()
            val expression = ExpressionBuilder(txt).build()
            try {
                val result = expression.evaluate()
                Log.e("onEqual", result.toString())
                Log.e("onEqual",txt)
                txtInput.text = result.toString()
                lastDot = true
            } catch (ex: ArithmeticException) {
                Log.e("onEqual",txt)
                txtInput.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }
}