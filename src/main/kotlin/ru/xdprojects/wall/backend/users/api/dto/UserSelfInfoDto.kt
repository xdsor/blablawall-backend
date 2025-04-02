package ru.xdprojects.wall.backend.users.api.dto

data class UserSelfInfoDto(
    val username: String,
    val activated: Boolean,
    val userProfileUrl: String?
)
