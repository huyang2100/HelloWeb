package com.yang.dao;

import com.yang.domain.Dept;

import java.util.List;

public interface IDeptDao {
    /**
     * 查询所有的部门
     * @return
     */
    List<Dept> findAll();
}
