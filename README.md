##### 1、初始化框架

```
GLog.initLogger(BuildConfig.DEBUG, false, "GDEMO", true, true, new GLogInterface() {
    @Override
    public String objectToString(Object object) {
        return new Gson().toJson(object);
    }
});
```
第一个参数是是否打印日志，推荐使用BuildConfig.DEBUG，在调试环境中打印，正式环境不打印以避免在正式环境中影响性能;
第二个参数是是否以ERROR级别显示所有日志，这个一般给false就行；
第三个参数是日志标签的开头，用来筛选日志，一般使用项目名称的简写，能区分来自不同项目的日志即可；
第四个参数是是否显示日志模块名，一般给true就行；
第五个参数是是否显示日志类型，一般给true就行；
第六个参数是一个接口，需要在其中的objectToString方法中提供将对象转换为字符串的功能，我这里使用了我常用的Gson，大家在用的时候可以用任意框架或方法，只要能将对象转换成Json字符串即可。
##### 2、实现日志打印器对象
```
package com.gegz.glog;
import com.gegz.logger.core.GLogExcutor;
import com.gegz.logger.core.constants.GLogTypes;
import com.gegz.logger.core.entity.GLogEntity;

/** 
 * 直播网络日志类 
 */
package com.gegz.glog;

import com.gegz.logger.core.GLogExcutor;
import com.gegz.logger.core.constants.GLogTypes;
import com.gegz.logger.core.entity.GLogEntity;

/**
 * 直播网络日志类
 */
public class LiveNetworkLogger extends GLogExcutor {

    // 日志工具单例
    private static volatile LiveNetworkLogger liveNetworkLogger;

    /**
     * 构造方法
     */
    private LiveNetworkLogger(GLogEntity entity) {
        super(entity);
    }

    /**
     * 获取单例的方法
     */
    public static LiveNetworkLogger getInstance() {
        if (liveNetworkLogger == null) {
            synchronized (LiveNetworkLogger.class) {
                if (liveNetworkLogger == null) {
                    initLogger();
                }
            }
        }
        return liveNetworkLogger;
    }

    /**
     * 初始化日志工具类的方法
     */
    private static void initLogger() {
        GLogEntity entity = new GLogEntity();
        entity.initClassName("LiveNetworkLogger");
        entity.initModuleName("LIVE");
        entity.initTypeName("NET");
        entity.initDefaultLevel(GLogTypes.V);
        entity.initJsonLevel(GLogTypes.V);
        entity.initFormatJson(true);
        entity.initTraceCount(3);
        entity.initForceShow(false);
        entity.initShowDivide(false);
        entity.initShowAll(true);
        entity.initMaxLengthCount(3);
        liveNetworkLogger = new LiveNetworkLogger(entity);
    }
}
```
如图所示，这里使用了单例模式来创建一个GLogExcutor（以下称为日志打印器）的子类，但你也可以直接创建一个日志打印器，都是可以的。这段代码的重点是initLogger方法，这个方法里你可以给这个打印器设置一些日志格式和参数，我们这里一个一个看一下：
className - 这个参数用这个类的类名即可，如果是直接创建日志打印器而非继承它的话就给它起一个自己记得住的名字，用处我们之后讲；
moduleName/typeName - 我们日志最终打印时的tag会是“项目名_模块名_类型名_标签的格式”，如“GDEMO_LIVE_NET_RESPONSE”，就是指GDEMO这个项目中，live模块下网络相关日志中，标签为response的日志，给这些参数提供恰当的值能让我们很方便的在logcat中区分和查看它们；
defaultLevel - 默认日志级别，我们在调用这个框架打印日志时，有一个方法会采用这个值作为日志的级别，我们后续会讲到；        
jsonLevel：框架中json/xml日志的级别，这里的json/xml日志特指调用框架的json/xml方法打印出的日志，这点我们后续详细讲；
formatJson - 是否需要显示格式化后的json/xml内容；
traceCount - 显示任务栈的数量，关于这点我们也放到之后详细解释；
forceShow - 是否强制显示，当该参数为true时，即使我们初始化框架时提供的是否打印日志的参数为false日志也会打印，比如我们有时候需要部分日志在release包中也显示时可以把该项设置为true；
showDivide - 是否显示分割线，如果显示的话每条日志直接区分会更清晰，但相应的日志会更长，可以根据自己的喜好选择；
showAll/maxLengthCount - 是否打印超过logcat日志上限的内容以及最多显示几大块日志，每大块日志4000行，即当showAll为true且maxLengthCount为3时最多能显示12000行的内容；
##### 3、调用日志对象进行日志打印
```
LiveNetworkLogger.getInstance().v();
```
我们完成初始化并创建出打印器对象以后，就可以用它来打印一个日志试试了，我们通过这个日志来了解一下这个框架打印出来的日志的基本结构。![775388b533dfa607bc1e610180534533.png](evernotecid://65BD031C-065D-4DC2-9FDF-1CCCE34DA96D/appyinxiangcom/23747610/ENResource/p100)
如上图所示，我们打印出了一条日志，可以看到，我们这个日志的tag正是我们提供的“项目名_模块名_日志类型”的结构。打印出的日志级别是v，和我们调用的方法相符。
右侧有三行at开头的内容，这个结构我们应该很熟悉，是项目报错时会有的调用栈信息，但是不要担心，我们的代码并没有出错，这个调用栈是我们自行打印出来的，没错，我们是可以自行打印调用栈的，如果对这项功能的实现方式感兴趣可以下载源码来阅读一下，我们的源码并不复杂，还有大量的注释辅助理解，相信聪明的你一定看得懂。
如果此时你正在一边看这篇文章一边在Android Studio上实践的话，不妨点一下蓝色的MainActivity.java字样，你会发现你定位到了这行日志在代码中的位置，有时候我们在调试长期不维护或别人写的项目时，常常无法确认异常日志在代码中的位置，有了这个功能我们就可以很方便的定位到了。
还记得我们给日志打印器设置的参数中的traceCount吗？没错，这个参数就是对应这个调用栈的行数的。有时候我们在调试很多地方都在调用的方法时，我们不知道具体是从哪个地方进入的，就可以把调用栈行数设置的大一点，来确认代码执行的流程。
最后右下角有一个excute，代表这行日志代码被执行了，这个无参的方法主要用来判断某个地方是否被调用了以及调用栈信息，如果想要打印出更多信息，就需要用到一些其它重载方法了。
##### 4、其它重载方法
```
LiveNetworkLogger.getInstance().v(message);
```
我相信聪明的你不需要图片也能猜到这个方法会打印出什么来吧？我们节省空间直接看下一个。
```
LiveNetworkLogger.getInstance().v(TAG, message);
```
![4c36778b3e9b8d106301740a1d3e151d.png](evernotecid://65BD031C-065D-4DC2-9FDF-1CCCE34DA96D/appyinxiangcom/23747610/ENResource/p101)
我们结合打印出来的图片来看看这个方法，可以看到，我们的message参数的内容是hello，所以我们右下角打印出来的内容就从之前的excute变成了hello，那我们传的TAG参数呢？可以看到，我们左边的日志tag从之前的GDEMO_LIVE_NET变成了GDEMO_LIVE_NET_RESPONSE，而RESPONSE正是我们这个TAG参数的值。所以这个重载方法就是可以自定义打印的内容并且给tag里面添加内容以方便我们更精准的筛选到这条日志。
```
LiveNetworkLogger.getInstance().v(TAG, params1, params2, params3);
```
![4e2089afced2e578cfdb1bdc6bf6ed41.png](evernotecid://65BD031C-065D-4DC2-9FDF-1CCCE34DA96D/appyinxiangcom/23747610/ENResource/p102)
可以看到，这个地方我们的参数从message变成了多个参数params1、params2、params3，而打印出来的内容也变成了如图所示的格式，方便我们分辨传入的几个参数。理所当然的是，这个方法可以接收任意数量的参数，而不是一定要传入三个。事实上如果你去看一眼源码的话，你会发现之前的`LiveNetworkLogger.getInstance().v(TAG, message);`和`LiveNetworkLogger.getInstance().v(TAG, params1, params2, params3);`调用的其实是同一个方法。
##### 5、其它日志级别
```
LiveNetworkLogger.getInstance().d(TAG, message);
LiveNetworkLogger.getInstance().i(TAG, message);
LiveNetworkLogger.getInstance().w(TAG, message);
LiveNetworkLogger.getInstance().e(TAG, message);
LiveNetworkLogger.getInstance().a(TAG, message);
LiveNetworkLogger.getInstance().print(TAG, message);
```
毫无疑问，我们的日志框架不可能仅支持一个级别的日志的打印，这个图中我们可以看到，我们也可以用这些方法去打印Debug、Error等级别的日志。这个图中有个a方法对应的是WTF级别的日志，这个级别我个人不太常用，但是也属于官方提供的一个级别，所以写在里面了，可以理解成比Error级别更高一级的日志。最后一个print方法对应的并不是一种特定级别的日志，而是根据我们之前给日志打印器设置的参数defaultLevel而定的，这里你可能会觉得这个功能很多余，但耐心看下去，之后你会看到这个方法有多么强大，事实上这个方法是我推荐在绝大部分情况下调用的打印方法。另外，和`LiveNetworkLogger.getInstance().v();`方法一样，这些方法也都有同样的重载方法可以使用。
##### 6、其它可以调用的方法
```
LiveNetworkLogger.getInstance().json(jsonString);
```
![1cc9653fe8230666e18ed1737e5fb6a3.png](evernotecid://65BD031C-065D-4DC2-9FDF-1CCCE34DA96D/appyinxiangcom/23747610/ENResource/p103)
这个方法是帮助我们更直观的查看看json字符串的内容的，如上图所示，原本显示内容的地方不仅显示了我们jsonString参数原本的内容，还在下方将其格式化以方便我们分析。如之前所述，这个方法打印的级别受日志打印器的jsonLevel参数控制，而下方格式化的内容受formatJson参数控制。需要注意的是，这个方法仅支持纯Json的字符串，混杂的其它内容的Json字符串是识别不出来的。
```
LiveNetworkLogger.getInstance().json(TAG, jsonString);
LiveNetworkLogger.getInstance().json(object);
LiveNetworkLogger.getInstance().json(TAG, object);
```
这三个方法打印的结果我就不展示了，其中的TAG参数我们之前介绍过就不过多赘述，object参数则是让我们可以把一个对象不用转换成json字符串格式就打印出来，还记得之前我们需要重写的objectToString方法吗？没错，就是通过这个方法把对象转换成json字符串。为了减少框架的依赖项和代码量，我把这个功能交给调用者实现。
```
LiveNetworkLogger.getInstance().xml(xmlString);
LiveNetworkLogger.getInstance().xml(TAG, xmlString);
```
![406071cc9f7eb13f569a5c3bbd06313a.png](evernotecid://65BD031C-065D-4DC2-9FDF-1CCCE34DA96D/appyinxiangcom/23747610/ENResource/p104)
这两个方法如上图所示，可以将xml字符串格式化的显示出来，它们同样受jsonLevel和formatJson这两个参数的控制。另外要注意，这个功能会被xml中的空格、回车、制表符等字符影响，所以只能格式化类似`<persons><person id="11"><name>SAX解析</name><age>18</age></person><person id="13"><name>XML1</name><age>43</age></person></persons>`这种的字符串。
```
LiveNetworkLogger.getInstance().file(targetDirectory, message);
LiveNetworkLogger.getInstance().file(TAG, targetDirectory, message);
LiveNetworkLogger.getInstance().file(TAG, targetDirectory, fileName, message);
```
![764816503e64d080dac06b2cf7e01a71.png](evernotecid://65BD031C-065D-4DC2-9FDF-1CCCE34DA96D/appyinxiangcom/23747610/ENResource/p105)
  这三个方法可以将message这个参数中的内容保存到文件夹中，targetDirectory即是保存内容的文件夹，如果不传fileName会自动生成一个文件名，如果传了就会用给定的文件名。需要注意的是，框架提供了创建文件和文件夹的代码，但是你需要自行处理权限问题。上图显示了调用后logcat显示的内容，可以看到如果打印成功的话我们就能看到文件保存的位置。
```
LiveNetworkLogger.getInstance().trace();
```
![4ed2b7d90c4d5e53056293ed84d9aff0.png](evernotecid://65BD031C-065D-4DC2-9FDF-1CCCE34DA96D/appyinxiangcom/23747610/ENResource/p109)
这个方法可以把当前位置的调用栈打印出来，可以用来帮助我们确定代码执行流程。
##### 7.运行中配置界面
```
GLog.startSetttingActivity(MainActivity.this);
```
![b05c4138433c42d8563943b8b0918030.png](evernotecid://65BD031C-065D-4DC2-9FDF-1CCCE34DA96D/appyinxiangcom/23747610/ENResource/p106)
 你是否曾经因为打印日志的级别不合适而不得不重新编写日志代码并运行？如果有的话，这个功能可以帮助到你。通过调用以上方法，我们可以跳转进入这个界面，在这个界面里，我们可以配置我们之前提到的各种日志打印器的参数。还记得我们之前提到的`LiveNetworkLogger.getInstance().print(TAG, message);`方法吗？为什么我特别推荐了这个方法，因为当你发现你要看的日志级别不合适导致难以调试时，你只需要进到这个界面，把defaultLeve的级别调整一下，再重新打印一遍日志即可解决，省去了重新运行的时间。这个界面还可以用来调整一些别的参数，举个例子，我们运行过程中发现调用栈行数设置的不够导致我们看不到是从哪个位置进入我们日志所在的方法时，就可以进入这个界面调整一下traceCount参数的值，再重新打印一遍日志即可。这个界面的入口我建议大家使用BuildConfig.DEBUG进行控制，只在调试环境下显示，以避免线上用户看到这个界面。事实上，我在Demo中提供了一个入口，也就是下图中这个写着“G”的按钮，大家可以参考一下Demo中的BaseActivity，这个入口写在BaseActivity中可以让它在所有界面显示，并且这个入口是可拖动的，不会影响我们界面的其它内容。
![0988319f63ca5f8ae80658986bc9e271.png](evernotecid://65BD031C-065D-4DC2-9FDF-1CCCE34DA96D/appyinxiangcom/23747610/ENResource/p107)
