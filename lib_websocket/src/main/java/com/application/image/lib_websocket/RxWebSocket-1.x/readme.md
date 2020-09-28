# RxWebSocket #
## 招聘：我司正在招聘Android架构师职位，如有兴趣，请点击[ Android 架构师](https://www.lagou.com/jobs/6143130.html?source=pl&i=pl-2&show=2996b58ba8d24315bac6932d3193e775)查看招聘详情，简历可发送到 dhh@gs-robot.com 邮箱，期待你的到来！
[![](https://img.shields.io/badge/platform-android-brightgreen.svg)](https://developer.android.com/index.html) 
[ ![Download](https://api.bintray.com/packages/dhhandroid/maven/rxwebsocket/images/download.svg) ](https://bintray.com/dhhandroid/maven/rxwebsocket/_latestVersion)
[ ![API](https://img.shields.io/badge/API-11%2B-blue.svg?style=flat-square) ](https://developer.android.com/about/versions/android-3.0.html)
[ ![License](http://img.shields.io/badge/License-Apache%202.0-blue.svg?style=flat-square) ](http://www.apache.org/licenses/LICENSE-2.0)
## RxWebSocket是一个基于okhttp和RxJava(RxJava1和RxJava2都有)封装的WebSocket客户端,此库的核心特点是  除了手动关闭WebSocket(就是RxJava取消订阅),WebSocket在异常关闭的时候(onFailure,发生异常,如WebSocketException等等),会自动重连,永不断连.其次,对WebSocket做的缓存处理,同一个URL,共享一个WebSocket.
## 原理解析: [戳我戳我戳我](http://blog.csdn.net/huiAndroid/article/details/78071703)
## [RxJava2版本点我(RxJava2和RxJava1调用方式一样)](https://github.com/dhhAndroid/RxWebSocket/tree/2.x)
### [查看changeLog](https://github.com/dhhAndroid/RxWebSocket/blob/1.x/ChangeLog.md)
## 效果图 ##
![](image/WebSocket.gif)
### 断网重连测试
![断网重连测试](image/recontection.gif)

## how to use ##

### 添加依赖: ###

#### 在项目module下gradle加入:
```gradle
    implementation 'com.dhh:websocket:2.1.2'
```
### init
```java

        //init config 在使用RxWebSocket之前设置即可，推荐在application里初始化
        Config config = new Config.Builder()
                .setShowLog(true)           //show  log
                .setClient(yourClient)   //if you want to set your okhttpClient
                .setShowLog(true, "your logTag")
                .setReconnectInterval(2, TimeUnit.SECONDS)  //set reconnect interval
                .setSSLSocketFactory(yourSSlSocketFactory, yourX509TrustManager) // wss support
                .build();
        RxWebSocket.setConfig(config);
```
### WSS support,其实就是设置okhttp的SSL,请参照okhttp的设置，请参照上面Config配置
### 心跳检测：需要设置自己的okhttpClient，在上面的Config里设置心跳间隔：
```java

        Config config = new Config.Builder()
                .setClient(new OkHttpClient.Builder()
                        .pingInterval(3, TimeUnit.SECONDS) // 设置心跳间隔，这个是3秒检测一次
                        .build())  //if you want to set your okhttpClient
                .build();
```

### open WebSocket:和RxJava调用一样，回调请使用项目里提供的 **WebSocketSubscriber**，WebSocketSubscriber是一个没有抽象方法的抽象类，根据业务需求，重写你想使用的回调

```java

        RxWebSocket.get("url")
                .subscribe(new WebSocketSubscriber() {
                    @Override
                    protected void onMessage(@NonNull String text) {

                    }
                });

        RxWebSocket.get("your url")
                //RxLifecycle : https://github.com/dhhAndroid/RxLifecycle
                .compose(RxLifecycle.with(this).<WebSocketInfo>bindToLifecycle())
                .subscribe(new WebSocketSubscriber() {
                    @Override
                    public void onOpen(@NonNull WebSocket webSocket) {
                        Log.d("MainActivity", "onOpen1:");
                    }

                    @Override
                    public void onMessage(@NonNull String text) {
                        Log.d("MainActivity", "返回数据:" + text);
                    }

                    @Override
                    public void onMessage(@NonNull ByteString byteString) {

                    }

                    @Override
                    protected void onReconnect() {
                        Log.d("MainActivity", "重连:");
                    }

                    @Override
                    protected void onClose() {
                        Log.d("MainActivity", "onClose:");
                    }
                });

```
#### 如果你想将String类型的text解析成具体的实体类,请使用 **WebSocketSubscriber2**
```java
        /**
         *
         *如果你想将String类型的text解析成具体的实体类，比如{@link List<String>},
         * 请使用 {@link  WebSocketSubscriber2}，仅需要将泛型传入即可
         */
        RxWebSocket.get("your url")
                .compose(RxLifecycle.with(this).<WebSocketInfo>bindToLifecycle())
                .subscribe(new WebSocketSubscriber2<List<String>>() {
                    @Override
                    protected void onMessage(List<String> strings) {

                    }
                });
```

### 发送消息 ###
```java
        //用WebSocket的引用直接发
        mWebSocket.send("hello word");
        //url 对应的WebSocket 必须打开,否则报错
        RxWebSocket.send(url, "hello");
        RxWebSocket.send(url, ByteString.EMPTY);
        //异步发送,若WebSocket已经打开,直接发送,若没有打开,打开一个WebSocket发送完数据,直接关闭.
        RxWebSocket.asyncSend(url, "hello");
        RxWebSocket.asyncSend(url, ByteString.EMPTY);
```
### 注销 ###
 RxJava的注销方式,就可以取消订阅.
```java

    Subscription subscription = RxWebSocket.get("ws://sdfs").subscribe();
    //注销
    if(subscription!=null&&!subscription.isUnsubscribed()) {
        subscription.unsubscribe();
    }

```
## 更优雅的注销处理方式,请看我的另一个项目: [RxLife](https://github.com/dhhAndroid/RxLife),优雅地处理RxJava注销问题,和Activity生命周期绑定.
## 如果本库对你有帮助,谢谢您的star!
### RxJava交流群
[![点击加群](image/RxJava交流群群二维码.png)](https://jq.qq.com/?_wv=1027&k=59SkTQ6) 

License
-------

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
