package cz.gattserver.pubs.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cz.gattserver.pubs.model.domain.Pub;

public interface PubRepository extends JpaRepository<Pub, Long> {

	Pub findByName(String pubName);

}
