package com.usermanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class StateMaster {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "country_id")
	private CountryMaster country;

}
