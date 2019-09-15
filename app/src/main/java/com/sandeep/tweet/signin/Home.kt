package com.sandeep.tweet.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sandeep.tweet.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlin.math.log

class Home : AppCompatActivity() {

    var logemail: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.setTitle(R.string.Home)



        val bundle: Bundle? = intent.extras
        val email = bundle?.getString("email")
        val retweet = bundle?.getString("retweet")
        val username = bundle?.getString("username")
        val fav = bundle?.getString("favouriteCount")
        val friend = bundle?.getString("friend")
        logemail = bundle?.getString("lgemail")
        if (logemail.equals(null)) {
            try {
                twemail.text = email
            } catch (ex: Exception) {
                twemail.text = "No email found"
            }
            try {
                retweetCount.text = retweet
            } catch (ex: Exception) {
                retweetCount.text = "Cannot find user"
            }
            try {
                user.text = username
            } catch (ex: Exception) {
                user.text = "Unknown User"
            }
            try {
                favCount.text = fav
            } catch (ex: Exception) {
                favCount.text = "No details found"
            }
            try {
                friendCouunt.text = friend
            } catch (ex: Exception) {
                friendCouunt.text = "No details found"
            }

        }else{
            twemail.text = logemail
            user.text = "New User with no Twitter Profile"
            favCount.visibility = View.INVISIBLE
            friendCouunt.visibility = View.INVISIBLE
            retweetCount.visibility = View.INVISIBLE
            totfr.visibility = View.INVISIBLE
            totfoll.visibility = View.INVISIBLE
            totfav.visibility = View.INVISIBLE
        }


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sign_out, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.signOut-> {
                FirebaseAuth.getInstance().signOut()
                val back = Intent(this@Home, Login::class.java)
                startActivity(back)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}

