package com.khnsoft.damta.domain.request

data class SignInRequest(
    val username: String,
    val password: String
) : Request