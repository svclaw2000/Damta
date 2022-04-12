package com.khnsoft.damta.domain.usecase.user

import com.khnsoft.damta.common.extension.flatMap
import com.khnsoft.damta.domain.common.HashGenerator
import com.khnsoft.damta.domain.common.UserValidator
import com.khnsoft.damta.domain.error.UserError
import com.khnsoft.damta.domain.model.User
import com.khnsoft.damta.domain.repository.UserRepository
import com.khnsoft.damta.domain.usecase.RequestValue
import com.khnsoft.damta.domain.usecase.ResponseValue
import com.khnsoft.damta.domain.usecase.ResultUseCase
import java.time.LocalDate
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val validator: UserValidator,
    private val hashGenerator: HashGenerator
) : ResultUseCase<SignUpUseCase.Request, ResponseValue.Empty> {

    override suspend fun invoke(request: Request): Result<ResponseValue.Empty> {
        when {
            !validator.isUsernameValid(request.username) ->
                return Result.failure(UserError.InvalidUsername)
            !validator.isPasswordValid(request.password) ->
                return Result.failure(UserError.InvalidPassword)
            !validator.isEmailValid(request.email) ->
                return Result.failure(UserError.InvalidEmail)
            !validator.isNicknameValid(request.nickname) ->
                return Result.failure(UserError.InvalidNickname)
        }

        val user = User(
            username = request.username,
            nickname = request.nickname,
            birthday = request.birthday,
            email = request.email
        )
        val hashedPassword = hashGenerator.hashPasswordWithSalt(request.password, user.username)

        return userRepository.signUp(user, hashedPassword).flatMap {
            Result.success(ResponseValue.Empty)
        }
    }

    data class Request(
        val username: String,
        val password: String,
        val nickname: String,
        val birthday: LocalDate,
        val email: String
    ) : RequestValue
}
