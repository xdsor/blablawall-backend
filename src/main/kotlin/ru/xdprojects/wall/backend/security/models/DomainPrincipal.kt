package ru.xdprojects.wall.backend.security.models

data class DomainPrincipal(
    val userId: String,
    val username: String,
    val profilePictureUrl: String?
)