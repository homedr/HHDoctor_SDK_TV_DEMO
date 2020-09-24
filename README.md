## 视频医生 Android SDK TV版接入文档 V3.0.4.09231508

## [查看版本更新说明](#五版本更新说明)

* [一、SDK接入引用说明](#一sdk接入引用说明)
    * [1. 建议接入环境](#1-建议接入环境)
        * [1.1 建议接入使用IDE版本](#11-建议接入使用ide版本)
        * [1.2 建议接入SDK版本以及最低支持设备系统版本](#12-建议接入sdk版本以及最低支持设备系统版本)
    * [2. 视频医生Android SDK通过maven仓库引用来导入工程，如下](#2-视频医生android-sdk通过maven仓库引用来导入工程如下)
        * [2.1 在build.gradle文件中配置远程库地址，在respositories中添加相应配置](#21-在buildgradle文件中配置远程库地址在respositories中添加相应配置)
        * [2.2 在build.gradle文件中dependencies中配置库的引用](#22-在buildgradle文件中dependencies中配置库的引用)
        * [2.3 配置NDK架构选择，必须进行对应配置](#23-配置ndk架构选择必须进行对应配置)
        * [2.4 java8支持的配置，必须配置](#24-java8支持的配置必须配置)
        * [2.5 packageingOptions配置，必须配置](#25-packageingoptions配置必须配置)
    * [3. 我们用到的常用第三方库以及库的版本](#3-我们用到的常用第三方库以及库的版本)
     
* [二、SDK接入引用说明](#二sdk接入引用说明)
    * [1. SDK初始化](#1-sdk初始化)
        * [1.1 SDK配置选项 HHSDKOptions](#11-sdk配置选项-hhsdkoptions)
        * [1.3 SDK初始化](#13-sdk初始化)
        * [1.4 SDK使用到的主要权限说明](#14-sdk使用到的主要权限说明)
    * [2. SDK功能介绍](#2-sdk功能介绍)
        * [2.1 获取SDK版本号](#21-获取sdk版本号)
        * [2.2 登录](#22-登录)
        * [2.3 登出](#23-登出)
        * [2.4 呼叫成人医生](#24-呼叫成人医生)
        * [2.5 呼叫儿童医生](#25-呼叫儿童医生)
        * [2.6 获取用户登录状态](#26-获取用户登录状态)
    * [3. 回调说明](#3-回调说明)
        * [3.1 登录回调（HHLoginListener）](#31-登录回调hhloginlistener)
        * [3.2 呼叫回调（HHCallListener）](#32-呼叫回调hhcalllistener)

* [三、常见问题](#三常见问题)
    * [1. AndroidManifest合并冲突问题](#1-androidmanifest合并冲突问题)
    * [2. error:style attribute '@android:attr/windowEnterAnimation' not found](#2-errorstyle-attribute-androidattrwindowenteranimation-not-found)
    * [3. 如果遇到库冲突也就是duplicate某个包这说明库冲突了，这种问题可以用如下方法解决](#3-如果遇到库冲突也就是duplicate某个包这说明库冲突了这种问题可以用如下方法解决)

* [四、Demo下载地址](#四demo下载地址)
* [五、版本更新说明](#五版本更新说明)

### 一、SDK接入引用说明

#### 1. 建议接入环境

##### 1.1 建议接入使用IDE版本

Android Studio 3.x.x版本以上版本

##### 1.2 建议接入SDK版本以及最低支持设备系统版本

|配置项|版本|
|---|---|
|compileSdkVersion| 28及以上|
|minSdkVersion| 17及以上|
|targetSdkVersion| 28及以上|
|最低支持设备系统| >= 4.2 |

#### 2. 视频医生Android SDK通过maven仓库引用来导入工程，如下

##### 2.1 在build.gradle文件中配置远程库地址，在respositories中添加相应配置

```
repositories {

    maven {url 'http://developer.huawei.com/repo/'} //这个是在用到华为推送的时候才需要配置
    
    maven {
        credentials {
            username 'hh-public'
            password 'OFGB5wX0'
        }
        url 'http://develop.hh-medic.com/repository/maven-public'
    }
}
```

##### 2.2 在build.gradle文件中dependencies中配置库的引用

```
implementation 'com.hhmedic.android.sdk:hh_tv:3.0.4.09231508'
```

<span style="color:red;">注：添加以上配置后需要进行gradle sync才能同步生效，配置maven库地址的时候不能省略用户名和密码，否则同步不下来。</span>

##### 2.3 配置NDK架构选择，必须进行对应配置

```
ndk {
    //设置支持的SO库架构
    abiFilters "armeabi-v7a"
}
```

##### 2.4 java8支持的配置，必须配置

```
compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
}
```

##### 2.5 packageingOptions配置，必须配置

```
packagingOptions {
   pickFirst 'lib/armeabi-v7a/libsecsdk.so'
   pickFirst 'lib/arm64-v8a/libsecsdk.so'
   pickFirst 'lib/armeabi/libsecsdk.so'
}
```

#### 3. 我们用到的常用第三方库以及库的版本
```
implementation 'com.google.code.gson:gson:2.8.6'
implementation 'com.orhanobut:logger:2.2.0'
implementation 'com.github.bumptech.glide:glide:4.11.0'
implementation 'com.zhihu.android:matisse:0.5.3-beta3'
implementation 'com.squareup.okhttp3:okhttp:3.x.x' //这个版本号只是一个代写
```

> 如果由于这些包引用出现冲突例如是duplicate某个jar包或文件有可能是某些库引用的版本和我们不一致，直接force一个合适的版本就行。具体写法可以参考[这里](#5-如果遇到库冲突也就是duplicate某个包这说明库冲突了这种问题可以用如下方法解决)。


### 二、SDK接入引用说明

#### 1. SDK初始化

##### 1.1 SDK配置选项 HHSDKOptions

```java
HHSDKOptions options = new HHSDKOptions("sdkProductId");
```

参数说明：

| 参数定义 | 说明 |
| --- | --- |
| sdkProductId | 分配的产品ID |
| sDebug    |是否开启调试（开启会打印log）|
|dev|是否开始测试服模式，开启后连接测试服|
|isOpenCamera|视频过程中是否开启拍照|
|mOrientation|屏幕方向 ActivityInfo.SCREEN_ORIENTATION_PORTRAIT 或 ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE|
|useSampleRate16K_HZ|是否使用16000 也可以叫做16k的音频通道，默认这个不设置，如果在有些设备上用的16k通道可以开启这个选项设置为true|
|useSoundStreamMusic|是否播放铃声使用Music通道，一般不用设置|

##### 1.3 SDK初始化

>SDK初始化最好是放到自定义的Application中去初始化。

```java
HHSDKOptions options = new HHSDKOptions("sdkProductId");
options.isDebug = true;
options.dev = true;
options.isOpenCamera = false;
options.mOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
HHDoctor.init(getApplicationContext(),options);
```

##### 1.4 SDK使用到的主要权限说明

| 权限 | 说明 |
| --- | --- |
| android.permission.READ_PHONE_STATE | 在视频通话过程中如果有电话进来我们会做挂断视频保证正常电话通话 |
|android.permission.CAMERA|保证正常使用设备的摄像设备|
|android.permission.RECORD_AUDIO|保证正常使用设备的音频设备|
|android.permission.WRITE_EXTERNAL_STORAGE android.permission.READ_EXTERNAL_STORAGE | 保证正常读取存储设备上的媒体文件 |

#### 2. SDK功能介绍

##### 2.1 获取SDK版本号

```java
public static String SDKVersion()
```

>通过这个接口在调试的过程可以获取到当前SDK的版本号

##### 2.2 登录

```java
public static void login(Context context,String userToken,HHLoginListener listener)
```

参数说明

| 参数定义 | 说明 |
| --- | --- |
|Context context|上下文，当前操作Activity|
|String userToken|接入方和视频医生服务端对接后返回的userToken|
|HHLoginListener listener|登录回调|

##### 2.3 登出

```java
public static void loginOut(Context context)
```

参数说明：

| 参数定义 | 说明 |
| --- | --- |
|Context context|上下文，当前操作Activity|

##### 2.4 呼叫成人医生

```java
public static void callForAdult(Context context,HHCallListener listener)
```

参数说明：

| 参数定义 | 说明 |
| --- | --- |
|Context context|上下文，当前呼叫发起Activity|
|HHCallListener listener|呼叫回调|

##### 2.5 呼叫儿童医生

```java
public static void callForChild(Context context,HHCallListener listener)
```

参数说明：

| 参数定义 | 说明 |
| --- | --- |
|Context context|上下文，当前呼叫发起Activity|
|HHCallListener listener|呼叫回调|

##### 2.6 获取用户登录状态

```java
public static boolean isLogined(Context context)
```

参数说明：

| 参数定义 | 说明 |
| --- | --- |
|Context context|上下文，当前呼叫发起Activity|

#### 3. 回调说明

>主要说明在各个接口用到的回调代理

##### 3.1 登录回调（HHLoginListener）

```java
public interface HHLoginListener
{
    /**
     * 登录成功
     */
    void onSuccess();

    /**
     * 登录失败
     * @param tips
     */
    void onError(String tips);
}

```

##### 3.2 呼叫回调（HHCallListener）

```java
public interface HHCallListener
{

    /**
     * 启动呼叫
     */
    void onStart(String orderId);

    /**
     * 呼叫中
     */
    void onCalling();

    /**
     * 通话中
     */
    void onInTheCall();

    /**
     * 通话结束
     */
    void onFinish();


    /**
     * 呼叫成功，等待医生接受
     */
    void onCallSuccess();


    /**
     * 呼叫失败
     * @param code 错误码
     */
    void onFail(int code);

    /**
     * 取消呼叫  含 取消排队
     */
    void onCancel();


    /**
     *
     * 排队超时 自动取消
     */
    void onLineUpTimeout();


    /**
     *
     * 需要排队等待
     */

    void onLineUp();
}
```

### 三、常见问题

#### 1. AndroidManifest合并冲突问题

```
 <provider
            android:name="android.support.v4.content.FileProvider"
            android:authorities="${applicationId}.provider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/provider_paths" />
</provider>
```

可以更替乘如下写法

```
<manifest package="cn.edu.fudan.rndrobot"
    xmlns:tools="http://schemas.android.com/tools"
          xmlns:android="http://schemas.android.com/apk/res/android">
    <provider
        tools:replace="android:authorities"
        android:name="android.support.v4.content.FileProvider"
        android:authorities="${applicationId}.provider"
        android:exported="false"
        android:grantUriPermissions="true">
        <meta-data
            tools:replace="android:resource"
            android:name="android.support.FILE_PROVIDER_PATHS"
            android:resource="@xml/file_paths"/>
    </provider>
</manifest>
```

#### 2. error:style attribute '@android:attr/windowEnterAnimation' not found

在Project/gradle.properties中添加 android.enableAapt2=false

#### 3. 如果遇到库冲突也就是duplicate某个包这说明库冲突了，这种问题可以用如下方法解决
这种问题遇到的情况有可能是用到的库和我们SDK中用到的库冲突，如果是module的冲突类似于问题4中的那种可以用exclude来进行排序module就行，另外一种情况是库和我们SDK库引用的版本的版本不一样，只要对库的版本选一个最合适的版本force一下版本就可以解决了。force大概写法如下：
```
configurations.all {
    resolutionStrategy {
        force "com.android.support:recyclerview-v7:27.1.1"
    }
}
```

### 四、Demo下载地址

https://github.com/homedr/HHDoctor_SDK_TV_DEMO

### 五、版本更新说明

|版本号|说明|
|---|---|
|3.0.4.09231508|1.优化流程 <br/> 2.去除sdk内部默认图标，有可能会影像接入APP图标|
|3.0.2.09081811|始发版本|
