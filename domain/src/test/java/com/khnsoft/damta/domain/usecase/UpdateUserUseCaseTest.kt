package com.khnsoft.damta.domain.usecase

import com.khnsoft.damta.domain.common.UserValidator
import com.khnsoft.damta.domain.error.UserError
import com.khnsoft.damta.domain.model.User
import com.khnsoft.damta.domain.repository.UserRepository
import com.khnsoft.damta.domain.request.UpdateUserRequest
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class UpdateUserUseCaseTest {

    @MockK private lateinit var userValidator: UserValidator
    @MockK private lateinit var userRepository: UserRepository
    private lateinit var useCase: UpdateUserUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = UpdateUserUseCase(
            userRepository = userRepository,
            validator = userValidator
        )
    }

    @Test
    fun invoke_validInformation_isSuccess(): Unit = runBlocking {
        val updatedUser = User(
            id = 0,
            username = "username",
            nickname = "nickname",
            birthday = LocalDate.of(2022, 3, 27),
            email = "email"
        )

        every {
            userValidator.isNicknameValid("nickname")
        } returns true
        every {
            userValidator.isEmailValid("email")
        } returns true

        coEvery {
            userRepository.updateUser("email", "nickname", LocalDate.of(2022, 3, 27))
        } returns Result.success(Unit)

        coEvery {
            userRepository.fetchCurrentUserData()
        } returns Result.success(updatedUser)

        val result = useCase(UpdateUserRequest(
            nickname = "nickname",
            email = "email",
            birthday = LocalDate.of(2022, 3, 27)
        ))

        assertTrue(result.isSuccess)
        assertEquals(updatedUser, result.getOrNull()?.user)
    }

    @Test
    fun invoke_notSignedIn_isFailure(): Unit = runBlocking {
        every {
            userValidator.isNicknameValid("nickname")
        } returns true
        every {
            userValidator.isEmailValid("email")
        } returns true

        coEvery {
            userRepository.updateUser("email", "nickname", LocalDate.of(2022, 3, 27))
        } returns Result.failure(UserError.NoSuchUser())

        val result = useCase(UpdateUserRequest(
            nickname = "nickname",
            email = "email",
            birthday = LocalDate.of(2022, 3, 27)
        ))

        assertTrue(result.isFailure)
        assertEquals(UserError.NoSuchUser(), result.exceptionOrNull())
    }

    @Test
    fun invoke_invalidNickname_isFailure(): Unit = runBlocking {
        every {
            userValidator.isNicknameValid("nickname")
        } returns false
        every {
            userValidator.isEmailValid("email")
        } returns true

        val result = useCase(UpdateUserRequest(
            nickname = "nickname",
            email = "email",
            birthday = LocalDate.of(2022, 3, 27)
        ))

        assertTrue(result.isFailure)
        assertEquals(UserError.InvalidNickname, result.exceptionOrNull())
    }

    @Test
    fun invoke_invalidEmail_isFailure(): Unit = runBlocking {
        every {
            userValidator.isNicknameValid("nickname")
        } returns true
        every {
            userValidator.isEmailValid("email")
        } returns false

        val result = useCase(UpdateUserRequest(
            nickname = "nickname",
            email = "email",
            birthday = LocalDate.of(2022, 3, 27)
        ))

        assertTrue(result.isFailure)
        assertEquals(UserError.InvalidEmail, result.exceptionOrNull())
    }
}