package com.example.cultured.core.presentation.util

import java.security.DigestException
import java.security.MessageDigest

@OptIn(ExperimentalStdlibApi::class)
fun String.toSha256EncodedString() : String {
    return MessageDigest
        .getInstance("SHA-256")
        .digest(this.toByteArray())
        .toHexString()
}