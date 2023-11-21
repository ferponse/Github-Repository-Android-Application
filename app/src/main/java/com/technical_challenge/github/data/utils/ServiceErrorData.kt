package com.technical_challenge.github.data.utils

open class ServiceErrorData(
    val description: String?,
    val statusCode: Int,
    val headers: Map<String, List<String>>,
)