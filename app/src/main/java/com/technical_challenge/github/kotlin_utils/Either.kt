package com.technical_challenge.github.kotlin_utils

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

sealed class Either<out T, out E> {
    data class Success<out T>(val data: T) : Either<T, Nothing>()
    data class Failure<out E>(val error: E) : Either<Nothing, E>()
}

@OptIn(ExperimentalContracts::class)
fun <T1, T2> Either<T1, T2>.isSuccess(
): Boolean {
    contract {
        returns(true) implies (this@isSuccess is Either.Success)
        returns(false) implies (this@isSuccess is Either.Failure)
    }
    return this is Either.Success
}

fun <T> buildSuccess(data: T) = Either.Success(data)

fun <T> buildFailure(error: T) = Either.Failure(error)