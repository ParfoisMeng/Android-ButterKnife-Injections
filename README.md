# Android ButterKnife Injections (Support Kotlin)
Android ButterKnife Injections (Support Kotlin)。支持Kotlin，可选初始化，可选R/R2。

> 关于支持Kotlin，插件的说明文档中有写明，是指“适用于父类为kt文件，子类为java文件的情况”。至于Kotlin类，有更好的`kotlinx`为什么不用呢？
> 
> 如果本插件对您有帮助，请给个star吧。顺便如果能在[JetBrains Plugins Repository](https://plugins.jetbrains.com/plugin/12012-android-butterknife-injections-support-kotlin-)评个5星就更好啦(😆)。

- - - - - 

#### 使用方法

- Android Studio -> Setting/Preferences -> Plugins -> Browse repositories，搜索 Android ButterKnife Injections (作者是ParfoisMeng)安装并重启可用。
- [下载jar包](https://github.com/ParfoisMeng/Android-ButterKnife-Injections/raw/master/Android-ButterKnife-Injections.jar)，Android Studio -> Setting/Preferences -> Plugins -> Install plugin from disk -> 选择刚刚下载的jar包安装并重启。

#### 功能点

1. 包含 Android ButterKnife Zelezny v1.6.0 所有功能，且使用方法与 Android ButterKnife Zelezny 一致。
2. 可选是否初始化 ButterKnife (避免原版本使用插件即自动在`onCreate`中添加`ButterKnife.bind(this)`,适用于在基类中初始化)。
3. 可选是否在 Lib 中使用(使用`R2`替换`R`,`onClick`中使用`if-else`)。
4. 2、3可在Android Studio设置页配置默认值。

<br>

![截图](https://github.com/ParfoisMeng/Android-ButterKnife-Injections/raw/master/screenshot/1.jpg)


------ 

#### 随便说说

公司要App优化。但原代码里大量14-16年的老代码，质量极差，耦合极重。这种代码怎么可能有性能？

决定更新。原意直接上MVVM，甚至激进的想用androidX，奈何阻力太大，弃之。由于公司App业务功能相对独立，很符合组件化的思路，但组件化的坡度略陡，所幸发现了[CC渐进式组件化改造方案](https://github.com/luckybilly/CC)（谢谢大佬），开搞。

Kotlin刚被Google认亲的时候就已开始接触学习与使用，能清晰的感受到它相较于Java的优劣。经过考虑，我在抽取基类封装lib_base时使用了Kotlin，本意是先写在基类中，让同事学习一下，慢慢的改变。（虽然目前公司还是用Java，但拥抱改变的心不能放弃，听说我要用Kotlin，同事还是颇有些抗拒的，花了好一番力气才说服之。）

问题来了。

继承Kotlin基类(BaseActivity.kt)的用Java编写的Activity/Fragment无法使用Android-ButterKnife-Zelezny插件！有报错！！

一番研究。。。辛酸泪一大把就不多说了，反正最后结果就是本插件了。（😄哈哈，还是很有成就感的）

我贴上关键处代码方便有兴趣的朋友浏览查阅：
```java

//        if (true) { // 不能直接throw，需要if(true)过编译
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < fields.length; i++) {
//                PsiField field = fields[i];
//                sb.append(i).append("\n")
//                        .append(field.toString()).append("\n")
//                        .append(field.getType()).append("\n")
//                        .append(field.getFirstChild()).append("\n");
//                if (null != field.getFirstChild()) {
//                    sb.append(field.getFirstChild().getText()).append("\n");
//                }
//            }
//            // 不知道怎么log怎么debug，只能用这种方式，build再引入使用看报错
//            throw new RuntimeException(sb.toString());
//        }

        for (PsiField field : fields) {
            // kotlin中field.getFirstChild()可能为null
            PsiElement element = field.getFirstChild();
            if (null == element) {
                continue;
            }

            annotations = element.getText().split(" ");

            for (String annotation : annotations) {
                id = Utils.getInjectionID(butterKnife, annotation.trim());
                if (!Utils.isEmptyString(id)) {
                    ids.add(id);
                }
            }
        }
        
```

PS：其他的功能代码有点乱就不贴了，有兴趣可以clone源码查阅。
