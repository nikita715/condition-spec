package io.nikstep.udsl.result.page

data class Page<T>(
    val values: List<T>,
    val total: Long,
)