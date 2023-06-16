package com.animebiru.kerjaaja.domain.models

data class Chat(
    val message: String,
    val isIncoming: Boolean,
    val timestamp: Int
)
