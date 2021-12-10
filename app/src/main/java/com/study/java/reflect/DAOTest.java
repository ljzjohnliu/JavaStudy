package com.study.java.reflect;

public class DAOTest {

    public static void main(String[] args) {
        try {
            testAnnotation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();  //插入断点
    }

    public static void testAnnotation() throws Exception {
        PersonDAO personDAO = new PersonDAO();
        Person entity = new Person();
        //调用父类的save方法，同时也把Person这个“实参”传给了父类的T
        personDAO.save(entity);
        //这句的本意是要返回一个Person类型的对象
        Person result = personDAO.get(0);
        System.out.print(result);
    }
}
