package com.study.java.throwable;

public class Child extends Parent {
    @Override
    public void testNoException() {
        System.out.println("Child have no Exception!");
    }

    @Override
    public void testHasException() throws Exception {
        System.out.println("Child have Exception!");
        throw new Exception();
    }

    /**
     * try  catch  finally 中有return 时，会先执行return ，但是不会返回。在执行完 finally 后 进行返回。
     * return 的是基本类型数据时， finally 里面的语句不会影响 return 的值，
     * return 的是引用类型数据时，此时已经确定了要返回对象的地址（地址一），后面 finally 里面的可以通过修改
     * 前面地址一中的内容修改返回的内容，
     * 但是如果将对象指向另一个地址（地址二），则不会影响返回的内容。
     * 因为返回的对象地址已经确定为地址一，只能通过修改地址一对象的内容修改返回的信息。
     */
    public int test() {
        int i = 1;
        try {
            testHasException();
            return i;
        } catch (Exception exception) {
            exception.printStackTrace();
            i++;
            return i;
        } finally {
            i = -1;
//            return i;
        }
    }
}
