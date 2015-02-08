package cz.gattserver.pubs.facades;

import java.util.List;

import cz.gattserver.pubs.model.dto.PubDTO;

public interface PubFacade {

	public List<PubDTO> findAllPubs();
	
	public void createPub(PubDTO pubDTO);
}