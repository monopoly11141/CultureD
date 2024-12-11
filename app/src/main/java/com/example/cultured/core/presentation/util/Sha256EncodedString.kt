package com.example.cultured.core.presentation.util

import java.security.MessageDigest

fun String.toSha256EncodedString() : String {
    return MessageDigest.getInstance("SHA-256")
        .digest(this.toByteArray())
        .joinToString { "%02x".format(it) }
}