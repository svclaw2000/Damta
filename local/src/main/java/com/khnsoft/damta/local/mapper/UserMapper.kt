package com.khnsoft.damta.local.mapper

import com.khnsoft.damta.data.model.UserData
import com.khnsoft.damta.local.model.UserDto

internal fun UserData.toDto(password: String) = UserDto(
    id = id,
    username = username,
    password = password,
    email = email,
    nickname = nickname,
    birthday = birthday
)

internal fun UserDto.toData() = UserData(
    id = id,
    username = username,
    email = email,
    nickname = nickname,
    birthday = birthday
)