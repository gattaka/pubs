package cz.gattserver.pubs.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.gattserver.pubs.model.domain.PubTag;

public interface PubTagRepository extends JpaRepository<PubTag, Long> {

	public PubTag findByName(String name);

}
