package com.felipecpdev.jpasqlresultsetmapping;

import com.felipecpdev.jpasqlresultsetmapping.entity.Post;
import com.felipecpdev.jpasqlresultsetmapping.entity.PostComment;
import com.felipecpdev.jpasqlresultsetmapping.repository.PostCommentRepository;
import com.felipecpdev.jpasqlresultsetmapping.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.stream.LongStream;

@SpringBootApplication
public class JpaSqlResultSetMappingApplication implements CommandLineRunner {

    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;

    public JpaSqlResultSetMappingApplication(PostRepository postRepository, PostCommentRepository postCommentRepository) {
        this.postRepository = postRepository;
        this.postCommentRepository = postCommentRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(JpaSqlResultSetMappingApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        initData();
    }

    public void initData(){
        final int POST_COUNT = 50;
        final int COMMENT_COUNT = 5;

        LongStream.rangeClosed(1, POST_COUNT)
                .forEach(postId -> {
                    Post post = new Post();
                    post.setId(postId);
                    post.setTitle(
                            String.format(
                                    "High-Performance Java Persistence - Chapter %d",
                                    postId
                            )
                    );
                    post.setCreatedOn(
                            LocalDateTime.now().plusDays(postId)
                    );

                    postRepository.save(post);

                    LongStream.rangeClosed(1, COMMENT_COUNT)
                            .forEach(commentOffset -> {
                                long commentId = ((postId - 1) * COMMENT_COUNT) + commentOffset;

                                PostComment postComment = new PostComment();

                                postComment.setId(commentId);
                                postComment.setReview(
                                        String.format("Comment nr. %d - A must read!", commentId)
                                );
                                postComment.setCreatedOn(
                                        LocalDateTime.now().plusDays(postId)
                                                .plusMinutes(commentId)
                                );
                                postComment.setPost(post);
                                postCommentRepository.save(postComment);
                            });
                });
    }
}


