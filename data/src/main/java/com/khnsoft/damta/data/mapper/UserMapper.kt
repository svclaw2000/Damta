package com.khnsoft.damta.data.mapper

import com.khnsoft.damta.data.model.UserData
import com.khnsoft.damta.domain.model.User

internal fun User.toData() = UserData(
    id = id,
    username = username,
    nickname = nickname,
    birthday = birthday,
    email = email
)

internal fun UserData.toDomain() = User(
    id = id,
    username = username,
    nickname = nickname,
    birthday = birthday,
    email = email
)