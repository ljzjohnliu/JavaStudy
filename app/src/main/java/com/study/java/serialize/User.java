package com.study.java.serialize;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * 从代码可以看出只需要ObjectOutputStream和ObjectInputStream就可以实现对象的序列化和反序列化操作，通过流对象把user对象写到文件中，
 * 并在需要时恢复userBack对象，但是两者并不是同一个对象了，反序列化后的对象是新创建的。
 * 这里有两点特别注意的是如果反序列类的成员变量的类型或者类名，发生了变化，那么即使serialVersionUID相同也无法正常反序列化成功。
 * 其次是静态成员变量属于类不属于对象，不会参与序列化过程，使用transient关键字标记的成员变量也不参与序列化过程。
 */
public class User implements Serializable {

    /**
     *当实现java.io.Serializable接口的类没有显式地定义一个serialVersionUID变量时候，
     * Java序列化机制会根据编译的Class自动生成一个serialVersionUID作序列化版本比较用，
     * 这种情况下，如果Class文件(类名，方法明等)没有发生变化(增加空格，换行，增加注释等等)，就算再编译多次，
     * serialVersionUID也不会变化的。
     * 但是修改了文件内容的话，serialVersionUID会发生变化！！！这也是为什么强烈建议手动填写该值的原因
     */
    private static final long serialVersionUID = -2083503801443301445L;

    private int id;

//    private int age;
//
//    public void print() {
//
//    }

    private transient String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 序列化时,
     * 首先系统会先调用writeReplace方法,在这个阶段,
     * 可以进行自己操作,将需要进行序列化的对象换成我们指定的对象.
     * 一般很少重写该方法
     */
    private Object writeReplace() throws ObjectStreamException {
        System.out.println("writeReplace invoked");
        return this;
    }

    /**
     * 接着系统将调用writeObject方法,
     * 来将对象中的属性一个个进行序列化,
     * 我们可以在这个方法中控制住哪些属性需要序列化.
     * 这里只序列化name属性
     */
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        System.out.println("writeObject invoked");
        out.writeObject(this.id == 0 ? "-100" : this.id);
        out.writeObject(this.name == null ? "默认值" : this.name);
    }

    /**
     * 反序列化时,系统会调用readObject方法,将我们刚刚在writeObject方法序列化好的属性,
     * 反序列化回来.然后通过readResolve方法,我们也可以指定系统返回给我们特定的对象
     * 可以不是writeReplace序列化时的对象,可以指定其他对象.
     */
    private void readObject(java.io.ObjectInputStream in) throws IOException,
            ClassNotFoundException {
        System.out.println("readObject invoked");
        this.id = (int)in.readObject();
        System.out.println("got id:" + id);
        this.name = (String) in.readObject();
        System.out.println("got name:" + name);
    }

    /**
     * 通过readResolve方法,我们也可以指定系统返回给我们特定的对象
     * 可以不是writeReplace序列化时的对象,可以指定其他对象.
     * 一般很少重写该方法
     */
    private Object readResolve() throws ObjectStreamException {
        System.out.println("readResolve invoked");
        return this;
    }
}
