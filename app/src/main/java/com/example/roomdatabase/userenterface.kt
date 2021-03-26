package com.example.roomdatabase

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.roomdatabase.databinding.ActivityUserenterfaceBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class userenterface : AppCompatActivity() {
private lateinit var binding: ActivityUserenterfaceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_userenterface)
        (this as AppCompatActivity?)!!.supportActionBar!!.hide()
        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        //Fullscreen mode;
        onWindowFocusChanged(true)
        //animation field
        val topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val botAnimation=AnimationUtils.loadAnimation(this,R.anim.bot_animation)
        val mid=AnimationUtils.loadAnimation(this,R.anim.meddle_animation)

        binding.wt.startAnimation(topAnimation)
        binding.blue.startAnimation(topAnimation)
        binding.red.startAnimation(topAnimation)
        binding.des.startAnimation(topAnimation)
        binding.blue2.startAnimation(topAnimation)
        binding.red2.startAnimation(topAnimation)
        //hooking mid
        binding.txt1.startAnimation(mid)
        binding.txt2.startAnimation(mid)
        binding.txt3.startAnimation(mid)
        //hooking bottom
        binding.txt4.startAnimation(botAnimation)
        CoroutineScope(Main).launch {
            delay(10000)
            changed()
        }
    }
    private fun changed()
    {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
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