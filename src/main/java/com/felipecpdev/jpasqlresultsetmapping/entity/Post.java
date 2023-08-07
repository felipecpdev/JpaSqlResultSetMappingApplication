package com.felipecpdev.jpasqlresultsetmapping.entity;

import com.felipecpdev.jpasqlresultsetmapping.dto.PostTitleWithCommentCountDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;


@NamedNativeQuery(
        name = "PostWithCommentByRank",
        query = """
        SELECT *
        FROM (
          SELECT
            *,
            DENSE_RANK() OVER (
            ORDER BY
              "p.created_on",
              "p.id"
            ) rank
          FROM (
            SELECT
              p.id AS "p.id", p.created_on AS "p.created_on",
              p.title AS "p.title", pc.post_id AS "pc.post_id",
              pc.id as "pc.id", pc.created_on AS "pc.created_on",
              pc.review AS "pc.review"
            FROM post p
            LEFT JOIN post_comment pc ON p.id = pc.post_id
            WHERE p.title LIKE :titlePattern
            ORDER BY p.created_on
          ) p_pc
        ) p_pc_r
        WHERE p_pc_r.rank <= :rank
        """,
        resultSetMapping = "PostWithCommentByRankMapping"
)
@NamedNativeQuery(
        name = "Post.postTitleWithCommentCount",
        query = """
        SELECT
          p.id AS "p.id",
          p.title AS "p.title",
          COUNT(pc.*) AS "comment_count"
        FROM post_comment pc
        LEFT JOIN post p ON p.id = pc.post_id
        GROUP BY p.id, p.title
        ORDER BY p.id
        """,
        resultSetMapping = "PostTitleWithCommentCountMapping"
)
@NamedNativeQuery(
        name = "Post.postWithCommentCount",
        query = """
        SELECT
          p.id AS "p.id",
          p.title AS "p.title",
          p.created_on AS "p.created_on",
          COUNT(pc.*) AS "comment_count"
        FROM post_comment pc
        LEFT JOIN post p ON p.id = pc.post_id
        GROUP BY p.id, p.title
        ORDER BY p.id
        """,
        resultSetMapping = "PostWithCommentCountMapping"
)
@SqlResultSetMapping(
        name = "PostWithCommentByRankMapping",
        entities = {
                @EntityResult(
                        entityClass = Post.class,
                        fields = {
                                @FieldResult(name = "id", column = "p.id"),
                                @FieldResult(name = "createdOn", column = "p.created_on"),
                                @FieldResult(name = "title", column = "p.title"),
                        }
                ),
                @EntityResult(
                        entityClass = PostComment.class,
                        fields = {
                                @FieldResult(name = "id", column = "pc.id"),
                                @FieldResult(name = "createdOn", column = "pc.created_on"),
                                @FieldResult(name = "review", column = "pc.review"),
                                @FieldResult(name = "post", column = "pc.post_id"),
                        }
                )
        }
)
@SqlResultSetMapping(
        name = "PostTitleWithCommentCountMapping",
        classes = {
                @ConstructorResult(
                        columns = {
                                @ColumnResult(name = "p.title"),
                                @ColumnResult(name = "comment_count", type = int.class)
                        },
                        targetClass = PostTitleWithCommentCountDTO.class
                )
        }
)
@SqlResultSetMapping(
        name = "PostWithCommentCountMapping",
        entities = @EntityResult(
                entityClass = Post.class,
                fields = {
                        @FieldResult(name = "id", column = "p.id"),
                        @FieldResult(name = "createdOn", column = "p.created_on"),
                        @FieldResult(name = "title", column = "p.title"),
                }
        ),
        columns = @ColumnResult(
                name = "comment_count",
                type = Long.class
        )
)
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    private String title;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "post")
    private List<PostComment>postCommentList;


    public Post() {
    }

    public Post(Long id, LocalDateTime createdOn, String title, List<PostComment> postCommentList) {
        this.id = id;
        this.createdOn = createdOn;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public List<PostComment> getPostCommentList() {
        return postCommentList;
    }

    public void setPostCommentList(List<PostComment> postCommentList) {
        this.postCommentList = postCommentList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
