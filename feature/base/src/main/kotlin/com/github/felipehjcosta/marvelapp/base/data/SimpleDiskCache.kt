package com.github.felipehjcosta.marvelapp.base.data

import com.jakewharton.disklrucache.DiskLruCache
import java.io.*
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

/**
 * Adapted from https://github.com/fhucho/simple-disk-cache
 * License Apache 2.0
 */
class SimpleDiskCache private constructor(dir: File, private val mAppVersion: Int, maxSize: Long) {

    private var diskLruCache: com.jakewharton.disklrucache.DiskLruCache

    init {
        diskLruCache = DiskLruCache.open(dir, mAppVersion, 2, maxSize)
    }

    fun clear() {
        val dir = diskLruCache.directory
        val maxSize = diskLruCache.maxSize
        diskLruCache.delete()
        diskLruCache = DiskLruCache.open(dir, mAppVersion, 2, maxSize)
    }

    fun getInputStream(key: String): InputStreamEntry? {
        val snapshot = diskLruCache.get(toInternalKey(key)) ?: return null
        return InputStreamEntry(snapshot, readMetadata(snapshot))
    }

    operator fun contains(key: String): Boolean {
        val snapshot = diskLruCache.get(toInternalKey(key)) ?: return false

        snapshot.close()
        return true
    }

    private fun openStream(key: String, metadata: Map<String, Serializable>): OutputStream {
        val editor = diskLruCache.edit(toInternalKey(key))
        try {
            writeMetadata(metadata, editor)
            val bos = BufferedOutputStream(editor.newOutputStream(VALUE_IDX))
            return CacheOutputStream(bos, editor)
        } catch (e: IOException) {
            editor.abort()
            throw e
        }

    }

    fun put(key: String, inputStream: InputStream) {
        put(key, inputStream, HashMap<String, Serializable>())
    }

    private fun put(key: String, inputStream: InputStream, annotations: Map<String, Serializable>) {
        inputStream.use { input ->
            openStream(key, annotations).use { output ->
                input.copyTo(output)
            }
        }
    }

    private fun writeMetadata(metadata: Map<String, Serializable>,
                              editor: DiskLruCache.Editor) {
        var oos: ObjectOutputStream? = null
        try {
            oos = ObjectOutputStream(BufferedOutputStream(
                    editor.newOutputStream(METADATA_IDX)))
            oos.writeObject(metadata)
        } finally {
            oos?.close()
        }
    }

    @SuppressWarnings("unchecked")
    private fun readMetadata(snapshot: DiskLruCache.Snapshot): Map<String, Serializable> {
        var ois: ObjectInputStream? = null
        try {
            ois = ObjectInputStream(BufferedInputStream(
                    snapshot.getInputStream(METADATA_IDX)))
            val annotations = ois.readObject() as Map<String, Serializable>
            return annotations
        } catch (e: ClassNotFoundException) {
            throw RuntimeException(e)
        } finally {
            ois?.close()
        }
    }

    private fun toInternalKey(key: String): String {
        return md5(key)
    }

    private fun md5(text: String): String {
        try {
            val messageDigest = MessageDigest.getInstance("MD5")
            messageDigest.update(text.toByteArray(charset("UTF-8")))
            val digest = messageDigest.digest()
            val bigInt = BigInteger(1, digest)
            return bigInt.toString(16)
        } catch (e: NoSuchAlgorithmException) {
            throw AssertionError()
        } catch (e: UnsupportedEncodingException) {
            throw AssertionError()
        }

    }

    private inner class CacheOutputStream constructor(os: OutputStream, private val editor: DiskLruCache.Editor) : FilterOutputStream(os) {
        private var failed = false

        @Throws(IOException::class)
        override fun close() {
            var closeException: IOException? = null
            try {
                super.close()
            } catch (e: IOException) {
                closeException = e
            }

            if (failed) {
                editor.abort()
            } else {
                editor.commit()
            }

            if (closeException != null)
                throw closeException
        }

        @Throws(IOException::class)
        override fun flush() {
            try {
                super.flush()
            } catch (e: IOException) {
                failed = true
                throw e
            }

        }

        @Throws(IOException::class)
        override fun write(oneByte: Int) {
            try {
                super.write(oneByte)
            } catch (e: IOException) {
                failed = true
                throw e
            }

        }

        @Throws(IOException::class)
        override fun write(buffer: ByteArray) {
            try {
                super.write(buffer)
            } catch (e: IOException) {
                failed = true
                throw e
            }

        }

        @Throws(IOException::class)
        override fun write(buffer: ByteArray, offset: Int, length: Int) {
            try {
                super.write(buffer, offset, length)
            } catch (e: IOException) {
                failed = true
                throw e
            }

        }
    }

    class InputStreamEntry(private val snapshot: DiskLruCache.Snapshot, val metadata: Map<String, Serializable>) : Closeable {

        val inputStream: InputStream
            get() = snapshot.getInputStream(VALUE_IDX)

        override fun close() {
            snapshot.close()
        }
    }

    companion object {

        private val VALUE_IDX = 0
        private val METADATA_IDX = 1
        private val usedDirs = ArrayList<File>()

        @Synchronized @Throws(IOException::class)
        fun open(dir: File, appVersion: Int, maxSize: Long): SimpleDiskCache {
            if (usedDirs.contains(dir)) {
                throw IllegalStateException("Cache dir " + dir.absolutePath + " was used before.")
            }

            usedDirs.add(dir)

            return SimpleDiskCache(dir, appVersion, maxSize)
        }
    }

}
