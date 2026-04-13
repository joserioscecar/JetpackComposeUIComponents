package co.fluxis.trm.data.dto

data class TrmResponse(
    val data: TrmData,
    val web: String
)

data class TrmData(
    val unit: String,
    val validityFrom: String,
    val validityTo: String,
    val value: Double,
    val success: Boolean
)
