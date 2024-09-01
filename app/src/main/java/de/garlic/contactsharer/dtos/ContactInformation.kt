package de.garlic.contactsharer.dtos

import android.text.TextUtils

data class ContactInformation(
    val name: Name, val email: String, val birthday: String, val phone: String, val address: Address
) {
    companion object {
        fun empty(): ContactInformation {
            return ContactInformation(Name("", ""), "", "", "", Address("", "", "", ""))
        }
    }

    fun isEmpty(): Boolean {
        return TextUtils.isEmpty(email) && TextUtils.isEmpty(name.firstName) && TextUtils.isEmpty(
            name.firstName
        ) && TextUtils.isEmpty(birthday) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(address.street) && TextUtils.isEmpty(
            address.city
        ) && TextUtils.isEmpty(address.country) && TextUtils.isEmpty(address.postalCode)
    }
}
