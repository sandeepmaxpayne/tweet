package com.sandeep.tweet.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.sandeep.tweet.R
import kotlinx.android.synthetic.main.activity_forgot_pass.*

class ForgotPass : AppCompatActivity() {

    private var mauth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)
        supportActionBar?.setTitle(R.string.ResetPassword)
        mauth = FirebaseAuth.getInstance()
        btnResetPassword.setOnClickListener {
            val email = resetEmail.text.toString()
            if (TextUtils.isEmpty(email)){
                Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show()
            }else{
                mauth!!.sendPasswordResetEmail(email)
                    .addOnCompleteListener{
                        if (it.isSuccessful){
                            Toast.makeText(this, "Check Email to reset Password", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this, "Failed to send Email", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        btnBack.setOnClickListener {
            val back = Intent(this@ForgotPass, Login::class.java)
            startActivity(back)
        }
    }
}
