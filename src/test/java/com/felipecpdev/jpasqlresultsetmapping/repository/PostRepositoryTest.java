package com.felipecpdev.jpasqlresultsetmapping.repository;

import com.felipecpdev.jpasqlresultsetmapping.dto.PostTitleWithCommentCountDTO;
import com.felipecpdev.jpasqlresultsetmapping.dto.PostWithCommentCountDTO;
import com.felipecpdev.jpasqlresultsetmapping.entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class PostRepositoryTest {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    /*JPA SqlResultSetMapping – EntityResult*/
    @Test
    void testPostWithCommentByRank() {
        final int POST_RESULT_COUNT = 5;
        List<?> postAndCommentList = entityManager
                .createNamedQuery("PostWithCommentByRank")
                .setParameter("titlePattern", "High-Performance Java Persistence %")
                .setParameter("rank", POST_RESULT_COUNT)
                .getResultList();
        System.out.println(postAndCommentList.size());
        postAndCommentList.forEach(System.out::println);
    }

    //JPA SqlResultSetMapping – ConstructorResult
    @Test
    void testPostTitleWithCommentCount() {
        List<PostTitleWithCommentCountDTO> postTitleWithCommentCountDTOS =
                postRepository.postTitleWithCommentCount();
        postTitleWithCommentCountDTOS.forEach(pc -> {
            System.out.println(pc.getPostTitle());
            System.out.println(pc.getCommentCount());
        });
    }


    /*JPA SqlResultSetMapping – ColumnResult
     */
    @Test
    void testPostWithCommentCount() {
        List<Object[]> postWithCommentCountList = entityManager
                .createNamedQuery("Post.postWithCommentCount")
                .getResultList();

        List<PostWithCommentCountDTO> postListDTO = postWithCommentCountList.stream()
                .map(obj -> new PostWithCommentCountDTO((Post) obj[0], (Long) obj[1])).toList();

        for (PostWithCommentCountDTO postWithCommentCountDTO : postListDTO) {
            System.out.println(postWithCommentCountDTO.getTitle());
        }
    }

}