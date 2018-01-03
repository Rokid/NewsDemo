# Rokid NativeApp开发流程


## 一、开发Rokid技能

####可参考[Rokid开发者社区](https://rokid.github.io/docs/ "Title")中[开发Rokid技能](https://rokid.github.io/docs/1-GetStarted/rokid-skill-kit-introduction.html)进行Rokid技能开发。

## 二、NativeApp开发说明
进行**NativeApp**开发是为了实现定制化Rokid本地应用而创建的一项系统能力。目前的系统设计是基于Android系统AMS机制管理、调度应用，所以，NativeApp的原型是基于Activity实现的，基于Activity作为载体的，需要注意的是，一个NativeApp只能存在一个Activit作为应用的载体。同时，需要开发者为NativeApp进行应用配置，如RokidManifest.xml的配置，如下所示：

```xml
<manifest xmlns:rokid="http:www.rokid-inc.com" rokid:version="1.0.0">
<application rokid:isSystem="false">
<scene rokid:activity="com.rokid.news.MainActivity"
rokid:appID="REE7B5E1BA20482FBE3BB860C0FFD30C" />
</application>
```

#####标签介绍：
*  isSystem:除系统级应用外，该值均为false。
2. scene:设置应用类型为scene应用，scene应用定义为可恢复类型应用，即在cut应用进入应用栈时，scene暂停所做的事情，同时在cut应用执行任务结束时继续所执行的任务。开发者在使用此类型时需要在应用activity的onPause和onResume进行暂停任务的操作和恢复任务操作。
3. cut:设置应用类型为cut应用，cut应用定义为可被中断类型应用，即在cut应用运行时，当scene应用或者新的cut应用进入应用栈时，都会把该cut应用中断掉，并从应用栈中清除。开发者在使用此类型时需要在应用activity的onPause方法中进行finish当前应用。
4. appId:作为应用的唯一标志符，appId由服务端配置生成，系统通过appId的值来查找并进入NativeApp。


## 三、新闻应用开发示例

###（一）新闻Skill技能创建
![Alt text](./news_skill.png)

###（二）创建新闻应用交互设计intent
#####创建新闻应用词表
![Alt text](./customer_word.png)

#####创建新闻应用交互intent
![Alt text](./intent.png)


```json
{
"intents": [
{
"intent": "news_play",
"slots": [
{
"name": "iwant",
"type": "iwant"
},
{
"name": "can",
"type": "can"
},
{
"name": "forMe",
"type": "forMe"
},
{
"name": "play",
"type": "play"
},
{
"name": "newsmode",
"type": "newsmode"
},
{
"name": "newstype",
"type": "newstype"
},
{
"name": "keyword",
"type": "keyword"
},
{
"name": "today",
"type": "today"
}
],
"user_says": [
"($iwant|$can?$forMe|$can)?$play$newsmode?的?$newstype?类?$keyword",
"($iwant|$can?$forMe|$can)?$play$today?的?$newsmode?的?$newstype?类?$keyword",
"$today?$play$newstype?类?$newsmode?的?$keyword",
"($iwant|$can?$forMe|$can)?$play?$today的?$newsmode?的?$newstype?类?$keyword",
"#我要听$keyword",
"#我想听$keyword",
"#我要看$keyword",
"#我想看$keyword"
]
},
{
"intent": "news_previous",
"slots": [
{
"name": "pre",
"type": "pre"
},
{
"name": "play",
"type": "play"
},
{
"name": "forMe",
"type": "forMe"
},
{
"name": "can",
"type": "can"
},
{
"name": "iwant",
"type": "iwant"
},
{
"name": "one",
"type": "one"
}
],
"user_says": [
"($iwant|$can?$forMe|$can)?$play?$pre$one?(新闻)?"
]
},
{
"intent": "news_next",
"slots": [
{
"name": "next",
"type": "next"
},
{
"name": "play",
"type": "play"
},
{
"name": "forMe",
"type": "forMe"
},
{
"name": "can",
"type": "can"
},
{
"name": "iwant",
"type": "iwant"
},
{
"name": "one",
"type": "one"
}
],
"user_says": [
"($iwant|$can$?$forMe|$can)?$play?$next$one?(新闻)?"
]
},
{
"intent": "news_finish",
"slots": [],
"user_says": [
"停止",
"别放了",
"停止播放",
"关了",
"关闭",
"关",
"停",
"不听了",
"我不听了",
"我不看了",
"退出"
]
},
{
"intent": "news_pause",
"slots": [],
"user_says": [
"暂停",
"停一下",
"暂停一下",
"停下",
"暂停下"
]
},
{
"intent": "news_resume",
"slots": [],
"user_says": [
"继续",
"继续播",
"继续播放"
]
}
]
}
```
###（三）配置新闻RokidManifest
#####在Android工程assets目录下创建RokidManifest文件，示例：
```xml
<manifest xmlns:rokid="http:www.rokid-inc.com" rokid:version="1.0.0">
<application rokid:isSystem="false">
<scene rokid:activity="com.rokid.news.MainActivity"
rokid:appID="REE7B5E1BA20482FBE3BB860C0FFD30C" />
</application>
```

###（四）新闻客户端代码简述
#####CommandController:新闻消息分发者。
```java
switch (intentEvent) {
case COMMAND_PLAY:
case COMMAND_WELCOME:
if (!isStarted) {
netCommandProcessor.start();
isStarted = true;
} else {
netCommandProcessor.resume();
}
break;
case COMMAND_NEXT:
if (isStarted) {
netCommandProcessor.next();
}
break;
case COMMAND_PREVIOUS:
if (isStarted) {
netCommandProcessor.previous();
}
break;
case COMMAND_FINISH:
netCommandProcessor.finish();
break;
case COMMAND_PAUSE:
netCommandProcessor.pause();
break;
case COMMAND_RESUME:
netCommandProcessor.resume();
break;
default:
Log.d(TAG, "unKnow command " + intentEvent);
}
```
#####BaseCommandProcessor、NetCommandProcessor：新闻消息的处理者。


#####详细代码见NewsDemo.


