package com.example.roomdatabase.extrafragmentfile


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FragmentDailerBinding

class DialerFragment : Fragment() {
    private lateinit var binding: FragmentDailerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dailer, container, false)
         val blink = AnimationUtils.loadAnimation(activity, R.anim.blink_animation)
         val downToUp = AnimationUtils.loadAnimation(activity, R.anim.down_to_up)
        //val phoneno=binding.myphoneno.text
        binding.mycallbutton.startAnimation(blink)
        binding.circleimg.startAnimation(downToUp)
        //val namecode=binding.codepicker!!.selectedCountryCode
       // Toast.makeText(activity, "namecode =$namecode", Toast.LENGTH_SHORT).show()
        binding.mycallbutton.setOnClickListener {

            if(checkCallPermission())
            {
                val intent=Intent(Intent.ACTION_CALL)
                intent.setData(Uri.parse("tel:+${binding.codepicker!!.selectedCountryCode+binding.myphoneno.text}"))
                startActivity(intent)
            }
            else
                Toast.makeText(activity, "Call permission not given", Toast.LENGTH_SHORT).show()
        }
        binding.backArrow.setOnClickListener { gotcallhistory() }
        return binding.root
    }
    private fun gotcallhistory()=view?.findNavController()?.navigate(R.id.action_dailerFragment_to_callFragment)
    private fun checkCallPermission() = (context?.let {
        ActivityCompat.checkSelfPermission(
            it,
            Manifest.permission.CALL_PHONE
        )
    } == PackageManager.PERMISSION_GRANTED)
}