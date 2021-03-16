package com.example.roomdatabase.utils

import android.Manifest
import android.R.attr
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapRegionDecoder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.BottonLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.FileNotFoundException


class BottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var binding: BottonLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.botton_layout, container, false)
        binding.btnopencam.setOnClickListener {
//            Toast.makeText(activity, "Hello fucker", Toast.LENGTH_SHORT).show()
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 111)

        }
        binding.btnopengal.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 404)
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            404 -> {
                if (resultCode == RESULT_OK) {
                    try {
                        val inputStream =requireActivity().contentResolver.openInputStream(data?.data!!)
                        val bitmapImage = BitmapFactory.decodeStream(inputStream)
                        Toast.makeText(
                            activity,
                            "Gallery is working file ${bitmapImage}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        /*Further modified status:Failed*/
                    } catch (e: FileNotFoundException) {
                        Toast.makeText(activity, "File not found", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            111 -> {
                if (resultCode == RESULT_OK) {
                    val bitmap = data?.getParcelableExtra<Bitmap>("data")
                    Toast.makeText(
                        activity,
                        "Gallery is working file ${bitmap}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    private fun checkCameraPermission() =
        (context?.let {
            ActivityCompat.checkSelfPermission(
                it,
                Manifest.permission.CAMERA
            )
        } == PackageManager.PERMISSION_GRANTED)
}