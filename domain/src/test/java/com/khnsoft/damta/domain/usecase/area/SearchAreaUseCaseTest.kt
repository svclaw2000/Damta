package com.khnsoft.damta.domain.usecase.area

import com.khnsoft.damta.domain.error.AreaError
import com.khnsoft.damta.domain.model.Area
import com.khnsoft.damta.domain.repository.AreaRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SearchAreaUseCaseTest {

    @MockK private lateinit var areaRepository: AreaRepository
    private lateinit var useCase: SearchAreaUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = SearchAreaUseCase(areaRepository)
    }

    @Test
    fun invoke_keyword_returnListOfTwo(): Unit = runBlocking {
        coEvery {
            areaRepository.searchArea("keyword")
        } returns Result.success(listOf(Area(), Area()))

        val result = useCase(SearchAreaUseCase.Request("keyword"))

        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.areaList?.size)
    }

    @Test
    fun invoke_emptyKeyword_throwEmptyKeywordError(): Unit = runBlocking {

        val result = useCase(SearchAreaUseCase.Request(""))

        assertTrue(result.isFailure)
        assertEquals(AreaError.EmptyKeyword, result.exceptionOrNull())
    }
}
