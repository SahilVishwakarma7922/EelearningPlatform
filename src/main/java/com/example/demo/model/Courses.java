package com.example.demo.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Courses {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String title;
	
	private String shortDesc;
	
	@Column(length = 2000)
	private String longDesc;
	
	private double price;
	private boolean live=false;
	private double discount;
	
	@Column(updatable = false)
	private Date createdDate;
	private String bannerName;
	
	@OneToMany(mappedBy = "courses")
	private List<Videos> videos;

	@ManyToMany
	private List<Categories> categoryList;
	
	private String contenttype;
	
}
