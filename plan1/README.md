# 学习计划(2021-08-30~2021-09-12)
该学习计划分三部分：
1、Java基础知识点
2、Android基础知识点
3、算法部分（围绕java知识点展开）

## 1、Java知识点：
(1)[深入理解Java数组](https://dunwu.github.io/javacore/basics/java-array.html#_1-%E7%AE%80%E4%BB%8B)

(2)[谈谈对 java 多态的理解?](https://blog.csdn.net/zhangqiluGrubby/article/details/109487009)

(3)[Java实现单例模式](https://blog.csdn.net/u011595939/article/details/79972371)

(4)[Java内部类详解](https://www.cnblogs.com/dolphin0520/p/3811445.html)

   内部类的使用场景和好处，为什么在Java中需要内部类？  
   总结一下主要有以下四点：

　　1.每个内部类都能独立的继承一个接口的实现，所以无论外部类是否已经继承了某个(接口的)实现，对于内部类都没有影响。内部类使得多继承的解决方案变得完整;

　　2.方便将存在一定逻辑关系的类组织在一起，又可以对外界隐藏;

　　3.方便编写事件驱动程序;

　　4.方便编写线程代码.

## 2、Android知识点
(1)[Android事件分发详解](https://www.jianshu.com/p/38015afcdb58)

(2)[Android 异步通信：图文详解Handler机制工作原理](https://blog.csdn.net/carson_ho/article/details/80175876)

(3)[Android ANR 分析](https://www.jianshu.com/p/108299cecd90)

[主线程中Thread.Sleep()是否会导致ANR？](https://blog.csdn.net/zhangqunshuai/article/details/82455503)

在android里面对导致ANR的耗时时常进行了常量定义
Android N 的 ANR时间

Service 超时

// How long we wait for a service to finish executing.

static final int SERVICE_TIMEOUT = 20*1000; // 前台

// How long we wait for a service to finish executing.

static final int SERVICE_BACKGROUND_TIMEOUT = SERVICE_TIMEOUT * 10; // 后台

Broadcast 超时

// How long we allow a receiver to run before giving up on it.

static final int BROADCAST_FG_TIMEOUT = 10*1000;  // 前台

static final int BROADCAST_BG_TIMEOUT = 60*1000;  // 后台

InputDispatching 超时

 // How long we wait until we timeout on key dispatching.

 static final int KEY_DISPATCHING_TIMEOUT = 5*1000;

ontentProvider 超时

// How long we wait for an attached process to publish its content providers

// before we decide it must be hung.

static final int CONTENT_PROVIDER_PUBLISH_TIMEOUT = 10*1000;


(4)[Android中的几种线程间通信方式](https://blog.csdn.net/small_and_smallworld/article/details/72791384)

(5)[Android 跨进程通信大总结](https://blog.csdn.net/zhaoyanjun6/article/details/111553746)

其实Android的四大组件都是支持跨进程通信的

## 3、算法部分
(1)[如何在一个 1 到 100 的整数数组中找到丢失的数字?](https://blog.csdn.net/feilang00/article/details/95312062)

(2)[无重复字符的最长子串](https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/)



