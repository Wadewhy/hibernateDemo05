package xyz.wadewhy.test;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import xyz.wadewhy.pojo.Course;
import xyz.wadewhy.pojo.Dept;
import xyz.wadewhy.pojo.Emp;
import xyz.wadewhy.pojo.Student;
import xyz.wadewhy.util.HibernateUtil;

public class HibernateTest {

    // 添加数据
    @Test
    public void fun1() {
        Session session = HibernateUtil.openSession();
        session.beginTransaction();
        // 创建两个学生
        Student s1 = new Student();
        s1.setSname("张s三");
        Student s2 = new Student();
        s2.setSname("s李四");
        // 创建两门科目
        Course c1 = new Course();
        c1.setCname("Java基础入门s");
        Course c2 = new Course();
        c2.setCname("MySQL基础入门s");
        // 学生关联科目
        s1.getCourses().add(c1);
        s2.getCourses().add(c1);
        s1.getCourses().add(c2);
        s2.getCourses().add(c2);
        // 存储
        // 课堂关联学生
        c1.getStudents().add(s1);
        c1.getStudents().add(s2);
        c2.getStudents().add(s1);
        c2.getStudents().add(s2);
        session.save(c1);
        session.save(c2);
        session.save(s1);
        session.save(s2);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * 测试级联添加
     */
    @Test
    public void fun2() {
        Session session = HibernateUtil.openSession();
        Transaction tx = session.beginTransaction();
        // 创建一个部门
        Dept dept = new Dept();
        dept.setDname("百度");
        // 创建一个员工
        Emp emp = new Emp();
        emp.setEname("李白");
        // 表明属于那个部门
        dept.getEmpset().add(emp);
        // 部门关联学生
        session.save(dept);
        tx.commit();
        session.close();

    }

    /**
     * 测试级联删除
     */
    @Test
    public void fun3() {
        Session session = HibernateUtil.openSession();
        Transaction tx = session.beginTransaction();
        // 查询deptid=47的部门，也就是刚添加
        Dept dept = session.get(Dept.class, 47);
        // 删除此部门
        session.delete(dept);
        tx.commit();
        session.close();

    }

    /**
     * 测试级联孤儿删除
     */
    @Test
    public void fun4() {
        Session session = HibernateUtil.openSession();
        Transaction tx = session.beginTransaction();
        // 查询deptid=41的部门
        Dept dept = session.get(Dept.class, 41);
        // 查询empid为7938，
        Emp emp = session.get(Emp.class, 7936);
        // 解除关系,删除记录
        dept.getEmpset().remove(emp);
        tx.commit();
        session.close();

    }

    /**
     * 多对多
     */
    @Test
    public void fun5() {
        Session session = HibernateUtil.openSession();
        Transaction tx = session.beginTransaction();
        // 创建课程对象
        Course c = new Course();
        c.setCname("JavaWeb程序开发");
        // 创建学生对象
        Student s = new Student();
        s.setSname("王五");
        // 学生关联课程
        s.getCourses().add(c);
        // 存储
        session.save(s);
        tx.commit();
        session.close();

    }
}
