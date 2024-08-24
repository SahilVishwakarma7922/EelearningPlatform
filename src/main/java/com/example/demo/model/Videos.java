package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="videos")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Videos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long videoId;
	
	private String title;
	
	@Column(length = 1000)
	private String description;
	private String filepath;
	private String contentType;
	private String size;

	@ManyToOne
	private Courses courses;
	
}
