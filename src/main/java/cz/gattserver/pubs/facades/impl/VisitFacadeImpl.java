package cz.gattserver.pubs.facades.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cz.gattserver.pubs.facades.VisitFacade;
import cz.gattserver.pubs.model.dao.VisitRepository;
import cz.gattserver.pubs.model.domain.Visit;
import cz.gattserver.pubs.model.dto.VisitDTO;
import cz.gattserver.pubs.util.MappingService;

@Transactional
@Component
public class VisitFacadeImpl implements VisitFacade {

	@Autowired
	private MappingService mapper;

	@Autowired
	private VisitRepository visitRepository;

	@Override
	public List<VisitDTO> getAllVisits() {
		return mapper.map(visitRepository.findAllOrderByDate(), VisitDTO.class);
	}

	@Override
	public Long saveVisit(VisitDTO v) {
		return visitRepository.save(mapper.map(v, Visit.class)).getId();
	}

}
