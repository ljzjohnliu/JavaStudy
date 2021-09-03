package com.study.java.leecode;

/**
 * Java 数组的本质是对象。它具有 Java 中其他对象的一些基本特点：封装了一些数据，可以访问属性，也可以调用方法。所以，数组是对象。
 * Java 数组在内存中的存储是这样的：
 * 数组对象（这里可以看成一个指针）存储在栈中。
 * 数组元素存储在堆中。
 * 只有当 JVM 执行 new String[] 时，才会在堆中开辟相应的内存区域。数组对象 array 可以视为一个指针，指向这块内存的存储地址。
 */
public class StudyArray {

    static class User {
        public String name;
        private int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String toString() {
            return "User name is " + name + ", age is " + age;
        }
    }

    public static void main(String[] args) {
//        initArray();
//        testLength();
        /**
         * 帮助理解数组就是对象，作为参数传递给函数的时候，是引用传递，能修改对象内容
         */
        String[] arrayStr = new String[]{"aaa", "bbb", "ccc"};
        System.out.println("main arrayStr.length : " + arrayStr.length);
        for (String str : arrayStr) {
            System.out.println("main arrayStr str : " + str);
        }
        optStrArray2(arrayStr);
        for (String str : arrayStr) {
            System.out.println("main arrayStr opt str : " + str);
        }

        /**
         * 在Java中，当基本类型作为参数传入方法时，无论该参数在方法内怎样被改变，外部的变量原型总是不变的，因为方法内部有外部变量的一份拷贝，
         * 对这个拷贝的更改不会改变外部变量的值。
         * 这就叫做“值传递”，即方法操作的是参数变量（也就是原型变量的一个值的拷贝）改变的也只是原型变量的一个拷贝而已，而非变量本身。所以变量原型并不会随之改变。
         * 但当方法传入的参数为非基本类型时（也就是说是一个对象类型的变量）， 方法里面改变参数变量的同时变量原型也会随之改变，
         */
        String str = "test";
        System.out.println("main before str : " + str);
        optStr(str);
        System.out.println("main after str : " + str);

        User userA = new User("xxx", 30);
        System.out.println("main before userA : " + userA.toString());
        optUser2(userA);
        System.out.println("main optUser2 userA : " + userA.toString());
        optUser(userA);
        System.out.println("main optUser userA : " + userA.toString());
    }

    /**
     * Java 中，数组类型是一种引用类型。
     * 因此，它可以作为引用，被 Java 函数作为函数入参或返回值。
     * @param array
     */
    public static void optStrArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = array[i] + ":opt";
        }
    }

    /**
     * 只是把引用重新赋值了，但是不会影响外部的变量arrayStr
     */
    public static void optStrArray2(String[] array) {
        array = new String[]{"xxx", "optStrArray2"};
    }

    /**
     * “可是String类型在Java语言中属于非基本类型啊！它在方法中的改变为什么没有被保 存下来呢！”的确，这是个问题!!!
     * 这个问题真正原因是因为String类的存储是通过final修饰的char[]数组来存放结果的。不可更改。
     * 所以每次当外部一个String类型的引用传递到方法内部时候，只是把外部String类型变量的引用传递给了方法参数变量。对的。
     * 外部String变量和方法参数变量都是实际char[]数组的引用而已。所以当我们在方法内部改变这个参数的引用时候，因为char[]数组不可改变，
     * 所以每次新建变量都是新建一个新的String实例。很显然外部String类型变量没有指向新的String实例。所以也就不会获取到新的更改。
     */
    public static void optStr(String s) {
        s = s + ":after";
    }

    /**
     * 因为是引用传递，所以该方式能修改实际对象的内容
     */
    public static void optUser(User user) {
        user.name = "ljz";
        user.age = 18;
    }

    /**
     * 只是修改了改引用的值，这个不会影响到外部的userA对象的
     */
    public static void optUser2(User user) {
        user = null;
    }

    /**
     * Java 语言使用 new 操作符来创建数组。有两种创建数组方式：
     */
    public static void initArray() {
        /**
         * 方式1、指定数组维度
         * 为数组开辟指定大小的数组维度。
         * 如果数组元素是基础数据类型，会将每个元素设为默认值；如果是引用类型，元素值为 null。
         */
        int[] arrayInt = new int[5];
        System.out.println("main arrayInt.length : " + arrayInt.length);
        for (int i : arrayInt) {
            //说明 请注意数组 array1 中的元素虽然没有初始化，但是 length 和指定的数组维度是一样的。
            // 这表明指定数组维度后，无论后面是否初始化数组中的元素，数组都已经开辟了相应的内存。
            //数组中的元素都被设为默认值。
            System.out.println("main arrayInt i : " + i);
        }

        /**
         * 方式2、不指定数组维度
         * 用花括号中的实际元素初始化数组，数组大小与元素数相同。
         */
        String[] arrayStr = new String[]{"aaa", "bbb", "ccc"};
        System.out.println("main arrayStr.length : " + arrayStr.length);
        for (String str : arrayStr) {
            System.out.println("main arrayStr str : " + str);
        }
    }

    /**
     * 创建数组时，指定的数组维度可以有多种形式：
     *
     * 数组维度可以是整数、字符。
     * 数组维度可以是整数型、字符型变量。
     * 数组维度可以是计算结果为整数或字符的表达式。
     * 数组维度并非没有上限的，如果数值过大，编译时会报错。数组过大，可能会导致栈溢出。
     */
    public static void testLength() {
        int length = 3;
        // 放开被注掉的代码，编译器会报错
        // int[] array = new int[4.0];
        // int[] array2 = new int["test"];
        int[] array3 = new int['a'];
        int[] array4 = new int[length];
        int[] array5 = new int[length + 2];
        int[] array6 = new int['a' + 2];
        // int[] array7 = new int[length + 2.1];
        System.out.println("array3.length = [" + array3.length + "]");
        System.out.println("array4.length = [" + array4.length + "]");
        System.out.println("array5.length = [" + array5.length + "]");
        System.out.println("array6.length = [" + array6.length + "]");
    }
}
