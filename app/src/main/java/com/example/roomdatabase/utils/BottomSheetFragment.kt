package com.example.roomdatabase.utils

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.BottonLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.io.FileNotFoundException

class BottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var binding: BottonLayoutBinding
    private lateinit var bitmap: Bitmap
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.botton_layout, container, false)
        binding.btnopencam.setOnClickListener {
            if (checkCameraPermission()) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, 111)
            } else
                Toast.makeText(activity, "Camera Permission not given", Toast.LENGTH_SHORT).show()

        }
        binding.btnopengal.setOnClickListener {
            if (checkGallery()) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 404)
            } else
                Toast.makeText(activity, "Gallery Permission not given", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            404 -> {
                if (resultCode == RESULT_OK) {
                    try {
                        var bitmap: Deferred<Bitmap>
                        CoroutineScope(Main).launch {
                            bitmap = async(IO) {
                                return@async bitGal(data)
                            }
                            bitmapop.value = bitmap.await()
                        }
                    } catch (e: FileNotFoundException) {
                        Toast.makeText(activity, "File not found", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            111 -> {
                if (resultCode == RESULT_OK) {
                    var bitmap: Deferred<Bitmap>
                    CoroutineScope(Main).launch {
                        bitmap = async(IO) {
                            return@async bitCam(data)
                        }
                        bitmapop.value = bitmap.await()
                    }
                }
            }

        }
    }

    suspend fun bitGal(data: Intent?): Bitmap {
        withContext(IO) {
            val inputStream = requireActivity().contentResolver.openInputStream(data?.data!!)
            bitmap = BitmapFactory.decodeStream(inputStream)
        }
        return bitmap
    }

    suspend fun bitCam(data: Intent?): Bitmap {
        withContext(IO) {
            bitmap = data?.getParcelableExtra<Bitmap>("data")!!
        }
        return bitmap
    }

    private fun checkCameraPermission() =
        (context?.let {
            ActivityCompat.checkSelfPermission(
                it,
                Manifest.permission.CAMERA
            )
        } == PackageManager.PERMISSION_GRANTED)

    private fun checkGallery() = (context?.let {
        ActivityCompat.checkSelfPermission(
            it,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    } == PackageManager.PERMISSION_GRANTED)

    companion object {
        var bitmapop = MutableLiveData<Bitmap>()
    }
}