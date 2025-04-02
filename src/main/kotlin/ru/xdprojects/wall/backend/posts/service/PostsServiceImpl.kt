package ru.xdprojects.wall.backend.posts.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import ru.xdprojects.wall.backend.posts.api.dto.*
import ru.xdprojects.wall.backend.posts.domain.PostEntity
import ru.xdprojects.wall.backend.posts.domain.PostReplyEntity
import ru.xdprojects.wall.backend.posts.repository.PostRepository
import ru.xdprojects.wall.backend.users.repository.UserRepository
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrNull

@Service
class PostsServiceImpl(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) : PostsService {
    override fun getPostPreviews(pageable: Pageable): Page<PostPreviewDto> {
        return postRepository.findAll(pageable).map { p ->
            val lastReply = p.replies.maxByOrNull { it.postedAt }
            return@map PostPreviewDto(
                id = p.id!!,
                author = p.author.toUserPreview(),
                lastReplier = lastReply?.author?.toUserPreview(),
                lastReplyDate = lastReply?.postedAt,
                postedAt = p.createdAt,
                title = p.title,
                preview = p.content,
                likes = p.likedBy.size,
                repliesCount = p.replies.size
            )
        }
    }

    override fun getPostById(id: Long): PostDto {
        return postRepository.findById(id).getOrNull()?.toPostDto() ?: throw PostNotFoundException()
    }

    override fun createNewPost(
        newPostDto: NewPostDto,
        authorId: String
    ): PostDto {
        val postEntity = PostEntity(
            title = newPostDto.title,
            content = newPostDto.content,
            author = userRepository.findById(authorId).getOrNull() ?: throw RuntimeException("User by id $authorId not found"),
            createdAt = LocalDateTime.now(),
            replies = mutableListOf(),
            likedBy = mutableListOf(),
        )
        return postRepository.save(postEntity).toPostDto()
    }

    override fun createNewReply(
        postId: Long,
        newReplyDto: NewReplyDto,
        authorId: String
    ): PostDto {
        val author = userRepository.findById(authorId).getOrNull() ?: throw RuntimeException("User by id $authorId not found")
        val post = postRepository.findById(postId).getOrNull() ?: throw PostNotFoundException()
        val replyTo = newReplyDto.replyTo?.let { replyId ->
            post.replies.find { it.id == replyId } ?: throw RuntimeException("Reply by id $replyId not found")
        }
        val newReply = PostReplyEntity(
            postEntity = post,
            author = author,
            text = newReplyDto.text,
            replyTo = replyTo,
            postedAt = LocalDateTime.now(),
        )
        post.replies.add(newReply)
        return postRepository.save(post).toPostDto()
    }
}