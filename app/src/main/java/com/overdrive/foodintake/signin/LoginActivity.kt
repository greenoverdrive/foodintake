package com.overdrive.foodintake.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.dd.processbutton.iml.ActionProcessButton
import com.overdrive.foodintake.MainActivity
import com.overdrive.foodintake.R
import com.overdrive.foodintake.server.ServerApp
import java.io.IOException


class LoginActivity : AppCompatActivity() {

    companion object {
        const val TAG: String = "LoginActivity"
        const val REQUEST_SIGNUP: Int = 1
        const val REQUEST_FORGOT: Int = 2
        const val DELAY_MILLIS: Long = 3000
    }

    lateinit var emailText: EditText
    lateinit var pswdText: EditText
    lateinit var signupLink: TextView
    lateinit var forgotLink: TextView
    lateinit var loginButton: ActionProcessButton

    var token: String? = null
    var username: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        emailText = findViewById<EditText>(R.id.input_email) as EditText
        pswdText = findViewById<EditText>(R.id.input_password) as EditText
        signupLink = findViewById<TextView>(R.id.link_signup) as TextView
        forgotLink = findViewById<TextView>(R.id.link_forgot) as TextView
        loginButton = findViewById<ActionProcessButton>(R.id.btn_login) as ActionProcessButton


        with(loginButton) {
            setMode(ActionProcessButton.Mode.ENDLESS)
            //setMode(ActionProcessButton.Mode.PROGRESS)

            setOnClickListener {
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                        .hideSoftInputFromWindow(currentFocus!!.windowToken, 0)

                setProgress(1)
                login()
            }
        }

        signupLink.setOnClickListener {
            Log.d(TAG, "Signup link")
            startActivityForResult(Intent(this, SignupActivity::class.java),
                    REQUEST_SIGNUP)
        }

        forgotLink.setOnClickListener {
            Log.d(TAG, "Forgot link")
            startActivityForResult(Intent(this, ForgotActivity::class.java),
                    REQUEST_FORGOT)
        }

    }


    fun login() {
        Log.d(TAG, "Login")

        val email: String = emailText.text.toString()
        val password: String = pswdText.text.toString()

        if(!validate(email, password)) {
            Log.d(TAG, "Validate failed")
            onLoginFailed()
            return
        }
        Log.d(TAG, "Validate completed")

        loginButton.isEnabled = false

        //TODO: authorization
        try {
            val loginResponse = ServerApp.signInApi?.signIn(email, password, "device_id")?.execute()
        } catch (e: IOException) {
            e.printStackTrace();
        }


        val handler: Handler = android.os.Handler()

        handler.postDelayed( {
            Log.d(TAG, "Handler run")
            // On complete call either onLoginSuccess or onLoginFailed
            onLoginSuccess();
            // onLoginFailed();

        }, DELAY_MILLIS)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_SIGNUP) {
            Log.d(TAG, "Request signup")
            if(resultCode == RESULT_OK) {
                // TODO: Implement successful signup(!) logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }

        if(requestCode == REQUEST_FORGOT) {
            Log.d(TAG, "Request forgot")
            if(resultCode == RESULT_OK) {
                // TODO: Implement successful forgot(!) logic here
            }
        }
    }


    fun onLoginSuccess() {
        Log.d(TAG, "Login success")
        loginButton.isEnabled = true
        loginButton.setProgress(0)

        username = emailText.text.toString()
        token = "abcdefg123"

        val resultIntent = Intent()
        resultIntent.putExtra(MainActivity.PREF_USERNAME, username)
        resultIntent.putExtra(MainActivity.PREF_TOKEN, username)
        setResult(MainActivity.REQUEST_LOGIN, resultIntent)

        finish()
    }

    fun onLoginFailed() {
        Log.d(TAG, "Login failed")
        // TODO: make error text on display
        Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show()
        loginButton.setProgress(0)
        loginButton.isEnabled = true
    }

    override fun onBackPressed() {
        Log.d(TAG, "Back pressed")
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }


    fun validate(email: String, password: String): Boolean {
        Log.d(TAG, "Validate")

        var valid: Boolean = true
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
