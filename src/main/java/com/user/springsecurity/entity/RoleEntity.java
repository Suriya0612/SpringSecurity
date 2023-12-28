package com.user.springsecurity.entity;

import jakarta.persistence.*;
@Entity
@Table(name="Roles_table")
public class RoleEntity {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;

	 @Column(nullable=false, unique=true)
	 private String name;
	 
	 public RoleEntity() {
		 
	 }
	public RoleEntity(Long id, String name) {
		super();
		this.id = id;
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
	 
	 
}
