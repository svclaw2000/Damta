package com.khnsoft.damta.domain.usecase

import com.khnsoft.damta.domain.request.Request
import com.khnsoft.damta.domain.response.Response

interface ResultUseCase<R : Request, T : Response> {

    suspend operator fun invoke(request: R): Result<T>
}
