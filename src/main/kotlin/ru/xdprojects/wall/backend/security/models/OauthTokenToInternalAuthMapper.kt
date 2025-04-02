package ru.xdprojects.wall.backend.security.models

import org.apache.commons.codec.digest.DigestUtils
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Component
import ru.xdprojects.wall.backend.security.models.extractors.OauthTokenInformationExtractor

@Component
class OauthTokenToInternalAuthMapper(
    authenticationInfoExtractors: List<OauthTokenInformationExtractor>,
) {
    private val registrationIdToInfoExtractorName: Map<String, OauthTokenInformationExtractor> =
        authenticationInfoExtractors.associateBy { it.getClientRegistrationId() }

    fun mapToInternalAuth(authentication: OAuth2AuthenticationToken): DomainAuthentication {
        val infoExtractor = registrationIdToInfoExtractorName[authentication.authorizedClientRegistrationId]
            ?: throw RuntimeException("No OauthTokenInformationExtractor for ${authentication.authorizedClientRegistrationId} found")
        return DomainAuthentication(
            authorities = emptyList(),
            domainPrincipal = DomainPrincipal(
                userId = generateUserIdByEmail(infoExtractor.extractEmail(authentication)),
                username = infoExtractor.extractUserName(authentication),
                profilePictureUrl = infoExtractor.extractAvatarUrl(authentication)
            )
        )
    }

    private fun generateUserIdByEmail(email: String): String {
        return DigestUtils.sha256Hex(email)
    }
}