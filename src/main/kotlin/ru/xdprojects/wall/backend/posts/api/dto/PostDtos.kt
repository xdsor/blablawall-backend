package ru.xdprojects.wall.backend.posts.api.dto

import ru.xdprojects.wall.backend.users.domain.UserEntity
import java.time.LocalDateTime

data class UserPreview(
    val nickname: String,
    val avatarUrl: String?
)

data class PostPreviewDto(
    val id: Long,
    val author: UserPreview,
    val lastReplier: UserPreview?,
    val lastReplyDate: LocalDateTime?,
    val postedAt: LocalDateTime,
    val title: String,
    val preview: String,
    val likes: Int,
    val repliesCount: Int,
)

data class PostDto(
    val id: Long,
    val author: UserPreview,
    val title: String,
    val content: String,
    val postedAt: LocalDateTime,
    val likes: Int,
    val replies: List<PostReplyDto>
)

data class PostReplyDto(
    val id: Long,
    val author: UserPreview,
    val content: String,
    val postedAt: LocalDateTime,
    val replyTo: Long?
)

data class NewPostDto(
    val title: String,
    val content: String
)

data class NewReplyDto(
    val text: String,
    val replyTo: Long?
)