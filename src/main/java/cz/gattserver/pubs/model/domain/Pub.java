package cz.gattserver.pubs.model.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "PUBS", uniqueConstraints = { @UniqueConstraint(columnNames = { "name" }) })
public class Pub implements Serializable {

	private static final long serialVersionUID = 1370519912799856102L;

	/**
	 * DB identifikátor
	 */
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	/**
	 * Zadáno
	 */
	private Date creationDate;

	/**
	 * Poslední návštěva
	 */
	private Date lastVisit;

	/**
	 * Popis
	 */
	@Column(columnDefinition = "TEXT")
	private String description;

	/**
	 * Název
	 */
	private String name;

	/**
	 * WWW stránky
	 */
	private String webAddress;

	/**
	 * Adresa
	 */
	private String address;

	/**
	 * Komentáře
	 */
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Comment> comments;

	/**
	 * Hodnocení
	 */
	private Integer rank;

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

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

}
