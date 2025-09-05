package com.usermanagement.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class UserMaster {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	private String name;
	private String email;
	private String pwd;
	private String updatedPwd;
	private int phNo;
	
	@ManyToOne
	@JoinColumn(name = "country_id")
	private CountryMaster country;
	
	@ManyToOne
	@JoinColumn(name = "state_id")
	private StateMaster state;
	
	@ManyToOne
	@JoinColumn(name = "city_id")
	private CityMaster city;
	
	private boolean status;

	@CreationTimestamp
	private LocalDateTime created_at;
	@UpdateTimestamp
	private LocalDateTime updated_at;


}
