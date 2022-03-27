package com.khnsoft.damta.domain.usecase

import com.khnsoft.damta.common.extension.flatMap
import com.khnsoft.damta.domain.common.HashGenerator
import com.khnsoft.damta.domain.repository.UserRepository
import com.khnsoft.damta.domain.request.SignInRequest
import com.khnsoft.damta.domain.response.EmptyResponse
import javax.inject.Inject

internal class SignInUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val hashGenerator: HashGenerator
) : ResultUseCase<SignInRequest, EmptyResponse> {

    override suspend fun invoke(request: SignInRequest): Result<EmptyResponse> {
        val hashedPassword = hashGenerator.hashPasswordWithSalt(request.password, request.username)

        return userRepository.signIn(request.username, hashedPassword).flatMap {
            Result.success(EmptyResponse)
        }
    }
}