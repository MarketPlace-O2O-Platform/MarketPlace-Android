package dev.kichan.marketplace.model.data.kakao.adress

data class RoadAddress(
    // 도로명 주소
    val address_name: String,
    val building_name: String,
    val main_building_no: String,
    val region_1depth_name: String,
    val region_2depth_name: String,
    val region_3depth_name: String,
    val road_name: String,
    val sub_building_no: String,
    val underground_yn: String,
    val x: String,
    val y: String,
    val zone_no: String
)