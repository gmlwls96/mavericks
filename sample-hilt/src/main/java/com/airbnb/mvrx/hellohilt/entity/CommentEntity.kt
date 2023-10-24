package com.airbnb.mvrx.hellohilt.entity

data class CommentEntity (
    val commentSerialNo : Long, // 댓글 고유 번호 (피드별로 관리)
    val feedSerialNo : Long,    // 피드 고유 번호
    val userSerialNo: String,   // 사용자 고유 번호
    val userName : String,      // 사용자 이름
    val comment: String,        // 댓글
    val date : Long             // 댓글 남긴 시간
)