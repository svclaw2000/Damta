package com.khnsoft.damta.domain.usecase.user

import com.khnsoft.damta.common.extension.flatMap
import com.khnsoft.damta.domain.common.HashGenerator
import com.khnsoft.damta.domain.repository.UserRepository
import com.khnsoft.damta.domain.usecase.RequestValue
import com.khnsoft.damta.domain.usecase.ResponseValue
import com.khnsoft.damta.domain.usecase.ResultUseCase
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val hashGenerator: HashGenerator
) : ResultUseCase<SignInUseCase.Request, ResponseValue.Empty> {

    override suspend fun invoke(request: Request): Result<ResponseValue.Empty> {
        val hashedPassword = hashGenerator.hashPasswordWithSalt(request.password, request.username)

        return userRepository.signIn(request.username, hashedPassword).flatMap {
            Result.success(ResponseValue.Empty)
        }
    }

    data class Request(
        val username: String,
        val password: String
    ) : RequestValue
}
