package com.sandeep.tweet.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.sandeep.tweet.R
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    var email: String? = null
    var pass: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.setTitle(R.string.Register)

        register.setOnClickListener {
            email = findViewById<EditText>(R.id.email).text.toString()
            pass = password.text.toString()
            if (email!!.isEmpty().or(pass!!.isEmpty())){
                Toast.makeText(this , "Please enter the Email and Password", Toast.LENGTH_SHORT).show()
            }else{
                perfomRegister()
                saveUserToFiredaseDatabase()
                val intent = Intent(this@Register, Login::class.java)
                intent.putExtra("em", email)
                intent.putExtra("pa", pass)
                startActivity(intent)
            }


        }



    }

    private fun perfomRegister(){
        email = findViewById<EditText>(R.id.email).text.toString()
        pass = password.text.toString()


            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email!!, pass!!)
                .addOnCompleteListener {
                    if (!it.isSuccessful) {
                        return@addOnCompleteListener
                    } else {
                        Toast.makeText(this, "successfully registered", Toast.LENGTH_LONG).show()
                        //verifyEmail()
                        Log.d("Register", "Successfully registered ${it.result?.user?.uid}")
                        sendEmail()

                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "failed to create user: ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("Main", "${it.message}")

                }


    }

    private fun saveUserToFiredaseDatabase(){
        val uid = FirebaseAuth.getInstance().uid?: ""
        val ref = FirebaseDatabase.getInstance().getReference("users/$uid")
        val user = User(email!!, pass!!, "")
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("Register", "Finally user saved to Firebase Database")
//                val intent = Intent(this, MessageActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(intent)
            }
            .addOnFailureListener{
                Log.d("Register", "Error on save user to database ${it.message}")
            }
    }
    private fun sendEmail() {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val intent = intent


        user?.sendEmailVerification()
            ?.addOnCompleteListener(this) {
                if(it.isSuccessful){
                    Toast.makeText(this@Register, "Email Verification Sent on ${user.email}", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@Register, "Failed to send Email Verification", Toast.LENGTH_SHORT).show()
                }
            }
            ?.addOnFailureListener(this){
                Log.d("Log", "${it.message}")
            }
    }
}
 data class User(val email: String, val pass: String, var uid: String)
