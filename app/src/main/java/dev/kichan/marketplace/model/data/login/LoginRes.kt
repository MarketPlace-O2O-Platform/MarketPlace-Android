package dev.kichan.marketplace.model.data.login

data class LoginRes(
    val studentId : Long,
)

data class MemberLoginRes(
    val studentId : Long,
    val cheerTicket: Int
)