package com.example.roomdatabase

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.roomdatabase.databinding.ActivityMainBinding
import kotlin.math.floor


class MainActivity : AppCompatActivity() {
    companion object {
        const val MYCONTACTDETAIL = "My_Contact_Detail"
        const val MYCALLDETAIL = "My_CALL_Detail"
        var getmybitmap: Bitmap? = null

        val myColor =
            listOf<Int>(
                R.color.anujgreen,
                R.color.anujcherryred,
                R.color.purple_500,
                R.color.black,
                R.color.teal_700,
                R.color.anujpurbles,
                R.color.anujorange
            )

        fun randomNumber(value: Int): Int = floor((Math.random() * value)).toInt()
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //getSupportActionBar()?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        /*lifecycleScope.launch {
            val deferred = async { getBitmap() }
            getmybitmap = deferred.await()

        }*/
        getmybitmap =convertImage(R.drawable.profilepicture)
        requestPermission()
        navController = findNavController(R.id.fragment)
        appBarConfiguration =
            AppBarConfiguration(setOf(R.id.favFragment, R.id.callFragment, R.id.contactFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.mybtnNag.setupWithNavController(navController)
    }

    private fun convertImage(it: Int): Bitmap = BitmapFactory.decodeResource(resources, it)

    private fun checkCallPermission() = (ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.CALL_PHONE
    ) == PackageManager.PERMISSION_GRANTED)

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

    private fun requestPermission() {
        val collectAllPermission = mutableListOf<String>()
        if (!checkCameraPermission())
            collectAllPermission.add(Manifest.permission.CAMERA)
        if (!checkStoragePermission())
            collectAllPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (!checkCallPermission())
            collectAllPermission.add(Manifest.permission.CALL_PHONE)
        if (collectAllPermission.isNotEmpty())
            ActivityCompat.requestPermissions(this, collectAllPermission.toTypedArray(), 101)
    }

    /*private suspend fun getBitmap(): Bitmap? {
        val loading = ImageLoader(this)
        val request = ImageRequest.Builder(this)
            .data("https://avatars.githubusercontent.com/u/63039156?s=400&u=7c38d67ee6b4ec2d5fbaffd9760544c0d981b16f&v=4")
            .build()

        return try {
            val result = (loading.execute(request) as SuccessResult).drawable
            (result as BitmapDrawable).bitmap
        } catch (e: Exception) {
            Toast.makeText(this, "Check Your Internet Connection", Toast.LENGTH_SHORT).show()
            getmybitmap
        }
    }*/

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && grantResults.isNotEmpty()) {
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("PERMISSION", "The Permission for $permissions")
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}