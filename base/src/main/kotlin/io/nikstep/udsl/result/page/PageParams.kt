package io.nikstep.udsl.result.page

data class PageParams(
    val limit: Int,
    val offset: Long,
) {
    companion object {
        fun by(pageNumber: Int, pageSize: Int) =
            PageParams(
                limit = pageSize,
                offset = ((pageNumber - 1) * pageSize).toLong(),
            )
    }
}
