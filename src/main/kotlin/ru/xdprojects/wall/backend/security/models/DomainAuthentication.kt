package ru.xdprojects.wall.backend.security.models

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class DomainAuthentication(
    authorities: List<GrantedAuthority>,
    val domainPrincipal: DomainPrincipal,
) : AbstractAuthenticationToken(authorities) {
    override fun getCredentials(): Any? = null
    override fun getPrincipal(): DomainPrincipal = this.domainPrincipal
    override fun isAuthenticated(): Boolean = true
    override fun getName(): String = this.domainPrincipal.userId
}