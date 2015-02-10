package cz.gattserver.pubs.facades;

import java.util.List;

import cz.gattserver.pubs.model.dto.CommentDTO;
import cz.gattserver.pubs.model.dto.PubDTO;

public interface CommentFacade {

	public List<CommentDTO> getAllComments();

	public Long createComment(PubDTO pubDTO, CommentDTO commentDTO);

	public CommentDTO getById(Long id);

	public Long updateComment(CommentDTO comment);
}
