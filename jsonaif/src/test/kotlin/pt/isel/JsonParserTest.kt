package pt.isel

import pt.isel.sample.Person
import pt.isel.sample.Student
import kotlin.test.Test
import kotlin.test.assertEquals

class JsonParserTest {

    @Test fun parseSimpleObjectViaProperties() {
        val json = "{ name: \"Ze Manel\", nr: 7353}"
        val student = JsonParserReflect.parse(json, Student::class) as Student
        assertEquals("Ze Manel", student.name)
        assertEquals(7353, student.nr)
    }

    @Test fun parseSimpleObjectViaConstructor() {
        val json = "{ id: 94646, name: \"Ze Manel\"}"
        val p = JsonParserReflect.parse(json, Person::class) as Person
        assertEquals(94646, p.id)
        assertEquals("Ze Manel", p.name)
    }

    @Test fun parseComposeObject() {
        val json = "{ id: 94646, name: \"Ze Manel\", birth: { year: 1999, month: 9, day: 19}, sibling: { name: \"Kata Badala\"}}"
        val p = JsonParserReflect.parse(json, Person::class) as Person
        assertEquals(94646, p.id)
        assertEquals("Ze Manel", p.name)
        assertEquals(19, p.birth?.day)
        assertEquals(9, p.birth?.month)
        assertEquals(1999, p.birth?.year)
    }

    @Test fun parseArray() {
        val json = "[{name: \"Ze Manel\"}, {name: \"Candida Raimunda\"}, {name: \"Kata Mandala\"}]";
        val ps = JsonParserReflect.parse(json, Person::class) as List<Person>
        assertEquals(3, ps.size)
        assertEquals("Ze Manel", ps[0].name)
        assertEquals("Candida Raimunda", ps[1].name)
        assertEquals("Kata Mandala", ps[2].name)
    }
}
