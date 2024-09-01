package de.garlic.contactsharer.services

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.widget.ImageView
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import de.garlic.contactsharer.R
import de.garlic.contactsharer.dtos.ContactInformation

class ContactInformationTileService : TileService() {

    companion object {
        private const val QR_CODE_SIZE = 500
    }

    override fun onTileAdded() {
        super.onTileAdded()
        qsTile.state = Tile.STATE_INACTIVE
        qsTile.label = getString(R.string.default_quick_setting_name_short)
        qsTile.updateTile()
    }

    override fun onClick() {
        super.onClick()
        if (!isLocked) {
            val contactInformation = getContactInformationFromPreferences()

            if (contactInformation.isEmpty()) {
                showWarningDialog()
            } else {
                showQRCodeDialog(contactInformation)
            }
        }
    }

    private fun showWarningDialog() {
        val dialog = AlertDialog.Builder(this).setTitle("Share Contact Information")
            .setMessage("No contact information was stored yet. Open the app and save your contact information.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }.create()

        showDialog(dialog)
    }

    private fun showQRCodeDialog(contactInformation: ContactInformation) {
        val vCardString = VCardBuilder.createVCard(contactInformation)

        val qrCodeBitmap = generateQRCode(vCardString)

        val imageView = ImageView(this)
        imageView.setImageBitmap(qrCodeBitmap)

        val dialog = AlertDialog.Builder(this).setTitle("Contact Information").setView(imageView)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }.create()

        showDialog(dialog)
    }

    private fun getContactInformationFromPreferences(): ContactInformation {
        val preferences =
            getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        val optionalContactInformation: ContactInformation? =
            preferences.getString(getString(R.string.preference_contact_information_key), null)
                ?.let {
                    val gson = Gson()
                    gson.fromJson(it, ContactInformation::class.java)
                }
        return optionalContactInformation ?: ContactInformation.empty()
    }

    private fun generateQRCode(data: String): Bitmap {
        val writer = QRCodeWriter()
        val bitMatrix: BitMatrix =
            writer.encode(data, BarcodeFormat.QR_CODE, QR_CODE_SIZE, QR_CODE_SIZE)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(
                    x, y, if (bitMatrix.get(
                            x, y
                        )
                    ) android.graphics.Color.BLACK else android.graphics.Color.WHITE
                )
            }
        }
        return bitmap
    }
}