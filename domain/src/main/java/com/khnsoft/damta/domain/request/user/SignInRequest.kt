package com.khnsoft.damta.domain.request.user

import com.khnsoft.damta.domain.request.Request

data class SignInRequest(
    val username: String,
    val password: String
) : Request