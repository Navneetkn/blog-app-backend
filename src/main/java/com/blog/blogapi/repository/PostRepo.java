package com.blog.blogapi.repository;

import com.blog.blogapi.entities.Category;
import com.blog.blogapi.entities.Post;
import com.blog.blogapi.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer> {
    List<Post> findByUser(Users users);
    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String search);

    @Query("select p from Post p where p.title like :key")
    List<Post> searchByTitle(@Param("key") String search);
}
