package com.airbnb.mvrx.hellohilt.entity

data class BannerEntity(
    val bannerImage : String,       // 배너 이미지 url
    val bannerClickLink : String,   // 배너 클릭시 이동할 링크
)