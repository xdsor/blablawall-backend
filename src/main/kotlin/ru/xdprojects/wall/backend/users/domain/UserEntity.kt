package ru.xdprojects.wall.backend.users.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(schema = "wall_backend", name = "users")
data class UserEntity(
    @Id
    val id: String,
    @Column
    val name: String,
    @Column
    val profileImageUrl: String? = null,
    @Column
    val activated: Boolean,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invited_by")
    val invitedBy: UserEntity?,
    @Column
    val dateJoined: LocalDateTime
)
