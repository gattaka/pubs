package cz.gattserver.pubs.facades;

import java.util.List;

import cz.gattserver.pubs.model.dto.CommentDTO;

public interface CommentFacade {

	public List<CommentDTO> findAllComments();

	public void createComment(CommentDTO commentDTO);
}
