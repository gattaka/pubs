package cz.gattserver.pubs.facades;

import java.util.Collection;
import java.util.List;

import cz.gattserver.pubs.model.dto.PubDTO;
import cz.gattserver.pubs.model.dto.PubTagDTO;

public interface PubFacade {

	public PubDTO getById(Long id);

	public PubDTO getByName(String pubName);

	public List<PubTagDTO> getPubTagsForOverview();

	public void updatePubRank(Long id, int intValue);

	public List<PubTagDTO> getBeerPubTags();

	public List<PubTagDTO> getWinePubTags();

	public List<PubTagDTO> getCoffeePubTags();

	public List<PubDTO> getAllBeerPubs();

	public List<PubDTO> getAllWinePubs();

	public List<PubDTO> getAllCoffeePubs();

	Long saveBeerPub(PubDTO pubDTO, Collection<String> tags);

	Long saveWinePub(PubDTO pubDTO, Collection<String> tags);

	Long saveCoffeePub(PubDTO pubDTO, Collection<String> tags);
}
