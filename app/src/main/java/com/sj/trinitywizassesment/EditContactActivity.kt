package com.sj.trinitywizassesment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.imageview.ShapeableImageView
import com.sj.trinitywizassesment.model.Contact

class EditContactActivity : AppCompatActivity() {

    private lateinit var itemImageView: ShapeableImageView
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var dobEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)

        // Initialize toolbar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)  // Show back button
        supportActionBar?.setDisplayShowTitleEnabled(false) // Hide default title

        // Set custom title
        toolbar.findViewById<TextView>(R.id.toolbar_title).text = "Add Contact"

        // Initialize views
        itemImageView = findViewById(R.id.itemImageView)
        firstNameEditText = findViewById(R.id.firstName)
        lastNameEditText = findViewById(R.id.lastName)
        emailEditText = findViewById(R.id.email)
        dobEditText = findViewById(R.id.dob)
        saveButton = findViewById(R.id.save)

        // Save button click listener
        saveButton.setOnClickListener {
            val firstName = firstNameEditText.text.toString()
            val lastName = lastNameEditText.text.toString()
            val email = emailEditText.text.toString()
            val dob = dobEditText.text.toString()

            // Create a new Contact object
            val newContact =
                Contact(getCurrentTimestampInSecondsAsString(), firstName, lastName, email, dob)

            Log.d("EditContactActivity", "New contact: $newContact")
            // Pass the new contact back to MainActivity
            val intent = Intent()
            intent.putExtra("newContact", newContact)
            setResult(RESULT_OK, intent)
            finish()
        }


        // Handle back button click
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    fun getCurrentTimestampInSecondsAsString(): String {
        val timestampInSeconds = System.currentTimeMillis() / 1000
        return timestampInSeconds.toString()
    }

}
