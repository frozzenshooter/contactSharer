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
    WORK_ADDRESS_STREET("work_address_street"),
    WORK_ADDRESS_CITY("work_address_city"),
    WORK_ADDRESS_COUNTRY("work_address_country"),
    WORK_ADDRESS_POSTAL_CODE("work_address_postal_code"),
    HOME_ADDRESS_STREET("home_address_street"),
    HOME_ADDRESS_CITY("home_address_city"),
    HOME_ADDRESS_COUNTRY("home_address_country"),
    HOME_ADDRESS_POSTAL_CODE("home_address_postal_code"),
    BIRTHDAY("birthday")
}