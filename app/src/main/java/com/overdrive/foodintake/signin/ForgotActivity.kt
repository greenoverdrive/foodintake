package com.overdrive.foodintake.signin

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.dd.processbutton.iml.ActionProcessButton
import com.overdrive.foodintake.R

class ForgotActivity : AppCompatActivity() {

    companion object {
        const val TAG: String = "ForgotActivity"
        const val DELAY_MILLIS: Long = 3000
    }

    lateinit var emailText: EditText
    lateinit var forgotButton: ActionProcessButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)

        emailText = findViewById<EditText>(R.id.input_email_forgot) as EditText
        forgotButton = findViewById<ActionProcessButton>(R.id.btn_forgot) as ActionProcessButton

        with(forgotButton) {
            setMode(com.dd.processbutton.iml.ActionProcessButton.Mode.ENDLESS)

            setOnClickListener {
                (getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                        .hideSoftInputFromWindow(currentFocus!!.windowToken, 0)

                setProgress(1)
                forgot()
            }
        }
    }


    fun forgot() {
        Log.d(TAG, "Forgot")

        val email: String = emailText.text.toString()

        if (!validate(email)) {
            Log.d(TAG, "Forgot validate failed")
            onForgotFailed()
            return
        }

        Log.d(TAG, "Forgot validate completed")

        forgotButton.isEnabled = false

        // TODO: Implement your own signup logic here.

        val handler: Handler = android.os.Handler()

        handler.postDelayed( {
            Log.d(TAG, "Forgot handler run")
            // On complete call either onLoginSuccess or onLoginFailed
            onForgotSuccess();
            // onLoginFailed();
        }, DELAY_MILLIS)
    }

    fun onForgotSuccess() {
        Log.d(TAG, "Forgot success")

        forgotButton.isEnabled = true
        forgotButton.setProgress(0)

        try {
            setResult(Activity.RESULT_OK, null)
            finish()
        } catch(e: Exception) {
            Log.e("Error", e.message)
        }
    }

    fun onForgotFailed() {
        Log.d(TAG, "Forgot failed")
        Toast.makeText(this, "Restore failed", Toast.LENGTH_LONG).show()

        forgotButton.isEnabled = true
        forgotButton.setProgress(0)
    }

    fun validate(email: String): Boolean {
        Log.d(TAG, "Forgot validate")
        var valid: Boolean = true

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Enter a vaild email address")
            valid = false
        } else {
            emailText.setError(null)
        }

        return valid
    }
}
