package com.khnsoft.damta.domain.common

interface HashGenerator {

    fun hashPasswordWithSalt(password: String, salt: String): String
}