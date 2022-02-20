package com.zntkr.deneme_fly.view

import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.zntkr.deneme_fly.R
import com.zntkr.deneme_fly.databinding.ActivityMyFlightsDetailsBinding

class MyFlightsDetailsActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityMyFlightsDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyFlightsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent()
        val list = intent.getStringArrayListExtra("list")
        val name = list?.get(0)
        val flightName = list?.get(1)
        val destination = list?.get(2)
        val time = list?.get(3)
        val date = list?.get(4)

        binding.name.text = name
        binding.flightName.text = flightName
        binding.destination.text = destination
        binding.time.text = time
        binding.date.text = date
        
        
        val bitmap = generateQRCode(flightName,time,date,name,destination)
        binding.qrcode.setImageBitmap(bitmap)
    }

    private fun generateQRCode(flightName: String?,time: String?,date: String?,name: String?,dest: String?): Bitmap? {
        val width = 500
        val height = 500
        val bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888)
        val codeWriter = MultiFormatWriter()

        try {
            val details = "$name\n$flightName\n$date\n$time\n$dest"
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