package com.animebiru.kerjaaja

import org.json.JSONArray
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun `custom test`() {
        val a = ", IOS Development"
        val b = a.split(",").filter { it.isNotBlank() }.map { it.trim() }
        println(b)
        assertEquals(1, b.size)
    }
}