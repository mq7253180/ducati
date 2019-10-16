package com.hce.ducati.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.hce.ducati.entity.DictionaryItem;

@Repository
public interface DictionaryItemRepository extends JpaRepository<DictionaryItem, Long>, JpaSpecificationExecutor<DictionaryItem> {
	public DictionaryItem findByParentIdAndKey(Long parentId, Integer key);
	public List<DictionaryItem> findByParentId(Long parentId);
}
