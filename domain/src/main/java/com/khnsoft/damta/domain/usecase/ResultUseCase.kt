package com.khnsoft.damta.domain.usecase

interface ResultUseCase<R : RequestValue, T : ResponseValue> {

    suspend operator fun invoke(request: R): Result<T>
}
