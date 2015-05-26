package cz.gattserver.pubs.model.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
	private Integer rankSum;

	/**
	 * Hodnocení
	 */
	private Integer rankCount;

	/**
	 * Poslední návštěva
	 */
	private Date lastVisit;

	/**
	 * WWW stránky
	 */
	private String webAddress;

	private byte[] image;

	/**
	 * Tagy
	 */
	private Set<PubTagDTO> tags;

	/**
	 * Má se zobrazovat jako hospoda?
	 */
	private Boolean beer;

	/**
	 * Má se zobrazovat jako vinárna?
	 */
	private Boolean wine;

	/**
	 * Má se zobrazovat jako kavárna?
	 */
	private Boolean coffee;

	public Boolean isBeer() {
		return beer;
	}

	public void setBeer(Boolean beer) {
		this.beer = beer;
	}

	public Boolean isWine() {
		return wine;
	}

	public void setWine(Boolean wine) {
		this.wine = wine;
	}

	public Boolean isCoffee() {
		return coffee;
	}

	public void setCoffee(Boolean coffee) {
		this.coffee = coffee;
	}

	public Set<PubTagDTO> getTags() {
		return tags;
	}

	public void setTags(Set<PubTagDTO> tags) {
		this.tags = tags;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

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

	public Integer getRankSum() {
		return rankSum;
	}

	public void setRankSum(Integer rankSum) {
		this.rankSum = rankSum;
	}

	public Integer getRankCount() {
		return rankCount;
	}

	public void setRankCount(Integer rankCount) {
		this.rankCount = rankCount;
	}

	public int getRank() {
		if (rankSum == null || rankCount == null || rankCount == 0)
			return 0;
		else
			return rankSum / rankCount;
	}

}
