package dev.kichan.marketplace.model.data.login

data class MemberLoginRes(
    val studentId : Long,
    val cheerTicket: Int,
    val account: String,
    val accountNumber: String,
)