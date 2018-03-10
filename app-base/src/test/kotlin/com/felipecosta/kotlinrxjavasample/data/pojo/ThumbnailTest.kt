package com.felipecosta.kotlinrxjavasample.data.pojo

import org.junit.Test
import kotlin.test.assertEquals

class ThumbnailTest {

    @Test
    fun givenPathAndExtensionThenAssertUrl() {
        val expected = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784.jpg"
        val actual = with(Thumbnail()) {
            path = "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784"
            extension = "jpg"
            url
        }
        assertEquals(expected, actual)
    }

}