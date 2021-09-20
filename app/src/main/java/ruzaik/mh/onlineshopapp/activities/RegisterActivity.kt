package ruzaik.mh.onlineshopapp.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import ruzaik.mh.onlineshopapp.R

class RegisterActivity : BaseActivity() {
    lateinit var txt_login : TextView
    lateinit var toolbar : Toolbar
    lateinit var firstName : TextView
    lateinit var lastName : TextView
    lateinit var emailID : TextView
    lateinit var password : TextView
    lateinit var confirmPassword : TextView
    lateinit var termAndCondtions : CheckBox
    lateinit var registerBtn : Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        txt_login = findViewById(R.id.tv_login)
        toolbar = findViewById(R.id.toolbar_register_activity)
        firstName = findViewById(R.id.et_firstName)
        lastName = findViewById(R.id.et_lastName)
        emailID = findViewById(R.id.et_emailID)
        password = findViewById(R.id.et_password)
        confirmPassword = findViewById(R.id.et_confirmPassword)
        termAndCondtions = findViewById(R.id.cb_terms_and_condition)
        registerBtn = findViewById(R.id.btn_register)

        setupActionBar()

        txt_login.setOnClickListener {
            intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        registerBtn.setOnClickListener {
            validateRegisterDetails()
        }
    }

    private fun setupActionBar() {

        setSupportActionBar(toolbar)

        val actionBar = supportActionBar

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24)
        }

        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(firstName.text.toString().trim() {it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }

            TextUtils.isEmpty(lastName.text.toString().trim() {it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
                false
            }

            TextUtils.isEmpty(emailID.text.toString().trim() {it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_emailID), true)
                false
            }

            TextUtils.isEmpty(password.text.toString().trim() {it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            TextUtils.isEmpty(confirmPassword.text.toString().trim() {it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_confirm_password), true)
                false
            }

            password.text.toString().trim() {it <= ' '} != confirmPassword.text.toString()
                .trim() {it <= ' '} -> {
                    showErrorSnackBar(resources.getString(R.string.err_msg_password_and_confirm_password_mismatch),
                        true)
                    false
                }

            !termAndCondtions.isChecked -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_agree_terms_and_conditions), true)
                false
            }

            else -> {
                showErrorSnackBar(resources.getString(R.string.register_successful), false)
                true
            }
        }
    }
}