package de.garlic.contactsharer

import de.garlic.contactsharer.dtos.Address
import de.garlic.contactsharer.dtos.ContactInformation
import de.garlic.contactsharer.dtos.Name
import de.garlic.contactsharer.services.VCardBuilder
import org.junit.Assert.assertEquals
import org.junit.Test

class VCardBuilderTest {

    companion object {
        private const val EXPECTED_VCARD = """
            BEGIN:VCARD
            VERSION:4.0
            N:Doe;John;;;
            BDAY:19700628
            EMAIL;TYPE=HOME:email@mail.de
            TEL;TYPE=HOME,CELL:123456789
            ADR;TYPE=HOME:;;Street;City;;PostalCode;Country
            END:VCARD
        """

    }

    @Test
    fun vCard_generated() {
        //given
        val info = ContactInformation(
            name = Name("John", "Doe"),
            email = "email@mail.de",
            birthday = "19700628",
            phone = "123456789",
            address = Address("Street", "City", "PostalCode", "Country")
        )

        //when
        val vCard = VCardBuilder.createVCard(info)

        //then
        assertEquals(EXPECTED_VCARD.trimIndent(), vCard)
    }
}