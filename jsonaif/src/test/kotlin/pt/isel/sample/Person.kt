package pt.isel.sample

data class Person (val id: Int, val name: String, val birth: Date? = null, var sibling: Person? = null)
