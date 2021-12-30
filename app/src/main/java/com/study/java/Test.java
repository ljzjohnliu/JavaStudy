package com.study.java;

import com.study.java.base.Shape;
import com.study.java.sub.Circle;
import com.study.java.sub.Square;

public class Test {

    public static void main(String[] args) {
        /**
         * “向上转型”
         * 这里定义了一个Shap 类型的square，它指向Square对象实例。
         * 由于Square是继承与Shap，所以Square可以自动向上转型为Shap，所以square是可以指向Square实例对象的。
         * 这样做存在一个非常大的好处，在继承中我们知道子类是父类的扩展，它可以提供比父类更加强大的功能，如果我们定义了一个指向子类的父类引用类型，
         * 那么它除了能够引用父类的共性外，还可以使用子类强大的功能。
         * 但是向上转型存在一些缺憾，那就是它必定会导致一些方法和属性的丢失，而导致我们不能够获取它们。
         * 所以父类类型的引用可以调用父类中定义的所有属性和方法，对于只存在与子类中的方法和属性它就望尘莫及了---1。(使用强制类型转换向下转型依然可以访问到的)
         */
        Square square = new Square();
        Shape circle = new Circle();

        /**
         * Square重写draw()，并且重载方法draw(int length)与 draw()不是同一个方法，由于父类中没有该方法，向上转型后会丢失该方法，
         * 所以执行Square的Shap类型引用是不能引用draw(int length)方法。而子类Square重写了draw() ，那么指向Square的Shap引用会调用Square中draw()方法。
         * 可以总结：
         * 指向子类的父类引用由于向上转型了，它只能访问父类中拥有的方法和属性，而对于子类中存在而父类中不存在的方法，该引用是不能使用的，
         * 尽管是重载该方法。若子类重写了父类中的某些方法，在调用该些方法的时候，必定是使用子类中定义的这些方法（动态连接、动态调用）。
         * 记住：调用方法是根据这个对象本质确定的！而不是这个引用类型决定的！
         */
        square.print();
//        square.draw(2);//这里会报错，因为父类没有该方法
//        System.out.println("getObj : " + square.getObj());
//
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        Date date = null;
//        try {
//            date = format.parse("2000-01-01 00:00:00");
//        } catch (ParseException e) {
//            System.out.println(e.getMessage());
//        }
//        Calendar calender = Calendar.getInstance();
//        calender.setTime(date);
//        System.out.println(format.format(calender.getTime()));
//        System.out.println(calender.get(Calendar.YEAR));
//        System.out.println(calender.get(Calendar.MONTH));

        String s = new String("aaa");
        String ns = Test.appendStr(s);
        System.out.println("String aaa>>>" + s.toString());
        // StringBuilder做参数
        StringBuilder sb = new StringBuilder("aaa");
        StringBuilder nsb = Test.appendSb(sb);
        System.out.println("StringBuilder aaa >>>" + sb.toString());
    }

    // 不可变的String
    public static String appendStr(String s) {
        s += "bbb";
        return s;
    }
    // 可变的StringBuilder
    public static StringBuilder appendSb(StringBuilder sb) {
        return sb.append("bbb");
    }
}
