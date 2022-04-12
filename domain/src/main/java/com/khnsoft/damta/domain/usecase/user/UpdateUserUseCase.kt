package com.khnsoft.damta.domain.usecase.user

import com.khnsoft.damta.common.extension.flatMap
import com.khnsoft.damta.domain.common.UserValidator
import com.khnsoft.damta.domain.error.UserError
import com.khnsoft.damta.domain.model.User
import com.khnsoft.damta.domain.repository.UserRepository
import com.khnsoft.damta.domain.usecase.RequestValue
import com.khnsoft.damta.domain.usecase.ResponseValue
import com.khnsoft.damta.domain.usecase.ResultUseCase
import java.time.LocalDate
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val validator: UserValidator
) : ResultUseCase<UpdateUserUseCase.Request, UpdateUserUseCase.Response> {

    override suspend fun invoke(request: Request): Result<Response> {
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
            Result.success(Response(user))
        }
    }

    data class Request(
        val nickname: String,
        val email: String,
        val birthday: LocalDate
    ) : RequestValue

    data class Response(
        val user: User
    ) : ResponseValue
}
