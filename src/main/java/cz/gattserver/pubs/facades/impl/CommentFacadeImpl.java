package cz.gattserver.pubs.facades.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cz.gattserver.pubs.facades.CommentFacade;
import cz.gattserver.pubs.facades.SecurityFacade;
import cz.gattserver.pubs.model.dao.CommentRepository;
import cz.gattserver.pubs.model.domain.Comment;
import cz.gattserver.pubs.model.domain.User;
import cz.gattserver.pubs.model.dto.CommentDTO;
import cz.gattserver.pubs.util.MappingService;

@Transactional
@Component
public class CommentFacadeImpl implements CommentFacade {

	@Autowired
	private MappingService mapper;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private SecurityFacade securityFacade;

	public List<CommentDTO> findAllComments() {
		return mapper.map(commentRepository.findAll(), CommentDTO.class);
	}

	@Override
	public void createComment(CommentDTO commentDTO) {
		Comment comment = mapper.map(commentDTO, Comment.class);
		comment.setCreationDate(Calendar.getInstance().getTime());
		comment.setAuthor(mapper.map(securityFacade.getCurrentUser(), User.class));
		commentRepository.save(comment);
	}
}
