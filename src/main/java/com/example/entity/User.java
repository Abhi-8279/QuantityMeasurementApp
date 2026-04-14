package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;

    private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email2) {
		// TODO Auto-generated method stub
		this.email=email2;
	}
	public String getEmail() {
		// TODO Auto-generated method stub
		return email;
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}


	
}