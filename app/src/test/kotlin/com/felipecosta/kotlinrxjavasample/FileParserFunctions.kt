package com.felipecosta.kotlinrxjavasample


fun readResourceFile(path: String): String? = ClassLoader.getSystemClassLoader()
        .getResourceAsStream(path)
        ?.use {
            it.reader().readText()
        }