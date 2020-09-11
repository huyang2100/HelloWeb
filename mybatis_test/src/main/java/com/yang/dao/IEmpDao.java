package com.yang.dao;

import com.yang.domain.Emp;
import com.yang.domain.QueryVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 雇员的持久层接口
 */
public interface IEmpDao {
    /**
     * 查询所有雇员
     * @return
     */
    List<Emp> findAll();

    /**
     * 添加一个雇员
     * @param emp
     */
    void addEmp(Emp emp);

    /**
     * 更新一个雇员
     * @param emp
     */
    void updateEmp(Emp emp);

    /**
     * 根据雇员id删除雇员
     * @param empId
     */
    void delEmp(Integer empId);

    /**
     * 根据雇员id查找雇员
     * @param empId
     * @return
     */
    Emp findEmpById(Integer empId);

    /**
     * 根据雇员姓名模糊查询雇员
     * @param name
     * @return
     */
    List<Emp> findEmpByName(String name);

    /**
     * 查询雇员的总记录数
     * @return
     */
    int findTotal();

    /**
     * 根据查询条件对象模糊查询雇员
     * @param vo
     * @return
     */
    List<Emp> findEmpByVo(QueryVo vo);

    /**
     * 根据雇员的条件查询雇员
     * @param e 可能是姓名、也可能是工种、也可能是雇佣日期、也可能全都是、也可能全都不是
     * @return
     */
    List<Emp> findEmpByCondition(Emp e);

    /**
     * 根据查询条件的雇员id集合，查询雇员
     * @param vo
     * @return
     */
    List<Emp> findEmpByIds(QueryVo vo);
}
