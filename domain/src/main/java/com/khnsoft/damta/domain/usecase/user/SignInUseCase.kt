package com.khnsoft.damta.domain.usecase.user

import com.khnsoft.damta.common.extension.flatMap
import com.khnsoft.damta.domain.common.HashGenerator
import com.khnsoft.damta.domain.repository.UserRepository
import com.khnsoft.damta.domain.usecase.Request
import com.khnsoft.damta.domain.usecase.Response
import com.khnsoft.damta.domain.usecase.ResultUseCase
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val hashGenerator: HashGenerator
) : ResultUseCase<SignInUseCase.SignInRequest, Response.Empty> {

    override suspend fun invoke(request: SignInRequest): Result<Response.Empty> {
        val hashedPassword = hashGenerator.hashPasswordWithSalt(request.password, request.username)

        return userRepository.signIn(request.username, hashedPassword).flatMap {
            Result.success(Response.Empty)
        }
    }

    data class SignInRequest(
        val username: String,
        val password: String
    ) : Request
}
