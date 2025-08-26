data class PageableObject(
    val sort: SortObject? = null,
    val offset: Long? = null,
    val paged: Boolean? = null,
    val pageNumber: Int? = null,
    val pageSize: Int? = null,
    val unpaged: Boolean? = null
)