package com.animebiru.kerjaaja

import com.animebiru.kerjaaja.domain.utils.MLHelper
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
        val tes = MLHelper.other.split("\n")
        println(tes)
        assert(true)
    }
}