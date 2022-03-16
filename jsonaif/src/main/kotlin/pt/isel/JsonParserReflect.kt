package pt.isel

import kotlin.reflect.KClass

object JsonParserReflect  : AbstractJsonParser() {

    /**
     * For each domain class we keep a Map<String, Setter> relating properties names with their setters.
     * This is for Part 2 of Jsonaif workout.
     */
    private val setters = mutableMapOf<KClass<*>, Map<String, Setter>>()
    
    override fun parsePrimitive(tokens: JsonTokens, klass: KClass<*>): Any? {
        TODO("Not yet implemented")
    }

    override fun parseObject(tokens: JsonTokens, klass: KClass<*>): Any? {
        TODO("Not yet implemented")
    }

}
