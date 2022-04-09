package com.khnsoft.damta.domain.common.impl

import com.khnsoft.damta.domain.common.HashGenerator
import java.security.MessageDigest
import javax.inject.Inject
import kotlin.experimental.and

internal class HashGeneratorImpl @Inject constructor(
    private val digest: MessageDigest
) : HashGenerator {

    override fun hashPasswordWithSalt(password: String, salt: String): String {
        return hash(password + hash(salt))
    }

    private fun hash(plain: String): String = byteArrayToString(digest.digest(plain.toByteArray()))

    private fun byteArrayToString(byteArray: ByteArray) =
        byteArray.joinToString("") { byte ->
            (byte.and(0xff.toByte()) + 0x100)
                .toString(16)
                .takeLast(2)
        }
}
