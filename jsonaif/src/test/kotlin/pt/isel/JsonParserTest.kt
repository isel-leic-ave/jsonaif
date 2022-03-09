package pt.isel

import pt.isel.sample.Person
import pt.isel.sample.Student
import kotlin.test.Test
import kotlin.test.assertEquals

class JsonParserTest {

    @Test fun parseSimpleObject() {
        val json = "{ name: \"Ze Manel\", nr: 7353}"
        val student = JsonParserReflect.parse(json, Student::class) as Student
        assertEquals("Ze Manel", student.name)
        assertEquals(7353, student.nr)
    }

    @Test fun parseComposeObject() {
        val json = "{ name: \"Ze Manel\", birth: { year: 1999, month: 9, day: 19}, sibling: { name: \"Kata Badala\"}}"
        val student = JsonParserReflect.parse(json, Person::class) as Person
        assertEquals("Ze Manel", student.name)
        assertEquals(19, student.birth?.day)
        assertEquals(9, student.birth?.month)
        assertEquals(1999, student.birth?.year)
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
