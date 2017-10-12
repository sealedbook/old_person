package com.esite.framework.dictionary.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="PUB_DICTIONARY")
@JsonIgnoreProperties(value={"subDictionary","parent"})
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class DictionaryEntity implements java.io.Serializable {

	private static final long serialVersionUID = -6874497918884390909L;

	@Id
	@Column(name="ID")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	@Column(name="DIC_CODE")
	private String dicCode;
	
	@Column(name="DIC_NAME")
	private String dicName;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="PARENT_ID")
	private DictionaryEntity parent;
	
	@Column(name="DIC_ORDER")
	private int dicOrder;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="parent")
	@OrderBy(value="dicOrder")
	private List<DictionaryEntity> subDictionary;
	
	public DictionaryEntity getParent() {
		return parent;
	}

	public void setParent(DictionaryEntity parent) {
		this.parent = parent;
	}

	public List<DictionaryEntity> getSubDictionary() {
		return subDictionary;
	}

	public void setSubDictionary(List<DictionaryEntity> subDictionary) {
		this.subDictionary = subDictionary;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDicCode() {
		return dicCode;
	}

	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}

	public String getDicName() {
		return dicName;
	}

	public void setDicName(String dicName) {
		this.dicName = dicName;
	}

	public int getDicOrder() {
		return dicOrder;
	}

	public void setDicOrder(int dicOrder) {
		this.dicOrder = dicOrder;
	}
	
}
