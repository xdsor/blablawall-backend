package ru.xdprojects.wall.backend.users.service

import ru.xdprojects.wall.backend.security.models.DomainPrincipal
import ru.xdprojects.wall.backend.users.api.dto.UserSelfInfoDto

interface UserService {
    fun registerNewUser(domainPrincipal: DomainPrincipal)
    fun getSelfInfo(userId: String): UserSelfInfoDto
}