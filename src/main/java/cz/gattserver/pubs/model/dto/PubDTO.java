package cz.gattserver.pubs.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PubDTO implements Serializable {

	private static final long serialVersionUID = 1370519912799856102L;

	/**
	 * DB identifikátor
	 */
	private Long id;

	/**
	 * Zadáno
	 */
	private Date creationDate;

	/**
	 * Popis
	 */
	private String description;

	/**
	 * Název
	 */
	private String name;

	/**
	 * Adresa
	 */
	private String address;

	/**
	 * Komentáře
	 */
	private List<CommentDTO> comments;

	/**
	 * Hodnocení
	 */
	private Integer rank;

	/**
	 * Poslední návštěva
	 */
	private Date lastVisit;

	/**
	 * WWW stránky
	 */
	private String webAddress;

	public String getWebAddress() {
		return webAddress;
	}

	public void setWebAddress(String webAddress) {
		this.webAddress = webAddress;
	}

	public Date getLastVisit() {
		return lastVisit;
	}

	public void setLastVisit(Date lastVisit) {
		this.lastVisit = lastVisit;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PubDTO) {
			PubDTO p2 = (PubDTO) obj;
			return id.equals(p2.getId());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
}
