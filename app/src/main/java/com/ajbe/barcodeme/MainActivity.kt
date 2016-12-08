package com.ajbe.barcodeme

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mBitmap : Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Load QR Code from drawables
        mBitmap = BitmapFactory.decodeResource(resources, R.drawable.puppy)
        imgview.setImageBitmap(mBitmap)

        button.setOnClickListener { loadImage() }
    }

    private fun loadImage() {
        // We create our new BarcodeDetector using a builder
        // and tell it to look for QR codes and Data Matrices
        val detector = BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.DATA_MATRIX or Barcode.QR_CODE)
                .build()
        // we need to check if our detector is operational before we use it. If it isn't
        // we may have to wait for a download to complete, or let our users know that they need
        // to find an internet connection or clear some space on their device.
        if (!detector.isOperational) {
            txtContent.text = getString(R.string.not_detector)
            return
        }
        // Create a frame from the bitmap, and passes it to the detector.
        // This returns a SparseArray of barcodes.
        val frame = Frame.Builder().setBitmap(mBitmap).build()
        val barcodes = detector.detect(frame)
        // I know I have 1 and only 1 bar code, I can hard code for i
        // Take the Barcode called ‘thisCode' to be the first element in the array.
        val thisCode = barcodes.valueAt(0)
        // Then assign it's rawValue to the textView
        txtContent.text = thisCode.rawValue
    }
}
