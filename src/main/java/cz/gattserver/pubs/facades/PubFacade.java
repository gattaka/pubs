package cz.gattserver.pubs.facades;

import java.util.Collection;
import java.util.List;

import cz.gattserver.pubs.model.dto.PubDTO;
import cz.gattserver.pubs.model.dto.PubTagDTO;

public interface PubFacade {

	public List<PubDTO> getAllPubs();

	public Long savePub(PubDTO pubDTO, Collection<String> tags);

	public PubDTO getById(Long id);

	public PubDTO getByName(String pubName);

	public List<PubTagDTO> getPubTagsForOverview();

	public void updatePubRank(Long id, int intValue);

	public List<PubTagDTO> getAllPubTags();
}
