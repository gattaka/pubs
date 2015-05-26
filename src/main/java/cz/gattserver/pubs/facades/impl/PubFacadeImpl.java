package cz.gattserver.pubs.facades.impl;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cz.gattserver.pubs.facades.PubFacade;
import cz.gattserver.pubs.model.dao.PubRepository;
import cz.gattserver.pubs.model.dao.PubTagRepository;
import cz.gattserver.pubs.model.domain.Pub;
import cz.gattserver.pubs.model.domain.PubTag;
import cz.gattserver.pubs.model.dto.PubDTO;
import cz.gattserver.pubs.model.dto.PubTagDTO;
import cz.gattserver.pubs.util.MappingService;

@Transactional
@Component
public class PubFacadeImpl implements PubFacade {

	@Autowired
	private MappingService mapper;

	@Autowired
	private PubRepository pubRepository;

	@Autowired
	private PubTagRepository pubTagRepository;

	private static final String HTTP_PREFIX = "http://";

	public List<PubDTO> getAllBeerPubs() {
		return mapper.map(pubRepository.findAllBeerPubs(), PubDTO.class);
	}

	public List<PubDTO> getAllWinePubs() {
		return mapper.map(pubRepository.findAllWinePubs(), PubDTO.class);
	}

	public List<PubDTO> getAllCoffeePubs() {
		return mapper.map(pubRepository.findAllCoffeePubs(), PubDTO.class);
	}

	@Override
	public Long savePub(PubDTO pubDTO, Collection<String> tags) {
		Pub pub = mapper.map(pubDTO, Pub.class);
		if (pub.getRankCount() == null)
			pub.setRankCount(0);
		if (pub.getRankSum() == null)
			pub.setRankSum(0);
		if (pub.getWebAddress() != null && pub.getWebAddress().startsWith(HTTP_PREFIX) == false) {
			pub.setWebAddress(HTTP_PREFIX + pub.getWebAddress());
		}
		pub.setCreationDate(Calendar.getInstance().getTime());
		pub = pubRepository.save(pub);
		saveTags(tags, pub);
		return pub.getId();
	}

	@Override
	public PubDTO getById(Long id) {
		return mapper.map(pubRepository.findOne(id), PubDTO.class);
	}

	@Override
	public PubDTO getByName(String pubName) {
		return mapper.map(pubRepository.findByName(pubName), PubDTO.class);
	}

	@Override
	public List<PubTagDTO> getPubTagsForOverview() {
		List<PubTag> tags = pubTagRepository.findAll();
		for (PubTag tag : tags) {
			tag.setPubs(null);
		}
		return mapper.map(tags, PubTagDTO.class);
	}

	public void saveTags(Collection<String> tagsDTOs, Long pubId) {
		saveTags(tagsDTOs, pubRepository.findOne(pubId));
	}

	public void saveTags(Collection<String> tagsDTOs, Pub pub) {

		// tagy, které které jsou použity/vytvořeny
		Set<PubTag> tags = new HashSet<PubTag>();

		if (tagsDTOs != null)
			for (String tag : tagsDTOs) {

				// existuje už takový tag ?
				PubTag contentTag = pubTagRepository.findByName(tag);

				if (contentTag == null) {
					// ne ? - vytvoř
					contentTag = new PubTag();
					contentTag.setName(tag);
				}

				// přidej ho do seznamu
				tags.add(contentTag);

			}

		// Fáze #1
		// získej tagy, které se už nepoužívají a na nich proveď odebrání
		// ContentNode a případně smazání
		Set<PubTag> tagsToDelete = new HashSet<PubTag>();
		if (pub.getTags() != null) {
			for (PubTag oldTag : pub.getTags()) {
				if (tags.contains(oldTag))
					continue;

				if (oldTag.getPubs().remove(pub) == false) {
					// TODO ... pokud nebyl node v tagu, pak je někde chyba a
					// měl by se aspon vyhodit warning
				}

				// ulož změnu
				oldTag.setPubsCount(oldTag.getPubs().size());
				oldTag = pubTagRepository.save(oldTag);

				// pokud je tag prázdný (nemá nodes) pak se může smazat
				if (oldTag.getPubs().isEmpty()) {
					tagsToDelete.add(oldTag);
				}
			}
		}

		// Fáze #2
		// vymaž tagy z node
		// do všech tagů přidej odkaz na node
		// tagy ulož nebo na nich proveď merge
		// zároveň je rovnou přidej do node
		pub.setTags(new HashSet<PubTag>());
		for (PubTag tag : tags) {
			if (tag.getPubs() == null)
				tag.setPubs(new HashSet<Pub>());
			tag.getPubs().add(pub);

			tag.setPubsCount(tag.getPubs().size());
			tag = pubTagRepository.save(tag);

			// přidej tag k node
			pub.getTags().add(tag);

		}

		// merge contentNode
		pub = pubRepository.save(pub);

		// Fáze #3
		// smaž nepoužívané tagy
		for (PubTag tagToDelete : tagsToDelete) {
			pubTagRepository.delete(tagToDelete);
		}
	}

	/**
	 * Získej tag dle jeho id
	 * 
	 * @param id
	 * @return tag
	 */
	public PubTagDTO getContentTagById(Long id) {
		PubTagDTO tag = mapper.map(pubTagRepository.findOne(id), PubTagDTO.class);
		return tag;
	}

	public boolean countContentNodes() {
		for (PubTag tag : pubTagRepository.findAll()) {
			tag.setPubsCount(tag.getPubs().size());
			if (pubTagRepository.save(tag) == null)
				return false;
		}
		return true;
	}

	@Override
	public void updatePubRank(Long id, int intValue) {
		pubRepository.updatePubRank(id, intValue);
	}

	@Override
	public List<PubTagDTO> getAllPubTags() {
		return mapper.map(pubTagRepository.findAll(), PubTagDTO.class);
	}

}
