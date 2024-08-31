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

        fun createVCard(fieldValues: Map<VCardField, String>): String {

            val vCardBuilder = StringBuilder()

            vCardBuilder.append(VCARD_TAG_BEGIN + System.lineSeparator())
            vCardBuilder.append(VERSION_TAG + System.lineSeparator())

            for (field in VCardField.entries) {
                val value = createValue(field, fieldValues)
                value?.let { vCardBuilder.append(it + System.lineSeparator()) }
            }

            vCardBuilder.append(VCARD_TAG_END);
            return vCardBuilder.toString()
        }

        private fun createValue(field: VCardField, fieldValues: Map<VCardField, String>): String? {
            return when (field) {
                VCardField.ADDRESS_CITY -> createHomeAddress(fieldValues) // we only check for street because we always want all fields present or none
                VCardField.CELL_PHONE -> createCellPhone(fieldValues[field])
                VCardField.WORK_CELL_PHONE -> createWorkCellPhone(fieldValues[field])
                VCardField.TELEPHONE -> createTelephone(fieldValues[field])
                VCardField.NAME -> createName(fieldValues)
                VCardField.EMAIL -> createEmail(fieldValues[field])
                VCardField.ORGANIZATION -> createOrganization(fieldValues[field])
                VCardField.JOB_TITLE -> createJobTitle(fieldValues[field])
                VCardField.BIRTHDAY -> createBirthday(fieldValues[field])
                VCardField.FIRST_NAME, VCardField.ADDRESS_STREET, VCardField.ADDRESS_COUNTRY, VCardField.ADDRESS_POSTAL_CODE -> null
            }
        }

        private fun createBasicValue(value: String?, tagStart: String): String? {
            return value?.let { tagStart + it }
        }

        private fun createJobTitle(fieldValue: String?): String? {
            return createBasicValue(fieldValue, TITLE_TAG)
        }

        private fun createBirthday(fieldValue: String?): String? {
            return createBasicValue(fieldValue, BIRTHDAY_TAG)
        }

        private fun createOrganization(fieldValue: String?): String? {
            return createBasicValue(fieldValue, ORGANIZATION_TAG)
        }

        private fun createEmail(fieldValue: String?): String? {
            return createBasicValue(fieldValue, EMAIL_TAG)
        }

        private fun createName(fieldValue: Map<VCardField, String>): String {
            return NAME_TAG + fieldValue[VCardField.NAME]!! + ";" + fieldValue[VCardField.FIRST_NAME]!! + ";;;"
        }

        private fun createTelephone(fieldValue: String?): String? {
            return fieldValue?.let { TELEPHONE_TAG + it }
        }

        private fun createCellPhone(fieldValue: String?): String? {
            return fieldValue?.let { CELL_PHONE_TAG + it }
        }

        private fun createWorkCellPhone(fieldValue: String?): String? {
            return fieldValue?.let { WORK_CELL_PHONE_TAG + it }
        }

        private fun createHomeAddress(fieldValues: Map<VCardField, String>): String? {
            val builder = StringBuilder()

            builder.append(HOME_ADDRESS_TAG)
            builder.append(fieldValues[VCardField.ADDRESS_STREET])
            builder.append(";")
            builder.append(fieldValues[VCardField.ADDRESS_CITY])
            builder.append(";;")
            builder.append(fieldValues[VCardField.ADDRESS_POSTAL_CODE])
            builder.append(";")
            builder.append(fieldValues[VCardField.ADDRESS_COUNTRY])

            return builder.toString()
        }
    }
}