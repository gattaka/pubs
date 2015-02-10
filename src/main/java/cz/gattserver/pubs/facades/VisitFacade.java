package cz.gattserver.pubs.facades;

import java.util.List;

import cz.gattserver.pubs.model.dto.VisitDTO;

public interface VisitFacade {

	public List<VisitDTO> getAllVisits();

	public Long saveVisit(VisitDTO bean);

}
