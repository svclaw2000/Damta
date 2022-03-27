package com.khnsoft.damta.domain.usecase

import com.khnsoft.damta.common.extension.flatMap
import com.khnsoft.damta.domain.common.UserValidator
import com.khnsoft.damta.domain.error.UserError
import com.khnsoft.damta.domain.repository.UserRepository
import com.khnsoft.damta.domain.request.UpdateUserRequest
import com.khnsoft.damta.domain.response.UpdateUserResponse
import javax.inject.Inject

internal class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val validator: UserValidator
) : ResultUseCase<UpdateUserRequest, UpdateUserResponse> {

    override suspend fun invoke(request: UpdateUserRequest): Result<UpdateUserResponse> {
        when {
            !validator.isNicknameValid(request.nickname) ->
                return Result.failure(UserError.InvalidNickname)
            !validator.isEmailValid(request.email) ->
                return Result.failure(UserError.InvalidEmail)
        }

        return userRepository.updateUser(
            email = request.email,
            nickname = request.nickname,
            birthday = request.birthday
        ).flatMap {
            userRepository.fetchCurrentUserData()
        }.flatMap { user ->
            Result.success(UpdateUserResponse(user))
        }
    }
}