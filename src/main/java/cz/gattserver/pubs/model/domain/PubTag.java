package cz.gattserver.pubs.model.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "PUB_TAG")
public class PubTag {

	/**
	 * Název tagu
	 */
	private String name;

	/**
	 * Obsahy tagu
	 */
	@ManyToMany(mappedBy = "tags")
	private Set<Pub> pubs;

	/**
	 * Počet pubů přiřazených k tomuto tagu (aplikace je pak mnohem rychlejší když se to nemusí počítat)
	 */
	private Integer pubsCount;

	/**
	 * DB identifikátor
	 */
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PubTag))
			return false;
		return ((PubTag) obj).getName().equals(getName());
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
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

	public Set<Pub> getPubs() {
		return pubs;
	}

	public void setPubs(Set<Pub> pubs) {
		this.pubs = pubs;
	}

	public Integer getPubsCount() {
		return pubsCount;
	}

	public void setPubsCount(Integer pubsCount) {
		this.pubsCount = pubsCount;
	}

}
