package dev.kichan.marketplace.model.data.kakao.adress

data class Address(
    val address: LotNumberAddress,
    val address_name: String,
    val address_type: String,
    val road_address: RoadAddress,
    val x: String,
    val y: String
)