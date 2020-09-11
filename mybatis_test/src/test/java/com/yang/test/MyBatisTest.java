package com.yang.test;

import com.yang.dao.IEmpDao;
import com.yang.domain.Emp;
import com.yang.domain.QueryVo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyBatisTest {

    private InputStream is;
    private SqlSession sqlSession;
    private IEmpDao empDao;

    @Before
    public void init() throws Exception{
        is = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(is);
        sqlSession = factory.openSession();
        empDao = sqlSession.getMapper(IEmpDao.class);
    }

    @After
    public void destory() throws Exception{
        sqlSession.close();
        is.close();
    }

    @Test
    public void testFindAll(){
        List<Emp> empList = empDao.findAll();
        for (Emp emp : empList) {
            System.out.println(emp);
        }
    }

    @Test
    public void testAddEmp(){
        Emp emp = new Emp();
        emp.setId(10015);
        emp.setName("钢铁侠");
        emp.setJob_id(3);
        emp.setMgr_id(9);
        emp.setJoindate(new Date());
        emp.setSalary(50000.00);
        emp.setBonus(28000.00);
        emp.setDept_id(30);

        empDao.addEmp(emp);
        sqlSession.commit();
    }

    @Test
    public void testUpdateEmp(){
        Emp emp = new Emp();
        emp.setId(10015);
        emp.setName("钢铁侠");
        emp.setJob_id(3);
        emp.setMgr_id(1009);
        emp.setJoindate(new Date());
        emp.setSalary(50000.00);
        emp.setBonus(28000.00);
        emp.setDept_id(30);

        empDao.updateEmp(emp);
        sqlSession.commit();
    }

    @Test
    public void testDelEmp(){
        empDao.delEmp(10015);
        sqlSession.commit();
    }

    @Test
    public void testFindEmpById(){
        Emp emp = empDao.findEmpById(10013);
        System.out.println(emp);
        sqlSession.commit();
    }

    @Test
    public void testFindEmpByName(){
        List<Emp> empList = empDao.findEmpByName("%8%");
        for (Emp emp : empList) {
            System.out.println(emp);
        }
        sqlSession.commit();
    }

    @Test
    public void testFindTotal(){
        int total = empDao.findTotal();
        System.out.println(total);
        sqlSession.commit();
    }

    @Test
    public void testFindEmpByVo(){
        QueryVo queryVo = new QueryVo();
        Emp e = new Emp();
        e.setName("%白%");
        queryVo.setEmp(e);
        List<Emp> empList = empDao.findEmpByVo(queryVo);
        for (Emp emp : empList) {
            System.out.println(emp);
        }
        sqlSession.commit();
    }

    @Test
    public void testFindEmpByCondition(){
        Emp e = new Emp();
        e.setName("%8%");
        e.setBonus(12200.00);
        List<Emp> empList = empDao.findEmpByCondition(e);
        for (Emp emp : empList) {
            System.out.println(emp);
        }
        sqlSession.commit();
    }

    @Test
    public void testFindEmpByIds(){
        QueryVo vo = new QueryVo();
        List<Integer> ids = new ArrayList<>();
        ids.add(1001);
        ids.add(1002);
        ids.add(1005);
        ids.add(1006);
        ids.add(1009);
        vo.setIds(ids);
        List<Emp> empList = empDao.findEmpByIds(vo);
        for (Emp emp : empList) {
            System.out.println(emp);
        }
        sqlSession.commit();
    }
}
