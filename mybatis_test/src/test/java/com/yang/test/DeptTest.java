package com.yang.test;

import com.yang.dao.IDeptDao;
import com.yang.dao.IEmpDao;
import com.yang.domain.Dept;
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

public class DeptTest {

    private InputStream is;
    private SqlSession sqlSession;
    private IDeptDao deptDao;

    @Before
    public void init() throws Exception{
        is = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(is);
        sqlSession = factory.openSession();
        deptDao = sqlSession.getMapper(IDeptDao.class);
    }

    @After
    public void destory() throws Exception{
        sqlSession.close();
        is.close();
    }

    @Test
    public void testFindAll(){
        List<Dept> empList = deptDao.findAll();
        for (Dept dept : empList) {
            System.out.println(dept);
        }
    }

}
