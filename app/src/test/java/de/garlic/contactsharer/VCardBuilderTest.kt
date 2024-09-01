package de.garlic.contactsharer

import de.garlic.contactsharer.services.VCardBuilder
import org.junit.Assert.assertEquals
import org.junit.Test

class VCardBuilderTest {

    companion object{
        private const val EXPECTED_VCARD = """
            BEGIN:VCARD
            VERSION:4.0
            N:John;Doe;;;
            ORG:Organization
            TITLE:Title
            TEL;TYPE=HOME,CELL:111111111
            TEL;TYPE=HOME,VOICE:123456789
            TEL;TYPE=WORK,VOICE:987654321
            EMAIL:email@mail.de
            ADR;TYPE=WORK:;;wStreet;wCity;;wPostal;wCountry
            ADR;TYPE=HOME:;;hAddress;hCity;;hPostal;hCountry
            BDAY:19700628
            END:VCARD
        """

    }


    @Test
    fun vCard_generated(){
        //given
        val map = HashMap<VCardField, String>()

        map[VCardField.NAME] = "John"
        map[VCardField.FIRST_NAME] = "Doe"
        map[VCardField.JOB_TITLE] = "Title"
        map[VCardField.EMAIL] = "email@mail.de"
        map[VCardField.TELEPHONE] = "123456789"
        map[VCardField.WORK_CELL_PHONE] = "987654321"
        map[VCardField.CELL_PHONE] = "111111111"
        map[VCardField.ORGANIZATION] = "Organization"
        map[VCardField.BIRTHDAY] = "19700628"
        map[VCardField.WORK_ADDRESS_CITY] = "wCity"
        map[VCardField.WORK_ADDRESS_STREET] = "wStreet"
        map[VCardField.WORK_ADDRESS_POSTAL_CODE] = "wPostal"
        map[VCardField.WORK_ADDRESS_COUNTRY] = "wCountry"
        map[VCardField.HOME_ADDRESS_CITY] = "hCity"
        map[VCardField.HOME_ADDRESS_STREET] = "hAddress"
        map[VCardField.HOME_ADDRESS_POSTAL_CODE] = "hPostal"
        map[VCardField.HOME_ADDRESS_COUNTRY] = "hCountry"

        //when
        val vCard = VCardBuilder.createVCard(map)

        //then
        assertEquals(EXPECTED_VCARD.trimIndent(), vCard)
    }
}