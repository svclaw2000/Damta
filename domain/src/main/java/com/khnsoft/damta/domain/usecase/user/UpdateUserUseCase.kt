package com.khnsoft.damta.domain.usecase.user

import com.khnsoft.damta.common.extension.flatMap
import com.khnsoft.damta.domain.common.UserValidator
import com.khnsoft.damta.domain.error.UserError
import com.khnsoft.damta.domain.model.User
import com.khnsoft.damta.domain.repository.UserRepository
import com.khnsoft.damta.domain.usecase.Request
import com.khnsoft.damta.domain.usecase.Response
import com.khnsoft.damta.domain.usecase.ResultUseCase
import java.time.LocalDate
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val validator: UserValidator
) : ResultUseCase<UpdateUserUseCase.UpdateUserRequest, UpdateUserUseCase.UpdateUserResponse> {

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

    data class UpdateUserRequest(
        val nickname: String,
        val email: String,
        val birthday: LocalDate
    ) : Request

    data class UpdateUserResponse(
        val user: User
    ) : Response
}
