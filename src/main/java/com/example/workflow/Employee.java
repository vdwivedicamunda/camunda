package com.example.workflow;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "employee")
public class Employee {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "id")
	Long id;
	@Column (name = "first_Name")
	String firstName;
	@Column (name = "last_Name")
	String lastName;
	@Column (name = "ohr_Id")
	Long ohrId;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Long getOhrId() {
		return ohrId;
	}
	public void setOhrId(Long ohrId) {
		this.ohrId = ohrId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	

}
