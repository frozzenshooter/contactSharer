package de.garlic.contactsharer

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.gson.Gson

class ContactInformationEditorActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contact_information_editor)

        updateStatusBarColor()

        val cancelButton = findViewById<ImageButton>(R.id.toolbar_close_button)
        cancelButton.setOnClickListener {
            finish()
        }

        val saveButton = findViewById<Button>(R.id.toolbar_save_button)
        saveButton.setOnClickListener {
            saveContactInformation()
        }
    }

    private fun saveContactInformation() {
        val preferences =
            getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val editor = preferences.edit()

        val gson = Gson()
        val contact = Contact(null, null, null, null, null)
        val contactInformationJson = gson.toJson(contact)
        editor.putString(
            getString(R.string.preference_contact_information_key), contactInformationJson
        )
        editor.apply()
    }

    private fun updateStatusBarColor() {
        val typedValue = TypedValue();
        theme.resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue, true);
        val color = ContextCompat.getColor(this, typedValue.resourceId)
        window.statusBarColor = color
    }
}