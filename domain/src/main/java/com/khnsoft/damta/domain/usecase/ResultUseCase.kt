package com.khnsoft.damta.domain.usecase

interface ResultUseCase<R : Request, T : Response> {

    suspend operator fun invoke(request: R): Result<T>
}
