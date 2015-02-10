package cz.gattserver.pubs.facades.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cz.gattserver.pubs.facades.CommentFacade;
import cz.gattserver.pubs.facades.SecurityFacade;
import cz.gattserver.pubs.model.dao.CommentRepository;
import cz.gattserver.pubs.model.dao.PubRepository;
import cz.gattserver.pubs.model.domain.Comment;
import cz.gattserver.pubs.model.domain.Pub;
import cz.gattserver.pubs.model.domain.User;
import cz.gattserver.pubs.model.dto.CommentDTO;
import cz.gattserver.pubs.model.dto.PubDTO;
import cz.gattserver.pubs.util.MappingService;

@Transactional
@Component
public class CommentFacadeImpl implements CommentFacade {

	@Autowired
	private MappingService mapper;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PubRepository pubRepository;

	@Autowired
	private SecurityFacade securityFacade;

	public List<CommentDTO> getAllComments() {
		return mapper.map(commentRepository.findAll(), CommentDTO.class);
	}

	@Override
	public Long createComment(PubDTO pubDTO, CommentDTO commentDTO) {
		Comment comment = mapper.map(commentDTO, Comment.class);
		comment.setCreationDate(Calendar.getInstance().getTime());
		comment.setAuthor(mapper.map(securityFacade.getCurrentUser(), User.class));
		Long id = commentRepository.save(comment).getId();

		Pub pub = mapper.map(pubDTO, Pub.class);
		pub.getComments().add(comment);
		pubRepository.save(pub);

		return id;
	}

	@Override
	public CommentDTO getById(Long id) {
		return mapper.map(commentRepository.findOne(id), CommentDTO.class);
	}

	@Override
	public Long updateComment(CommentDTO commentDTO) {
		Comment comment = mapper.map(commentDTO, Comment.class);
		comment.setModificationDate(Calendar.getInstance().getTime());
		Long id = commentRepository.save(comment).getId();
		return id;
	}
}
