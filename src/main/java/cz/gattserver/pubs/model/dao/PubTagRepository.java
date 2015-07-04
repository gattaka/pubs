package cz.gattserver.pubs.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cz.gattserver.pubs.model.domain.PubTag;

public interface PubTagRepository extends JpaRepository<PubTag, Long> {

	public PubTag findByName(String name);

	@Query("select p from PubTag p where exists (select pp from Pub pp where p member of pp.tags and pp.beer = 'true')")
	List<PubTag> findByBeerPubs();

	@Query("select p from PubTag p where exists (select pp from Pub pp where p member of pp.tags and pp.wine = 'true')")
	List<PubTag> findByWinePubs();

	@Query("select p from PubTag p where exists (select pp from Pub pp where p member of pp.tags and pp.coffee = 'true')")
	List<PubTag> findByCoffeePubs();

}
