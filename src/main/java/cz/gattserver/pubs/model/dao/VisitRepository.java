package cz.gattserver.pubs.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cz.gattserver.pubs.model.domain.Visit;

public interface VisitRepository extends JpaRepository<Visit, Long> {

	@Query("SELECT v FROM Visit v ORDER BY date DESC") 
	public List<Visit> findAllOrderByDate();

}
