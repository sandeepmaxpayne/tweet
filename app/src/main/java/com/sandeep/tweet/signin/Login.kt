package com.sandeep.tweet.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sandeep.tweet.R
import com.sandeep.tweet.TwitLog
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*


class Login : AppCompatActivity() {

    var email: String? = null
    var pass: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val bundle = intent.extras
        email = bundle?.getString("em")
        pass = bundle?.getString("pa")
        log_in_email.setText(email)
        login_password.setText(pass)
        supportActionBar?.setTitle(R.string.LogIn)

        reg.setOnClickListener {
            val intent = Intent(this@Login, Register::class.java)
            startActivity(intent)
        }
        btn_login.setOnClickListener {
                signin()

        }

        forget_pass.setOnClickListener {
            val intent = Intent(this@Login, ForgotPass::class.java)
            startActivity(intent)
        }
        login_button.setOnClickListener{
            val tweet = Intent(this@Login, TwitLog::class.java)
            startActivity(tweet)
        }
    }
    fun signin(){
        try {
            email = log_in_email.text.toString()
            pass = login_password.text.toString()


            val auth = FirebaseAuth.getInstance()
            val intent = intent
            val emailLink = intent.data?.toString()
            Log.d("Log", "email: $emailLink")
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email!!, pass!!)
                .addOnCompleteListener {
                    if (!it.isSuccessful) {
                        return@addOnCompleteListener
                    } else {
                        // Log.d("Log", "Successfully Logged in ${it.result.user}")
                        Toast.makeText(this@Login, "Logged in Successfully", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this, Home::class.java)
                        intent.putExtra("lgemail", email!!)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }

                }
                .addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    Log.d("Log", "Error signing in: ${it.message}")

                }
        }catch (ex: Exception){
            Toast.makeText(this@Login, "Please enter the email and password", Toast.LENGTH_SHORT).show()

        }
    }

}
class SavedUser(email1: String, uid: String)
