package com.felipecosta.kotlinrxjavasample.data

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue


class SimpleDiskCacheTest {

    private val appVersion = 100

    @Rule @JvmField
    val tempDir = TemporaryFolder()

    lateinit var cache: SimpleDiskCache

    @Before
    fun setUp() {
        val cacheDir = tempDir.newFolder("SimpleDiskCacheTest")
        cache = SimpleDiskCache.open(cacheDir, appVersion, Integer.MAX_VALUE.toLong())
    }

    @Test
    fun givenKeyWhenGetInputStreamThenAssertValue() {
        val key = "key"
        val value = "value"

        cache.put(key, value.byteInputStream())

        assertEquals(value, cache.getInputStream(key)!!.use { it.inputStream.reader().readText() })
    }

    @Test
    fun givenKeyWhenContainsThenAssertTrue() {
        val key = "key"
        val value = "value"

        cache.put(key, value.byteInputStream())

        assertTrue { cache.contains(key) }
    }

    @Test
    fun givenKeyWhenClearThenAssertFalse() {
        val key = "key"
        val value = "value"

        cache.put(key, value.byteInputStream())

        cache.clear()

        assertFalse { cache.contains(key) }
    }

    @Test
    fun givenNotKeyWhenContainsThenAssertFalse() {
        val key = "key"

        assertFalse { cache.contains(key) }
    }

    @Test
    fun givenNotKeyWhenGetInputStreamThenAssertEmpty() {
        val key = "key"

        assertNull(cache.getInputStream(key))
    }
}