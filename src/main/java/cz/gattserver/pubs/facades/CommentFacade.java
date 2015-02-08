package cz.gattserver.pubs.facades;

import java.util.List;

import cz.gattserver.pubs.model.dto.CommentDTO;
import cz.gattserver.pubs.model.dto.PubDTO;

public interface CommentFacade {

	public List<CommentDTO> findAllComments();

	public Long createComment(PubDTO pubDTO, CommentDTO commentDTO);

	public CommentDTO findById(Long id);
}
