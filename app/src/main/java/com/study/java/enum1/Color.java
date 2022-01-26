package com.study.java.enum1;

interface IPlay {
    void printColor();
}

/**
 * 枚举可以实现接口
 */
public enum Color implements IPlay{
    //    RED, GREEN, BLUE
    RED("红色", 1), GREEN("绿色", 2), BLUE("蓝色", 3);
    // 成员变量
    private String name;
    private int index;

    // 构造方法
    Color(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(int index) {
        for (Color c : Color.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    //覆盖方法
    @Override
    public String toString() {
        return this.index + "_" + this.name;
    }

    @Override
    public void printColor() {
        System.out.println("要打印的颜色是：" + toString());
    }
}

class TestEnum {
    public static void main(String[] args) {
        Color color = Color.RED;
        color.printColor();
        System.out.println(testColor(color));
    }

    private static String testColor(Color color) {
        String colorStr;
        switch (color) {
            case RED:
                colorStr = color.toString();
                break;
            case GREEN:
                colorStr = "绿色";
                break;
            case BLUE:
                colorStr = "蓝色";
                break;
            default:
                colorStr = "未知色";
                break;
        }
        return colorStr;
    }
}
