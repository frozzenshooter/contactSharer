package de.garlic.contactsharer

data class Contact(
    val name:Name?,
    val email:String?,
    val phone:String?,
    val cellPhone:String?,
    val address:Address?
)
