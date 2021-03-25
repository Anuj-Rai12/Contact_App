package com.example.roomdatabase

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class userenterface : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userenterface)
        (this as AppCompatActivity?)!!.supportActionBar!!.hide()
        /*window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()*/
        window.decorView.apply {

            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        //Fullscreen mode;
        onWindowFocusChanged(true)
        //animation field
        val Topanimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val botanimatio=AnimationUtils.loadAnimation(this,R.anim.bot_animation)
        val mid=AnimationUtils.loadAnimation(this,R.anim.meddle_animation)
        //id
//lines
        val white=findViewById<View>(R.id.wt)
        val blue1=findViewById<View>(R.id.blue)
        val red1=findViewById<View>(R.id.red)
        val des=findViewById<View>(R.id.des)
        var blue2=findViewById<View>(R.id.blue2)
        val red2=findViewById<View>(R.id.red2)
//txt
        val txt1=findViewById<TextView>(R.id.txt1)
        val txt2=findViewById<TextView>(R.id.txt2)
        val txt3=findViewById<TextView>(R.id.txt3)
//Last Line
        val txt4=findViewById<TextView>(R.id.txt4)
        //hooking top
        white.startAnimation(Topanimation)
        blue1.startAnimation(Topanimation)
        red1.startAnimation(Topanimation)
        des.startAnimation(Topanimation)
        blue2.startAnimation(Topanimation)
        red2.startAnimation(Topanimation)
        //hooking mid
        txt1.startAnimation(mid)
        txt2.startAnimation(mid)
        txt3.startAnimation(mid)
        //hooking bottom
        txt4.startAnimation(botanimatio)

        //splash screen end
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000)
    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}