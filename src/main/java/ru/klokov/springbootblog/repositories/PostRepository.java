package ru.klokov.springbootblog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.klokov.springbootblog.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
