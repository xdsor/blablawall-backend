package ru.xdprojects.wall.backend.users.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.xdprojects.wall.backend.security.models.DomainAuthentication
import ru.xdprojects.wall.backend.users.api.dto.UserSelfInfoDto
import ru.xdprojects.wall.backend.users.service.UserService

@RequestMapping("/api/users")
@RestController
class UsersController(
    private val userService: UserService
) {
    @GetMapping("/self-info")
    fun getSelfInfo(authentication: DomainAuthentication): UserSelfInfoDto = userService.getSelfInfo(authentication.name)
}