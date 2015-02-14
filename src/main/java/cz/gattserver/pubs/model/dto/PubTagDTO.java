package cz.gattserver.pubs.model.dto;

import java.util.List;

public class PubTagDTO implements Comparable<PubTagDTO> {

	/**
	 * Název tagu
	 */
	private String name;

	/**
	 * Obsahy tagu
	 */
	private List<PubDTO> pubs;

	/**
	 * Počet obsahů k tagu
	 */
	private int pubsCount;

	/**
	 * DB identifikátor
	 */
	private Long id;

	public PubTagDTO() {
	}

	public PubTagDTO(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PubDTO> getPubs() {
		return pubs;
	}

	public void setPubs(List<PubDTO> pubs) {
		this.pubs = pubs;
	}

	public int getPubsCount() {
		return pubsCount;
	}

	public void setPubsCount(int pubsCount) {
		this.pubsCount = pubsCount;
	}

	@Override
	public int compareTo(PubTagDTO o) {
		return getName().compareTo(o.getName());
	}

}
