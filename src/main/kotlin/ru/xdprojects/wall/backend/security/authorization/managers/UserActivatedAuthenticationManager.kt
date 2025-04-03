package ru.xdprojects.wall.backend.security.authorization.managers

import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.access.intercept.RequestAuthorizationContext
import org.springframework.stereotype.Component
import ru.xdprojects.wall.backend.security.models.DomainAuthentication
import ru.xdprojects.wall.backend.users.service.UserService
import java.util.function.Supplier

@Component
class UserActivatedAuthenticationManager(
    private val userService: UserService
) : AuthorizationManager<RequestAuthorizationContext> {
    @Deprecated("Deprecated by the Spring team, but required to override")
    override fun check(
        authentication: Supplier<Authentication>,
        `object`: RequestAuthorizationContext
    ): AuthorizationDecision? = authorize(authentication, `object`)

    override fun authorize(
        authentication: Supplier<Authentication>,
        `object`: RequestAuthorizationContext
    ): AuthorizationDecision {
        val accessGranted = false
        return with(authentication.get()) {
            if (this !is DomainAuthentication) return AuthorizationDecision(accessGranted)
            AuthorizationDecision(userService.getSelfInfo(this.name).activated)
        }.let {
            if (!it.isGranted) throw AuthorizationDeniedException("Your profile is not activated")
            else it
        }
    }
}