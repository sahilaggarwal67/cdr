package com.transform.cdr.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.transform.cdr.model.Type3Parent;

public interface Type3ParentDao extends CrudRepository<Type3Parent, Integer> {
	public List<Type3Parent> findByParentId(int parentId);
}
