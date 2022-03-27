package com.khnsoft.damta.domain.response

import com.khnsoft.damta.domain.model.User

data class UpdateUserResponse(
    val user: User
) : Response