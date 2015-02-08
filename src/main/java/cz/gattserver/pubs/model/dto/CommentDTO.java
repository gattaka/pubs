package cz.gattserver.pubs.model.dto;

import java.io.Serializable;
import java.util.Date;

public class CommentDTO implements Serializable {

	private static final long serialVersionUID = 1370519912799856102L;

	/**
	 * DB identifikátor
	 */
	private Long id;

	/**
	 * Kdy byl vytvořen
	 */
	private Date creationDate;

	/**
	 * Autor
	 */
	private UserDTO author;

	/**
	 * Obsah komentáře
	 */
	private String text;

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

	public UserDTO getAuthor() {
		return author;
	}

	public void setAuthor(UserDTO author) {
		this.author = author;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
