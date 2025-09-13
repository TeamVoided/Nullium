package org.teamvoided.nullium.config.data

import kotlinx.serialization.Serializable

@Serializable
data class MainData(
    val info: String,
    val stoppingInfo: String,
    private val stopping: MutableMap<String, Boolean>,
    val reloadableInfo: String,
    private val reloadable: MutableMap<String, Boolean>,
) {
    constructor() : this(
        "Hello I'm the main config!",
        "If you change a value the Stopping category, you will need to restart your game (or server).",
        mutableMapOf(),
        "If you change anything in Reloadable category, you will need to run /reload",
        mutableMapOf()
    )
}