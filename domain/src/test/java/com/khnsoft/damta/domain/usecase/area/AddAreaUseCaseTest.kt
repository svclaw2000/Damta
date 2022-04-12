package com.khnsoft.damta.domain.usecase.area

import com.khnsoft.damta.domain.error.AreaError
import com.khnsoft.damta.domain.model.AreaType
import com.khnsoft.damta.domain.model.Place
import com.khnsoft.damta.domain.repository.AreaRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AddAreaUseCaseTest {

    @MockK private lateinit var areaRepository: AreaRepository
    private lateinit var useCase: AddAreaUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = AddAreaUseCase(areaRepository)
    }

    @Test
    fun invoke_validInformation_isSuccess(): Unit = runBlocking {
        coEvery {
            areaRepository.addArea(any())
        } returns Result.success(1)

        val result = useCase(
            AddAreaUseCase.Request(
                name = "name",
                type = AreaType.OPENED,
                place = Place(
                    name = null,
                    address = null,
                    roadAddress = null,
                    x = 0.0,
                    y = 0.0
                ),
                facilities = emptySet()
            )
        )

        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.areaId)
    }

    @Test
    fun invoke_emptyName_throwsEmptyNameError(): Unit = runBlocking {

        val result = useCase(
            AddAreaUseCase.Request(
                name = "",
                type = AreaType.OPENED,
                place = Place(
                    name = null,
                    address = null,
                    roadAddress = null,
                    x = 0.0,
                    y = 0.0
                ),
                facilities = emptySet()
            )
        )

        assertTrue(result.isFailure)
        assertEquals(AreaError.EmptyName, result.exceptionOrNull())
    }
}
