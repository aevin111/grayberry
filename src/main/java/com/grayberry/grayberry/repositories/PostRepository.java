package com.grayberry.grayberry.repositories;

import java.time.LocalDateTime;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.grayberry.grayberry.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>, PagingAndSortingRepository<Post, Integer>
{
    public Page<Post> findAll(Pageable pageable);
    public Page<Post> findByPostTitle(String postTitle, Pageable pageable);
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO post VALUES (NULL, ?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
    public void createPost(Integer userId, LocalDateTime dateTimePosted, String postTitle, String postText, Boolean commentAllowed);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM post WHERE post_id = ?1 AND user_id = ?2", nativeQuery = true)
    public int deletePost(Integer postId, Integer userId);
}
