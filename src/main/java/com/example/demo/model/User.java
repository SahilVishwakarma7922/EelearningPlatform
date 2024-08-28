package com.example.demo.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userid;
	private String name;
	private String email;
	private String phoneNumber;	
	private String password;
	private String about;
	private boolean active=true;
	private boolean emailVerified=false;
	
	
	@Column(updatable = false)
	private Date createdAt;
	private String profilePath;
	private String recentOtp;
	
	
	@ManyToMany(cascade = CascadeType.DETACH,fetch = FetchType.EAGER)
	@JoinTable(name="userwithroles")
	private List<Role> roles;
	
	
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		if (roles!=null) {
			List<SimpleGrantedAuthority> user = roles.stream().map(r->new SimpleGrantedAuthority(r.getName())).toList();
			return user;
		}
		
			return Collections.emptyList();
		
		
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}
	
}
