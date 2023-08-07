package com.felipecpdev.jpasqlresultsetmapping.repository;

import com.felipecpdev.jpasqlresultsetmapping.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment,Long> {
}
