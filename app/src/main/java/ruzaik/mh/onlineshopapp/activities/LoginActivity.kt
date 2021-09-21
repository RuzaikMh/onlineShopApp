package ruzaik.mh.onlineshopapp.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import ruzaik.mh.onlineshopapp.R

class LoginActivity : BaseActivity(), View.OnClickListener {
    private lateinit var email : TextView
    private lateinit var password : TextView
    private lateinit var btnLogin : Button
    private lateinit var txtForgotPassword : TextView
    private lateinit var txtRegister : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        email = findViewById(R.id.et_email)
        password = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
        txtForgotPassword = findViewById(R.id.tv_forgot_password)
        txtRegister = findViewById(R.id.tv_register)

        btnLogin.setOnClickListener(this)
        txtForgotPassword.setOnClickListener(this)
        txtRegister.setOnClickListener(this)
    }

    override fun onClick(view: View?) {

        when(view?.id) {
            R.id.tv_forgot_password -> {
                startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
            }

            R.id.btn_login -> {
                loginRegisteredUser()
            }

            R.id.tv_register -> {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
        }

    }

    private fun validateLoginDetails() : Boolean {

        return  when {
            TextUtils.isEmpty(email.text.toString().trim() {it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_emailID), true)
                false
            }

            TextUtils.isEmpty(password.text.toString().trim() {it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            else -> {
                true
            }
        }
    }

    private fun loginRegisteredUser() {

        if(validateLoginDetails()) {
            showProgressDialog(resources.getString(R.string.please_wait))

            val emailTxt : String = email.text.toString().trim() { it <= ' '}
            val passwordTxt : String = password.text.toString().trim() { it <= ' '}

            FirebaseAuth.getInstance().signInWithEmailAndPassword(emailTxt, passwordTxt)
                .addOnCompleteListener(this) { task ->

                    hideProgressDialog()

                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                            // TODO - send user to main screen
                        showErrorSnackBar(resources.getString(R.string.login_successful), false)
                    } else {
                        // If sign in fails, display a message to the user.
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }
    }


}