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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ruzaik.mh.onlineshopapp.R

class RegisterActivity : BaseActivity() {
    private lateinit var txt_login : TextView
    private lateinit var toolbar : Toolbar
    private lateinit var firstName : TextView
    private lateinit var lastName : TextView
    private lateinit var emailID : TextView
    private lateinit var password : TextView
    private lateinit var confirmPassword : TextView
    private lateinit var termAndCondtions : CheckBox
    private lateinit var registerBtn : Button



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
            registerUser()
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
                //showErrorSnackBar(resources.getString(R.string.register_successful), false)
                true
            }
        }
    }

    private fun registerUser() {

        showProgressDialog(resources.getString(R.string.please_wait))

        //check with validate function if the entries are valid or not.
        if(validateRegisterDetails()) {

            val emailTxt : String = emailID.text.toString().trim() { it <= ' '}
            val passwordTxt : String = password.text.toString().trim() { it <= ' '}

            //create an instance and create a register a user with email and password.
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailTxt, passwordTxt)
                .addOnCompleteListener(this) { task ->

                    hideProgressDialog()

                    if (task.isSuccessful) {
                        // Sign in success
                        val firebaseUser : FirebaseUser = task.result!!.user!!

                        showErrorSnackBar(
                            resources.getString(R.string.user_registered_firebase) + firebaseUser.uid,
                            false
                        )

                    } else {
                        // If sign in fails, display a message to the user.
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
        }
    }
}