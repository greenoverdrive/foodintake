package com.overdrive.foodintake.signin

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.dd.processbutton.iml.ActionProcessButton
import com.overdrive.foodintake.R

class SignupActivity : AppCompatActivity() {

    companion object {
        const val TAG: String = "SignupActivity"
        const val DELAY_MILLIS: Long = 3000
    }

    lateinit var nameText: EditText
    lateinit var emailText: EditText
    lateinit var pswdText: EditText
    lateinit var loginLink: TextView
    lateinit var signupButton: ActionProcessButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        nameText = findViewById<EditText>(R.id.input_name_signup) as EditText
        emailText = findViewById<EditText>(R.id.input_email_signup) as EditText
        pswdText = findViewById<EditText>(R.id.input_password_signup) as EditText
        loginLink = findViewById<TextView>(R.id.link_login) as TextView
        signupButton = findViewById<ActionProcessButton>(R.id.btn_signup) as ActionProcessButton

        with(signupButton) {
            setMode(ActionProcessButton.Mode.ENDLESS)

            setOnClickListener {
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                        .hideSoftInputFromWindow(currentFocus!!.windowToken, 0)

                setProgress(1)
                signup()
            }
        }

        loginLink.setOnClickListener {
            finish()
        }
    }

    fun signup() {
        Log.d(TAG, "Signup")

        val name: String = nameText.text.toString()
        val email: String = emailText.text.toString()
        val password: String = pswdText.text.toString()

        if (!validate(name, email, password)) {
            Log.d(TAG, "Signup validate failed")
            onSignupFailed()
            return
        }
        Log.d(TAG, "Signup validate completed")

        signupButton.isEnabled = false

        // TODO: Implement your own signup logic here.

        val handler: Handler = android.os.Handler()

        handler.postDelayed( {
            Log.d(TAG, "Signup handler run")
            // On complete call either onLoginSuccess or onLoginFailed
            onSignupSuccess();
            // onLoginFailed();
        }, DELAY_MILLIS)

    }

    fun onSignupSuccess() {
        Log.d(TAG, "Signup success")

        signupButton.isEnabled = true
        signupButton.setProgress(0)

        try {
            setResult(Activity.RESULT_OK, null)
            finish()
        } catch(e: Exception) {
            Log.e("Error", e.message)
        }

    }

    fun onSignupFailed() {
        Log.d(TAG, "Signup failed")
        Toast.makeText(this, "Signup failed", Toast.LENGTH_LONG).show()

        signupButton.isEnabled = true
        signupButton.setProgress(0)
    }

    fun validate(name: String, email: String, password: String): Boolean {
        Log.d(TAG, "Signup validate")

        var valid: Boolean = true

        if (name.isEmpty() || name.length < 2) {
            nameText.setError("At least 2 characters")
            valid = false
        } else {
            nameText.setError(null)
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Enter a vaild email address")
            valid = false
        } else {
            emailText.setError(null)
        }

        if (password.isEmpty() || password.length < 4 || password.length > 12) {
            pswdText.setError("Between 4 and 12 alphanumeric characters");
            valid = false
        } else {
            pswdText.setError(null);
        }

        return valid
    }
}
