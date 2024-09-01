package de.garlic.contactsharer

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.gson.Gson
import de.garlic.contactsharer.dtos.Address
import de.garlic.contactsharer.dtos.ContactInformation
import de.garlic.contactsharer.dtos.Name

class ContactInformationEditorActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contact_information_editor)

        updateStatusBarColor()

        val toolbar: MaterialToolbar = findViewById(R.id.materialToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val contactInformation = getCorrectContactInformation(savedInstanceState)
        setContactInformationInEditFields(contactInformation)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_save -> {
                saveContactInformationInPreferences()
                true
            }
            R.id.action_cancel -> {
                finish()
                true
            }
            R.id.action_reset-> {
                setContactInformationInEditFields(ContactInformation.empty())
                saveContactInformationInPreferences()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    private fun getCorrectContactInformation(savedInstanceState: Bundle?): ContactInformation {
        if (savedInstanceState != null) {
            val json: String? = savedInstanceState.getString(
                getString(R.string.preference_contact_information_key), null
            )
            return getFromJson(json)!!
        } else {
            return getContactInformationFromPreferences()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val contactInformation = getContactInformationFromEditFields()
        val json = getAsJson(contactInformation)
        outState.putString(getString(R.string.preference_contact_information_key), json)
    }

    private fun setContactInformationInEditFields(contactInformation: ContactInformation) {
        val firstNameEditText: EditText = findViewById(R.id.editFirstName)
        firstNameEditText.setText(contactInformation.name.firstName)

        val lastNameEditText: EditText = findViewById(R.id.editLastName)
        lastNameEditText.setText(contactInformation.name.lastName)

        val phoneEditText: EditText = findViewById(R.id.editPhone)
        phoneEditText.setText(contactInformation.phone)

        val emailEditText: EditText = findViewById(R.id.editEmailAddress)
        emailEditText.setText(contactInformation.email)

        val streetEditText: EditText = findViewById(R.id.editStreet)
        streetEditText.setText(contactInformation.address.street)

        val postalCodeEditText: EditText = findViewById(R.id.editPostalCode)
        postalCodeEditText.setText(contactInformation.address.postalCode)

        val cityEditText: EditText = findViewById(R.id.editCity)
        cityEditText.setText(contactInformation.address.city)

        val countryEditText: EditText = findViewById(R.id.editCountry)
        countryEditText.setText(contactInformation.address.country)

        val birthdayEditText: EditText = findViewById(R.id.editBirthday)
        birthdayEditText.setText(contactInformation.birthday)
    }

    private fun getContactInformationFromEditFields(): ContactInformation {
        val firstNameEditText: EditText = findViewById(R.id.editFirstName)
        val lastNameEditText: EditText = findViewById(R.id.editLastName)
        val phoneEditText: EditText = findViewById(R.id.editPhone)
        val emailEditText: EditText = findViewById(R.id.editEmailAddress)
        val streetEditText: EditText = findViewById(R.id.editStreet)
        val postalCodeEditText: EditText = findViewById(R.id.editPostalCode)
        val cityEditText: EditText = findViewById(R.id.editCity)
        val countryEditText: EditText = findViewById(R.id.editCountry)
        val birthdayEditText: EditText = findViewById(R.id.editBirthday)

        val name: Name = getName(firstNameEditText, lastNameEditText)
        val address: Address =
            getAddress(streetEditText, postalCodeEditText, cityEditText, countryEditText)

        return ContactInformation(
            name = name,
            email = emailEditText.text.toString(),
            birthday = birthdayEditText.text.toString(),
            phone = phoneEditText.text.toString(),
            address = address
        )
    }

    private fun getAddress(
        streetEditText: EditText,
        postalCodeEditText: EditText,
        cityEditText: EditText,
        countryEditText: EditText
    ): Address {
        return Address(
            streetEditText.text.toString(),
            cityEditText.text.toString(),
            postalCodeEditText.text.toString(),
            countryEditText.text.toString()
        )
    }

    private fun getName(firstNameEditText: EditText, lastNameEditText: EditText): Name {
        val firstName: String = firstNameEditText.text.toString()
        val lastName: String = lastNameEditText.text.toString()

        return Name(firstName, lastName)

    }

    private fun getContactInformationFromPreferences(): ContactInformation {
        val preferences =
            getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val json =
            preferences.getString(getString(R.string.preference_contact_information_key), null)
        val contactInformation = getFromJson(json)
        return contactInformation ?: ContactInformation.empty()
    }

    private fun saveContactInformationInPreferences() {
        val preferences =
            getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val editor = preferences.edit()

        val contactInformation = getContactInformationFromEditFields()
        editor.putString(
            getString(R.string.preference_contact_information_key), getAsJson(contactInformation)
        )
        editor.apply()
    }

    private fun updateStatusBarColor() {
        val typedValue = TypedValue();
        theme.resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue, true);
        val color = ContextCompat.getColor(this, typedValue.resourceId)
        window.statusBarColor = color
    }

    private fun getAsJson(contactInformation: ContactInformation): String {
        val gson = Gson()
        return gson.toJson(contactInformation)
    }

    private fun getFromJson(json: String?): ContactInformation? {
        val gson = Gson()
        return gson.fromJson(json, ContactInformation::class.java)
    }
}
