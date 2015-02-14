package cz.gattserver.pubs.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cz.gattserver.pubs.model.domain.Pub;

public interface PubRepository extends JpaRepository<Pub, Long> {

	Pub findByName(String pubName);

	@Modifying
	@Query("update Pub p set p.rankSum = p.rankSum + ?2, p.rankCount = p.rankCount + 1 where p.id = ?1")
	void updatePubRank(Long id, int intValue);

}
