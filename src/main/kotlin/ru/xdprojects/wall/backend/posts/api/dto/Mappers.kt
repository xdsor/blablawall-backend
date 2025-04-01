package ru.xdprojects.wall.backend.posts.api.dto

import ru.xdprojects.wall.backend.posts.domain.PostEntity
import ru.xdprojects.wall.backend.posts.domain.PostReplyEntity
import ru.xdprojects.wall.backend.users.domain.UserEntity

fun PostEntity.toPostDto(): PostDto {
    return PostDto(
        id = this.id!!,
        author = this.author.toUserPreview(),
        title = this.title,
        content = this.content,
        postedAt = this.createdAt,
        likes = this.likedBy.size,
        replies = this.replies.map { it.toDto() }
    )
}

fun PostReplyEntity.toDto(): PostReplyDto {
    return PostReplyDto(
        id = this.id!!,
        author = this.author.toUserPreview(),
        content = this.text,
        postedAt = this.postedAt,
        replyTo = this.replyTo?.id
    )
}

fun UserEntity.toUserPreview() = UserPreview(
    nickname = this.name,
    avatarUrl = this.profileImageUrl
)