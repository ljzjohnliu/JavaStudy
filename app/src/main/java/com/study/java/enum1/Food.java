package com.study.java.enum1;

public interface Food {
    enum Coffee implements Food {
        BLACK_COFFEE, DECAF_COFFEE, LATTE, CAPPUCCINO
    }

    enum Dessert implements Food {
        FRUIT, CAKE, GELATO
    }
}

class TestFood {
    public static void main(String[] args) {
        testImplementsInterface();
    }

    /**
     * 测试继承接口的枚举的使用
     */
    private static void testImplementsInterface() {
        for (Food.Dessert dessertEnum : Food.Dessert.values()) {
            System.out.print(dessertEnum + "  ");
        }
        System.out.println();
        //我这地方这么写，是因为我在自己测试的时候，把这个coffee单独到一个文件去实现那个food接口，而不是在那个接口的内部。
//        for (Food.Coffee coffee : Food.Dessert.values()) {
//            System.out.print(coffee + "  ");
//        }
        System.out.println();
        //搞个实现接口，来组织枚举，简单讲，就是分类吧。如果大量使用枚举的话，这么干，在写代码的时候，就很方便调用啦。
        //还有就是个“多态”的功能吧，
        Food food = Food.Dessert.CAKE;
        System.out.println(food);
        food = Food.Coffee.BLACK_COFFEE;
        System.out.println(food);
    }
}
