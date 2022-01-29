package com.maksimyurau.android.phonecall

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private val requestCall = 1
    private lateinit var etNumber: EditText
    private lateinit var btCall: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViews()
        btCallOnClickListener()
    }

    private fun initializeViews() {
        etNumber = findViewById(R.id.et_number)
        btCall = findViewById(R.id.bt_call)
    }

    private fun btCallOnClickListener() {
        btCall.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf({ Manifest.permission.CALL_PHONE }.toString()), requestCall
                )
            }
            try {
                val phone = etNumber.text.toString()
                if (phone.isEmpty()) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.please_enter_number),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    val s = "tel:$phone"
                    val intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse(s)
                    startActivity(intent)
                }
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCall) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                btCallOnClickListener()
            } else {
                Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}