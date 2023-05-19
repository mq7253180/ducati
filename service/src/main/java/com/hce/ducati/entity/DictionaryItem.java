package com.hce.ducati.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@DynamicInsert
@DynamicUpdate
@EntityListeners({AuditingEntityListener.class})
@Entity(name = "b_dictionary_item")
public class DictionaryItem {
	@JsonIgnore
	@Id
	@Column(name="id")
	private Long id;
	@JsonIgnore
	@Column(name="p_id")
	private Long parentId;
	@Column(name="_key")
	private Integer key;
	@Column(name="value")
	private String value;
	@JsonIgnore
	@Column(name="sort")
	private Integer sort;
}
