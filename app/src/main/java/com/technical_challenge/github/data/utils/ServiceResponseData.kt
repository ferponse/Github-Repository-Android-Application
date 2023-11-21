package com.technical_challenge.github.data.utils

open class ServiceResponseData<T>(
    val optData: T?,
    val statusCode: Int,
    val headers: Map<String, List<String>>,
) {
    val data: T
        get() = optData!!
}