package com.study.java.fina;

public class SubClass extends ParentClass {
    //子类无法重写（override父类的final方法，否则编译时会报错
    /*@Override
    public void TestFinal(){
        System.out.println("子类--重写final方法");
    }*/

    /**
     * 这里需要特殊说明的是，具有private访问权限的方法也可以增加final修饰，
     * 但是由于子类无法继承private方法，因此也无法重写它。
     * 编译器在处理private方法时，是照final方来对待的，这样可以提高该方法被调用时的效率。
     * 不过子类仍然可以定义同父类中private方法具同样结构的方法，但是这并不会产生重写的效果，
     * 而且它们之间也不存在必然联系。
     */
    private final void testPrivateFinal() {
        System.out.println("子类--这是一个private final方法");
    }

    public static void main(String[] args) {
        FinalTest finalTest = new FinalTest(100);
        System.out.println(" E = " + finalTest.E);

        SubClass sc = new SubClass();
        sc.testPrivateFinal();
    }
}
