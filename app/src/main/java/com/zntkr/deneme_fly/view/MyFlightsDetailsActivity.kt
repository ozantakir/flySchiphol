package com.zntkr.deneme_fly.view

import android.Manifest
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.zntkr.deneme_fly.R
import com.zntkr.deneme_fly.databinding.ActivityMyFlightsDetailsBinding
import com.zntkr.deneme_fly.model.RoomModel
import com.zntkr.deneme_fly.viewmodel.MyFlightsDetailsViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class MyFlightsDetailsActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityMyFlightsDetailsBinding
    private lateinit var viewModel : MyFlightsDetailsViewModel

            override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyFlightsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MyFlightsDetailsViewModel::class.java]

        // getting intent and data
        val intent = getIntent()
        val list = intent.getStringArrayListExtra("list")
        val name = list?.get(0)
        val flightName = list?.get(1)
        val destination = list?.get(2)
        val time = list?.get(3)
        val date = list?.get(4)

        // assigning values to ui
        binding.name.text = name
        binding.flightName.text = flightName
        binding.destination.text = destination
        binding.time.text = time
        binding.date.text = date
        
        // qr code generation for reservation and assigning to imageview
        val bitmap = generateQRCode(flightName,time,date,name,destination)
        binding.qrcode.setImageBitmap(bitmap)

        // to save the qr code to gallery
        binding.download.setOnClickListener {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) ==  PackageManager.PERMISSION_GRANTED ){
                if (bitmap != null) {
                    saveMediaToStorage(bitmap,flightName)
                }
            }
        }

        binding.deleteButton.setOnClickListener {
           if(flightName != null){
               viewModel.deleteData(flightName)
           }
            val newIntent = Intent(this,MyFlightsActivity::class.java)
            startActivity(newIntent)
        }
    }
    // save function
    private fun saveMediaToStorage(bitmap: Bitmap,flightName: String?){
        val filename = "flight-${flightName}.jpg"
        var fos: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            this.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }
        fos?.use {
            // Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(this , "Saved to Gallery" , Toast.LENGTH_SHORT).show()
        }
    }

    // qr generator function
    private fun generateQRCode(flightName: String?,time: String?,date: String?,name: String?,dest: String?): Bitmap? {
        val width = 500
        val height = 500
        val bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888)
        val codeWriter = MultiFormatWriter()

        try {
            val details = "Reservation Details: \n$name\n$flightName\n$date\n$time\n$dest"
            val bitMatrix = codeWriter.encode(details, BarcodeFormat.QR_CODE,width,height)
            for(x in 0 until width) {
                for(y in 0 until height){
                    bitmap.setPixel(x,y,if(bitMatrix[x,y]) Color.BLACK else Color.WHITE)
                }
            }
        } catch (e: WriterException){
            Log.d(TAG,"generateQRCode: ${e.message}")
        }
        return bitmap
    }
}