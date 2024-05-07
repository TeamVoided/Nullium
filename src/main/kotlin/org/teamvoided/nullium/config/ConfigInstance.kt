package org.teamvoided.nullium.config

import java.io.File

interface ConfigInstance<T> {
    val configFile: File

    fun data(): T
    fun load(): Boolean
    fun save(): Boolean
}
