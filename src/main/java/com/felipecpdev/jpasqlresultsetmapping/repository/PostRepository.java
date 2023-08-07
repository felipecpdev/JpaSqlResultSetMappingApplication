package com.felipecpdev.jpasqlresultsetmapping.repository;

import com.felipecpdev.jpasqlresultsetmapping.dto.PostTitleWithCommentCountDTO;
import com.felipecpdev.jpasqlresultsetmapping.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    @Query(nativeQuery = true)
    List<PostTitleWithCommentCountDTO> postTitleWithCommentCount();
}
