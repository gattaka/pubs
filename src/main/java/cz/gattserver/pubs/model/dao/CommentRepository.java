package cz.gattserver.pubs.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.gattserver.pubs.model.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
