package com.github.felipehjcosta.marvelapp.base


fun readResourceFile(path: String): String? = ClassLoader.getSystemClassLoader()
        .getResourceAsStream(path)
        ?.use {
            it.reader().readText()
        }