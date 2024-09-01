package de.garlic.contactsharer.services

import de.garlic.contactsharer.dtos.Address
import de.garlic.contactsharer.dtos.ContactInformation

class VCardBuilder {

    companion object {
        private const val VCARD_TAG_BEGIN = "BEGIN:VCARD"
        private const val VCARD_TAG_END = "END:VCARD"
        private const val VERSION_TAG = "VERSION:4.0"
        private const val NAME_TAG = "N:"
        private const val EMAIL_TAG = "EMAIL;TYPE=HOME:"
        private const val BIRTHDAY_TAG = "BDAY:"
        private const val HOME_ADDRESS_TAG = "ADR;TYPE=HOME:;;"
        private const val CELL_PHONE_TAG = "TEL;TYPE=HOME,CELL:"

        fun createVCard(contactInformation: ContactInformation): String {

            val vCardBuilder = StringBuilder()

            vCardBuilder.append(VCARD_TAG_BEGIN + System.lineSeparator())
            vCardBuilder.append(VERSION_TAG + System.lineSeparator())

            contactInformation.name.let { name ->
                val firstName = name.firstName
                val lastName = name.lastName
                vCardBuilder.append("$NAME_TAG$lastName;$firstName;;;" + System.lineSeparator())
            }
            contactInformation.birthday.let { vCardBuilder.append(BIRTHDAY_TAG + it + System.lineSeparator()) }
            contactInformation.email.let { vCardBuilder.append(EMAIL_TAG + it + System.lineSeparator()) }
            contactInformation.phone.let { vCardBuilder.append(CELL_PHONE_TAG + it + System.lineSeparator()) }
            contactInformation.address.let { vCardBuilder.append(createHomeAddress(it) + System.lineSeparator()) }

            vCardBuilder.append(VCARD_TAG_END);
            return vCardBuilder.toString()
        }

        private fun createHomeAddress(address: Address): String {
            val builder = StringBuilder()

            builder.append(HOME_ADDRESS_TAG)
            builder.append(address.street)
            builder.append(";")
            builder.append(address.city)
            builder.append(";;")
            builder.append(address.postalCode)
            builder.append(";")
            builder.append(address.country)

            return builder.toString()
        }
    }
}