# READEME

想要在studio中直接调试java程序，需要修改.idea/gradle.xml
添加<option name="delegatedBuild" value="false" />

<GradleProjectSettings>
        <option name="delegatedBuild" value="false" />
        <option name="testRunner" value="PLATFORM" />
        ...
      </GradleProjectSettings>


View事件分发机制
一、view的onTouchEvent，OnClickListener和OnTouchListener的onTouch方法 三者优先级
优先级高低：
onTouchListener >>> onTouchEvent >>> setOnLongClickListener >>> OnClickListener
二、onTouch 和onTouchEvent的区别
0、onTouchEvent是 手机屏幕事件的处理方法，是获取的对屏幕的各种操作，比如向左向右滑动，点击返回按钮等等。属于一个宏观的屏幕触摸监控。
onTouchEvent方法是override 的Activity或者View的方法。当屏幕有touch事件时，此方法就会别调用。
onTouch()是OnTouchListener接口的方法，它是获取某一个控件的触摸事件，因此使用时，必须使用setOnTouchListener绑定到控件，
然后才能鉴定该控件的触摸事件。当一个View绑定了OnTouchLister后，当有touch事件触发时，就会调用onTouch方法。
1、如果setOnTouchListener中的onTouch方法返回值是true（事件被消费）时，则onTouchEvent方法将不会被执行；
2、只有当setOnTouchListener中的onTouch方法返回值是false（事件未被消费，向下传递）时，onTouchEvent方法才被执行。
3、以上说的情况适用于View对象（事件会最先被最内层的View对象先响应）而不是ViewGroup对象（事件会最先被最外层的View对象先响应）。
综合来讲：
onTouchListener的onTouch方法优先级比onTouchEvent高，会先触发。
假如onTouch方法返回false，会接着触发onTouchEvent，反之onTouchEvent方法不会被调用。
内置诸如click事件的实现等等都基于onTouchEvent，假如onTouch返回true，这些事件将不会被触发。

三、ACTION_CANCEL什么时候触发
子View在处理一个Touch事件中，父View的onInterceptTouchEvent返回true，此时子View会接收到MotionEvent.ACTION_CANCEL。
比如在ViewGroup的onInterceptTouchEvent中这样写
if (event.getAction() == MotionEvent.ACTION_DOWN) {
    return super.onInterceptTouchEvent(event);
} else {
    return true;
}
首先需要把DOWN事件传递到子View，不然子View也没法收到后续事件

四、事件是先到DecorView还是先到Window
因为解耦的原因，所以要DecorView -> Activity -> PhoneWindow -> DecorView传递事件。
ViewRootImpl并不知道有Activity这种东西存在！它只是持有了DecorView。所以，不能直接把触摸事件送到Activity.dispatchTouchEvent()；
不直接分发给DecorView，而是要通过PhoneWindow来间接发送也是因为Activity不知道有DecorView！但是，Activity持有PhoneWindow，
而PhoneWindow当然知道自己的窗口里有些什么了，所以能够把事件派发给DecorView。
在Android中，Activity并不知道自己的Window中有些什么，这样耦合性就很低了。
不管Window里面的内容如何，只要Window仍然符合Activity制定的标准，那么它就能在Activity中很好的工作。当然，这就是解耦所带来的扩展性的好处。
五、点击事件被拦截，但是想传到下面的View，如何操作
1、可以在Activity的dispatchTouchEvent中调用view.dispatchTouchEvent(ev)但这样做并不是明智的选择

2、重写子类的requestDisallowInterceptTouchEvent()方法返回true就不会执行父类的onInterceptTouchEvent()，即可将点击事件传到下面的View
不知道这个到底怎么写的！！！
六、如何解决View的事件冲突
https://blog.csdn.net/LoverLeslie/article/details/102706860

七、在 ViewGroup 中的 onTouchEvent 中消费 ACTION_DOWN 事件，ACTION_UP事件是怎么传递
首先，我们先分析一下 ACTION_DOWN 的事件走向，由于 ViewGroup 中的 onInterceptTouch 是默认设置的，那么 ACTION_DOWN 的事件最终在 ViewGroup 中的 onTouchEvent 方法中停止了，
事件走向是这样的：
-> Activity.dispatchTouchEvent()
-> ViewGroup1.dispatchTouchEvent()
-> ViewGroup1.onInterceptTouchEvent()
-> view1.dispatchTouchEvent()
-> view1.onTouchEvent()
-> ViewGroup1.onTouchEvent()
接着 ACTION_MOVE 和 ACTION_UP 的事件分发流程，之后 onInterceptTouch 和 View 中的方法都不会被调用了，事件分发如下：
-> Activity.dispatchTouchEvent()
-> ViewGroup1.dispatchTouchEvent()
-> ViewGroup1.onTouchEvent()

八、Activity ViewGroup和View都不消费ACTION_DOWN,那么ACTION_UP事件是怎么传递的
默认Activity消费，之后事件直接在Activity消费 不会执行viewGroup View的dispatchTouchEvent
2021-12-26 12:23:44.832 24216-24216/com.study.android D/TestEventActivity: dispatchTouchEvent: ev.getAction = 0
2021-12-26 12:23:44.834 24216-24216/com.study.android D/DecorViewGroup: dispatchTouchEvent getAction = 0
2021-12-26 12:23:44.834 24216-24216/com.study.android D/DecorViewGroup: onInterceptTouchEvent event.getAction = 0
2021-12-26 12:23:44.834 24216-24216/com.study.android D/DecorView: dispatchTouchEvent ----event.getAction = 0
2021-12-26 12:23:44.835 24216-24216/com.study.android D/DecorView: onTouchEvent =================点击====
2021-12-26 12:23:44.835 24216-24216/com.study.android D/DecorViewGroup: onTouchEvent event.getAction = 0
2021-12-26 12:23:44.836 24216-24216/com.study.android D/TestEventActivity: onTouchEvent: ---------- event.getAction() = 0
2021-12-26 12:23:44.872 24216-24216/com.study.android D/TestEventActivity: dispatchTouchEvent: ev.getAction = 1
2021-12-26 12:23:44.872 24216-24216/com.study.android D/TestEventActivity: onTouchEvent: ---------- event.getAction() = 1
九、同时对父 View 和子 View 设置点击方法，优先响应哪个
优先响应子View的点击事件
十、requestDisallowInterceptTouchEvent的调用时机
在子View的onTouchEvent中，结合问题三理解！！！


