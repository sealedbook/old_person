package com.esite.framework.user.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
//import org.eclipse.persistence.annotations.Customizer;
import org.hibernate.annotations.GenericGenerator;

import com.esite.framework.organize.entity.OrganizeViewEntity;
import com.esite.framework.security.entity.Role;
import com.esite.framework.util.IdentityCardHelper;
import com.esite.framework.util.StringHelper;

@Entity
@Table(name="SYS_SECURITY_USER")
@JsonIgnoreProperties(value={"roleCollection","password"})
public class User {
	@Id
	@Column(name="ID")
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	@Column(name="ACCOUNT")
	private String account;
	
	@Column(name="SHOW_NAME")
	private String showName;
	
	@Column(name="SEX")
	private int sex;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="FLAG")
	private String flag;
	
	@Column(name="ID_CARD")
	private String idCard;
	
	//@ManyToOne
	//@JoinColumn(name="ORG_ID")
	//private OrganizeEntity organize;
	
	public String getIdCard() {
		return idCard;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public void setIdCard(String idCard) {
		idCard = IdentityCardHelper.get18idCard(idCard);
		this.idCard = idCard;
	}
	@Transient
	private List<Role> roleCollection;
	public List<Role> getRoleCollection() {
		return roleCollection;
	}
	public void setRoleCollection(List<Role> roleCollection) {
		this.roleCollection = roleCollection;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
