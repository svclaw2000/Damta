package com.khnsoft.damta.domain.response.user

import com.khnsoft.damta.domain.model.User
import com.khnsoft.damta.domain.response.Response

data class UpdateUserResponse(
    val user: User
) : Response
