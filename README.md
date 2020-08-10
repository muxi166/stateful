# Stateful

### 介绍
#### StatefulLiveData
* 辅助android开发者在中间层向view层传递数据和状态的LiveData。
* kotlin协程推荐使用
1. 不用考虑在中间层切线程，直接通过`postValue`方法把数据提交到`SatefulLiveData`中，观察`StatefulLiveData`的View层代码会自动切换到主线程处理数据。
2. 在子线程中发生的异常会提交到`StatefulLiveData`中，由`view`层进行详尽的分析与处理。
3. 子线程可以随时将自己处理数据的进度与状态提交到`StatefulLiveData`中，`view`层可及时做出响应。
4. 将任意一个`LiveData`实例扩展为一个新的`StatefulLiveData`实例，原`LiveData`的新数据会通过`StatefulLiveData`的`onSuccess`方法响应。

#### StatefulLayout
* 辅助android开发者在不同的状态下切换界面的布局。
1. 通过`setStatus`方法切换不同的布局。
2. 可设置不同状态下的默认界面。
3. 可设置切换状态时的动画。

### 引入
1.  在你的android工程的根目录下的build.gradle文件中的适当的位置添加以下代码：
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
2.  选择要使用的模块，在你的android工程中对应的android模块的build.gradle文件中的适当位置添加以下代码：

| 模块   | 依赖   |
|----------|------------------------------------------------------------|
| layout   | implementation 'com.gitee.numeron.stateful:layout:1.0.0'   |
| livedata | implementation 'com.gitee.numeron.stateful:livedata:1.0.0' |



### 使用方法：

| 模块 | 使用方法 |
|------|--------|
| layout | 暂无 |
| livedata | [掘金-关于LiveData的最佳使用方法](https://juejin.im/post/6844904150031941639) |