package de.garlic.contactsharer

enum class VCardField(val fieldName: String) {

    NAME("name"),
    FIRST_NAME("first_name"),
    ORGANIZATION("organization"),
    JOB_TITLE("job_title"),
    CELL_PHONE("cellphone"),
    TELEPHONE("telephone"),
    WORK_CELL_PHONE("work_cellphone"),
    EMAIL("email"),
    ADDRESS_STREET("address_street"),
    ADDRESS_CITY("address_city"),
    ADDRESS_COUNTRY("address_country"),
    ADDRESS_POSTAL_CODE("address_postal_code"),
    BIRTHDAY("birthday")
}