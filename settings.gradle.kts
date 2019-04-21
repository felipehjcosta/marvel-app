listOf(":apk", ":instant").forEach { include(it) }
listOf(":feature:base", ":feature:detail", ":feature:listing", ":feature:wiki").forEach { include(it) }
listOf(":library:network", ":library:cache", ":library:unit-test-utils").forEach { include(it) }
