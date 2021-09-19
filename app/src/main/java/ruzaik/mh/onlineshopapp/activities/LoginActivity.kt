package ruzaik.mh.onlineshopapp.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import ruzaik.mh.onlineshopapp.R

class LoginActivity : AppCompatActivity() {
    lateinit var txt_register : TextView

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

        txt_register = findViewById(R.id.tv_register)


        txt_register.setOnClickListener {
            val intent  = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}