package de.garlic.contactsharer

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class ContactInformationEditorActivity : AppCompatActivity() {

    private var isDatePickerShown = false

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contact_information_editor)

//        val birthdayEditText = findViewById<TextInputEditText>(R.id.editBirthday)
//
//
//        // Create a MaterialDatePicker instance
//        val datePicker =
//            MaterialDatePicker.Builder.datePicker().setTitleText("Select your birthday")
//                .setCalendarConstraints(
//                    CalendarConstraints.Builder()
//                        .setValidator(DateValidatorPointBackward.now()) // Only allow past dates
//                        .build()
//                ).build()
//
//        birthdayEditText.setOnClickListener {
//            // Prevent multiple clicks from showing multiple dialogs
//            if (!isDatePickerShown) {
//                isDatePickerShown = true
//
//                // Show the DatePicker
//                datePicker.show(supportFragmentManager, "MATERIAL_DATE_PICKER")
//            }
//        }
//
//        // Reset the flag when the picker is dismissed
//        datePicker.addOnDismissListener {
//            isDatePickerShown = false
//        }
//
//        // Set the date once selected
//        datePicker.addOnPositiveButtonClickListener { selection ->
//            // Format the date and set it to the EditText
//            val formattedDate =
//                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(selection))
//            birthdayEditText.setText(formattedDate)
//        }

        updateStatusBarColor()
    }

    private fun updateStatusBarColor() {
        val typedValue = TypedValue();
        theme.resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue, true);
        val color = ContextCompat.getColor(this, typedValue.resourceId)
        window.statusBarColor = color
    }
}