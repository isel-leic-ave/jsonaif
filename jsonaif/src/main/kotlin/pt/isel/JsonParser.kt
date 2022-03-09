package pt.isel

import kotlin.reflect.KClass

interface JsonParser {

    fun parse(source: String, klass: KClass<*>): Any?

}
