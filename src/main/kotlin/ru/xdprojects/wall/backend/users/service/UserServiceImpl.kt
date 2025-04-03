package ru.xdprojects.wall.backend.users.service

import org.springframework.stereotype.Service
import ru.xdprojects.wall.backend.security.models.DomainPrincipal
import ru.xdprojects.wall.backend.users.api.dto.UserSelfInfoDto
import ru.xdprojects.wall.backend.users.domain.UserEntity
import ru.xdprojects.wall.backend.users.repository.UserRepository
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrNull

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {
    override fun registerNewUser(domainPrincipal: DomainPrincipal) {
        userRepository.save(domainPrincipal.toUserEntity())
    }

    override fun getSelfInfo(userId: String): UserSelfInfoDto = userRepository.findById(userId).getOrNull()
        ?.toUserSelfInfoDto()
        ?: throw RuntimeException("User not found")
}

fun DomainPrincipal.toUserEntity(): UserEntity {
    return UserEntity(
        id = this.userId,
        name = this.username,
        profileImageUrl = this.profilePictureUrl,
        activated = false,
        invitedBy = null,
        dateJoined = LocalDateTime.now(),
    )
}

fun UserEntity.toUserSelfInfoDto(): UserSelfInfoDto = UserSelfInfoDto(
    username = this.name,
    activated = this.activated,
    userProfileUrl = this.profileImageUrl
)