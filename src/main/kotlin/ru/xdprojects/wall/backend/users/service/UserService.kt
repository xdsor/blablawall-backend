package ru.xdprojects.wall.backend.users.service

import ru.xdprojects.wall.backend.security.models.DomainPrincipal

interface UserService {
    fun registerNewUser(domainPrincipal: DomainPrincipal)
}