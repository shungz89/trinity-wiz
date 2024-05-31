package com.sj.trinitywizassesment

import FirstFragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.sj.trinitywizassesment.R
import com.sj.trinitywizassesment.SecondFragment
import com.sj.trinitywizassesment.model.Contact
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    private var contactsList = mutableListOf<Contact>()
    private val ADD_CONTACT_REQUEST = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false) // Hide default title


        val searchView: androidx.appcompat.widget.SearchView = findViewById(R.id.searchView)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        val fab: FloatingActionButton = findViewById(R.id.fab)


        // Load and parse JSON data from assets
        val jsonFileString = loadJSONFromAsset()
        contactsList = Gson().fromJson(jsonFileString, Array<Contact>::class.java).toMutableList()

        fab.setOnClickListener {
            // Navigate to EditContactActivity when FAB is clicked
            val intent = Intent(this, EditContactActivity::class.java)
            startActivityForResult(intent, ADD_CONTACT_REQUEST)
        }
        fab.show()


        // Set default fragment
        loadFragment(FirstFragment())

        val onNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_tab1 -> {
                        item.setIcon(R.drawable.ic_tab1_inactive)
                        loadFragment(FirstFragment())
                        fab.show()
                        searchView.visibility = View.VISIBLE
                        toolbar.findViewById<TextView>(R.id.toolbar_title).text = "My Contact"
                        return@OnNavigationItemSelectedListener true
                    }

                    R.id.nav_tab2 -> {
                        item.setIcon(R.drawable.ic_tab2_inactive)
                        loadFragment(SecondFragment())
                        fab.hide()
                        searchView.visibility = View.GONE
                        toolbar.findViewById<TextView>(R.id.toolbar_title).text = "My Profile"

                        return@OnNavigationItemSelectedListener true
                    }
                }
                false
            }

        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    // Method to add a contact to the list
    fun addContact(contact: Contact) {
        contactsList.add(contact)
        refreshContactList() // Refresh the list in FirstFragment
    }

    // Method to retrieve the list of contacts
    fun getContacts(): List<Contact> {
        return contactsList
    }

    // Method to refresh the list in FirstFragment
    private fun refreshContactList() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment is FirstFragment) {
            fragment.refreshContacts(contactsList)
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }


    // Handle result from EditContactActivity
// Handle result from EditContactActivity
    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Retrieve the new contact from the intent
        Log.d("MainActivity", "onActivityResult:")
        val newContact = data?.getParcelableExtra<Contact>("newContact")
        if (newContact != null) {
            addContact(newContact)
        }

    }


    private fun loadJSONFromAsset(): String? {
        var json: String? = null
        try {
            val inputStream = this.assets.open("data.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charset.defaultCharset())
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return json
    }
}
