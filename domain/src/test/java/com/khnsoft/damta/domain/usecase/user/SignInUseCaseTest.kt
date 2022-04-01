package com.khnsoft.damta.domain.usecase.user

import com.khnsoft.damta.domain.common.HashGenerator
import com.khnsoft.damta.domain.error.UserError
import com.khnsoft.damta.domain.repository.UserRepository
import com.khnsoft.damta.domain.request.user.SignInRequest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SignInUseCaseTest {

    @MockK private lateinit var userRepository: UserRepository
    @MockK private lateinit var hashGenerator: HashGenerator
    private lateinit var useCase: SignInUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = SignInUseCase(
            userRepository = userRepository,
            hashGenerator = hashGenerator
        )
    }

    @Test
    fun invoke_validAccount_isSuccess(): Unit = runBlocking {
        every {
            hashGenerator.hashPasswordWithSalt("password", "username")
        } returns "password with salt"

        coEvery {
            userRepository.signIn("username", "password with salt")
        } returns Result.success(Unit)

        val result = useCase(SignInRequest("username", "password"))

        assertTrue(result.isSuccess)
    }

    @Test
    fun invoke_invalidAccount_isFailure(): Unit = runBlocking {
        every {
            hashGenerator.hashPasswordWithSalt("password", "username")
        } returns "password with salt"

        coEvery {
            userRepository.signIn("username", "password with salt")
        } returns Result.failure(UserError.InvalidUsernameOrPassword())

        val result = useCase(SignInRequest("username", "password"))

        assertTrue(result.isFailure)
        assertEquals(UserError.InvalidUsernameOrPassword(), result.exceptionOrNull())
    }
}