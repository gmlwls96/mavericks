package com.airbnb.mvrx.hellohilt.entity

data class FeedEntity (
    val feedSerialNo: Int, // 피드 고유 번호
    val userSerialNo: Int, // 사용자 고유 번호
    val userName : String,  // 사용자 이름
    val imageUrl: String,  // 피드 이미지
    val feedText: String,   // 피드 텍스트
    val isLike: Boolean,    // 좋아요 여부
    val likeCount: Int      // 좋아요 개수
)