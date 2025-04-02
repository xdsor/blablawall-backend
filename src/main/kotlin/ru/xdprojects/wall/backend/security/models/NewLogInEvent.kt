package ru.xdprojects.wall.backend.security.models

import org.springframework.context.ApplicationEvent

data class NewLogInEvent(
    private val source: Any,
    val authentication: DomainAuthentication
) : ApplicationEvent(source)