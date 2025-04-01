package ru.xdprojects.wall.backend.posts.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import ru.xdprojects.wall.backend.users.domain.UserEntity
import java.time.LocalDateTime

@Entity
@Table(schema = "wall_backend", name = "posts")
data class PostEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column
    val title: String,
    @Column
    val content: String,
    @ManyToOne
    @JoinColumn(nullable = false)
    val author: UserEntity,
    @Column
    val createdAt: LocalDateTime,
    @OneToMany(mappedBy = "postEntity", cascade = [CascadeType.ALL], orphanRemoval = true)
    val replies: MutableList<PostReplyEntity> = mutableListOf(),
    @ManyToMany
    @JoinTable(
        schema = "wall_backend",
        name = "post_likes",
        joinColumns = [JoinColumn(name = "post_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val likedBy: MutableList<UserEntity> = mutableListOf()
)

@Entity
@Table(schema = "wall_backend", name = "post_replies")
data class PostReplyEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    val postEntity: PostEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    val author: UserEntity,
    @Column
    val text: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_to")
    val replyTo: PostReplyEntity? = null,
    @Column
    val postedAt: LocalDateTime
)
