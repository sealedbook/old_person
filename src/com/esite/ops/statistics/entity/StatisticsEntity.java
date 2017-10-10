package com.esite.ops.statistics.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class StatisticsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(generator="system-uuid")
	//@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
}
