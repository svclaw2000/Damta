package com.khnsoft.damta.domain.common

interface UserValidator {

    fun isUsernameValid(username: String): Boolean

    fun isPasswordValid(password: String): Boolean

    fun isEmailValid(email: String): Boolean

    fun isNicknameValid(nickname: String): Boolean
}
