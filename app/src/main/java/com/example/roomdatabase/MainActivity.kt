package com.example.roomdatabase

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.roomdatabase.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //getSupportActionBar()?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        requestPermission()
        navController = findNavController(R.id.fragment)
        appBarConfiguration =
            AppBarConfiguration(setOf(R.id.favFragment, R.id.callFragment, R.id.contactFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.mybtnNag.setupWithNavController(navController)
    }

    private fun checkCameraPermission() =
        (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED)

    private fun checkStoragePermission() =
        (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED)

    fun requestPermission() {
        val collectAllPermission = mutableListOf<String>()
        if (!checkCameraPermission())
            collectAllPermission.add("CAMERA")
        if (!checkCameraPermission())
            collectAllPermission.add("READ_EXTERNAL_STORAGE")
        if (collectAllPermission.isNotEmpty())
            ActivityCompat.requestPermissions(this, collectAllPermission.toTypedArray(), 101)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && grantResults.isNotEmpty()) {
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "permsion is granted for $permissions", Toast.LENGTH_SHORT)
                        .show()
            }
        }
    }
}