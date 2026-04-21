package com.example.emailsender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etTo: EditText
    private lateinit var etSubject: EditText
    private lateinit var etBody: EditText
    private lateinit var btnSend: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bind views
        etTo      = findViewById(R.id.etTo)
        etSubject = findViewById(R.id.etSubject)
        etBody    = findViewById(R.id.etBody)
        btnSend   = findViewById(R.id.btnSend)

        btnSend.setOnClickListener {
            sendEmail()
        }
    }

    private fun sendEmail() {
        val to      = etTo.text.toString().trim()
        val subject = etSubject.text.toString().trim()
        val body    = etBody.text.toString().trim()

        // Basic validation
        if (to.isEmpty()) {
            etTo.error = "Please enter a recipient"
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(to).matches()) {
            etTo.error = "Please enter a valid email address"
            return
        }

        // Build the email Intent
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")          // only email apps respond
            putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }

        // Launch email app chooser
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(Intent.createChooser(intent, "Choose an email app"))
        } else {
            Toast.makeText(
                this,
                "No email app found. Please install Gmail or similar.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}