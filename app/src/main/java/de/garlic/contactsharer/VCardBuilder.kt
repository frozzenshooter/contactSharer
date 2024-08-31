package de.garlic.contactsharer

class VCardBuilder {

    companion object {
        private const val VCARD_TAG_BEGIN = "BEGIN:VCARD"
        private const val VCARD_TAG_END = "END:VCARD"
        private const val VERSION_TAG = "VERSION:4.0"
        private const val NAME_TAG = "N:"
        private const val ORGANIZATION_TAG = "ORG:"
        private const val TITLE_TAG = "TITLE:"
        private const val EMAIL_TAG = "EMAIL:"
        private const val BIRTHDAY_TAG = "BDAY:"
        private const val HOME_ADDRESS_TAG = "ADR;TYPE=HOME:;;"
        private const val WORK_CELL_PHONE_TAG = "TEL;TYPE=WORK,VOICE:"
        private const val TELEPHONE_TAG = "TEL;TYPE=HOME,VOICE:"
        private const val CELL_PHONE_TAG = "TEL;TYPE=HOME,CELL:"

        fun createVCard(contact: Contact): String {

            val vCardBuilder = StringBuilder()

            vCardBuilder.append(VCARD_TAG_BEGIN + System.lineSeparator())
            vCardBuilder.append(VERSION_TAG + System.lineSeparator())

            contact.name?.let { name ->
                val firstName = name.firstName ?: ""
                val lastName = name.lastName ?: ""
                vCardBuilder.append("$NAME_TAG$lastName;$firstName;;;")
            }
            contact.email?.let { vCardBuilder.append(EMAIL_TAG + it) }
            contact.phone?.let { vCardBuilder.append(TELEPHONE_TAG + it) }
            contact.cellPhone?.let { vCardBuilder.append(CELL_PHONE_TAG + it) }
            contact.address?.let { vCardBuilder.append(createHomeAddress(it)) }

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