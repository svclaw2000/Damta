package com.khnsoft.damta.domain.usecase

import com.khnsoft.damta.domain.common.HashGenerator
import com.khnsoft.damta.domain.common.UserValidator
import com.khnsoft.damta.domain.model.User
import com.khnsoft.damta.domain.repository.UserRepository
import com.khnsoft.damta.domain.request.SignUpRequest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class SignUpUseCaseTest {

    @MockK private lateinit var userValidator: UserValidator
    @MockK private lateinit var userRepository: UserRepository
    @MockK private lateinit var hashGenerator: HashGenerator
    private lateinit var useCase: SignUpUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = SignUpUseCase(
            userRepository = userRepository,
            validator = userValidator,
            hashGenerator = hashGenerator
        )
    }

    @Test
    fun invoke_validInformation_isSuccess(): Unit = runBlocking {
        every {
            userValidator.isEmailValid("email")
        } returns true
        every {
            userValidator.isPasswordValid("password")
        } returns true
        every {
            userValidator.isNicknameValid("nickname")
        } returns true
        every {
            userValidator.isUsernameValid("username")
        } returns true

        every {
            hashGenerator.hashPasswordWithSalt("password", "username")
        } returns "password with salt"

        coEvery {
            userRepository.signUp(
                User(
                    username = "username",
                    nickname = "nickname",
                    birthday = LocalDate.of(2022, 1, 1),
                    email = "email"
                ),
                "password with salt"
            )
        } returns Result.success(Unit)

        val result = useCase(SignUpRequest(
            username = "username",
            password = "password",
            nickname = "nickname",
            birthday = LocalDate.of(2022, 1, 1),
            email = "email"
        ))

        assertTrue(result.isSuccess)
    }

    @Test
    fun invoke_invalidEmail_isFailure(): Unit = runBlocking {
        every {
            userValidator.isEmailValid("email")
        } returns false
        every {
            userValidator.isPasswordValid("password")
        } returns true
        every {
            userValidator.isNicknameValid("nickname")
        } returns true
        every {
            userValidator.isUsernameValid("username")
        } returns true

        val result = useCase(SignUpRequest(
            username = "username",
            password = "password",
            nickname = "nickname",
            birthday = LocalDate.of(2022, 1, 1),
            email = "email"
        ))

        assertTrue(result.isFailure)
    }

    @Test
    fun invoke_invalidPassword_isFailure(): Unit = runBlocking {
        every {
            userValidator.isEmailValid("email")
        } returns true
        every {
            userValidator.isPasswordValid("password")
        } returns false
        every {
            userValidator.isNicknameValid("nickname")
        } returns true
        every {
            userValidator.isUsernameValid("username")
        } returns true

        val result = useCase(SignUpRequest(
            username = "username",
            password = "password",
            nickname = "nickname",
            birthday = LocalDate.of(2022, 1, 1),
            email = "email"
        ))

        assertTrue(result.isFailure)
    }

    @Test
    fun invoke_invalidNickname_isFailure(): Unit = runBlocking {
        every {
            userValidator.isEmailValid("email")
        } returns true
        every {
            userValidator.isPasswordValid("password")
        } returns true
        every {
            userValidator.isNicknameValid("nickname")
        } returns false
        every {
            userValidator.isUsernameValid("username")
        } returns true

        val result = useCase(SignUpRequest(
            username = "username",
            password = "password",
            nickname = "nickname",
            birthday = LocalDate.of(2022, 1, 1),
            email = "email"
        ))

        assertTrue(result.isFailure)
    }

    @Test
    fun invoke_invalidUsername_isFailure(): Unit = runBlocking {
        every {
            userValidator.isEmailValid("email")
        } returns true
        every {
            userValidator.isPasswordValid("password")
        } returns true
        every {
            userValidator.isNicknameValid("nickname")
        } returns true
        every {
            userValidator.isUsernameValid("username")
        } returns false

        val result = useCase(SignUpRequest(
            username = "username",
            password = "password",
            nickname = "nickname",
            birthday = LocalDate.of(2022, 1, 1),
            email = "email"
        ))

        assertTrue(result.isFailure)
    }
}