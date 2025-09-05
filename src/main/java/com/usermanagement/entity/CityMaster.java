package com.usermanagement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CityMaster {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "state_id")
	private StateMaster state;

}
