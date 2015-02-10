package cz.gattserver.pubs.model.dto;

import java.io.Serializable;
import java.util.Date;

public class VisitDTO implements Serializable {

	private static final long serialVersionUID = -5014976320658298578L;

	/**
	 * DB identifikátor
	 */
	private Long id;

	/**
	 * Kdy
	 */
	private Date date;

	/**
	 * Autor
	 */
	private UserDTO author;

	/**
	 * Místo
	 */
	private PubDTO pub;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public UserDTO getAuthor() {
		return author;
	}

	public void setAuthor(UserDTO author) {
		this.author = author;
	}

	public PubDTO getPub() {
		return pub;
	}

	public void setPub(PubDTO pub) {
		this.pub = pub;
	}

}
