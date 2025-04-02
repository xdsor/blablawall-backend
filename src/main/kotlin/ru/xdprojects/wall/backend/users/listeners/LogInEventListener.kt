package ru.xdprojects.wall.backend.users.listeners

import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import ru.xdprojects.wall.backend.security.models.NewLogInEvent
import ru.xdprojects.wall.backend.users.repository.UserRepository
import ru.xdprojects.wall.backend.users.service.UserService

@Component
class LogInEventListener(
    private val userRepository: UserRepository,
    private val userService: UserService
) : ApplicationListener<NewLogInEvent> {
    override fun onApplicationEvent(event: NewLogInEvent) {
        val userId = event.authentication.name
        if (!userRepository.existsById(userId)) {
            userService.registerNewUser(event.authentication.domainPrincipal)
        }
    }
}