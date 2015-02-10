package cz.gattserver.pubs.facades.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cz.gattserver.pubs.facades.PubFacade;
import cz.gattserver.pubs.model.dao.PubRepository;
import cz.gattserver.pubs.model.domain.Pub;
import cz.gattserver.pubs.model.dto.PubDTO;
import cz.gattserver.pubs.util.MappingService;

@Transactional
@Component
public class PubFacadeImpl implements PubFacade {

	@Autowired
	private MappingService mapper;

	@Autowired
	private PubRepository pubRepository;

	public List<PubDTO> findAllPubs() {
		return mapper.map(pubRepository.findAll(), PubDTO.class);
	}

	@Override
	public Long createPub(PubDTO pubDTO) {
		Pub pub = mapper.map(pubDTO, Pub.class);
		pub.setCreationDate(Calendar.getInstance().getTime());
		return pubRepository.save(pub).getId();
	}

	@Override
	public PubDTO getById(Long id) {
		return mapper.map(pubRepository.findOne(id), PubDTO.class);
	}

	@Override
	public PubDTO getByName(String pubName) {
		return mapper.map(pubRepository.findByName(pubName), PubDTO.class);
	}
}
