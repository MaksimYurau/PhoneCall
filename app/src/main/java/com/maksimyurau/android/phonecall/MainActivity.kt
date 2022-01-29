package com.maksimyurau.android.phonecall

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
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
}