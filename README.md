<img src="./screenshot/icon.png">

# 应用简介
Android 平台在线漫画阅读器  
Online manga reader based on Android

# 感谢
- [Android Open Source Project](http://source.android.com/)
- [ButterKnife](https://github.com/JakeWharton/butterknife)
- [GreenDAO](https://github.com/greenrobot/greenDAO)
- [OkHttp](https://github.com/square/okhttp)
- [Fresco](https://github.com/facebook/fresco)
- [Jsoup](https://github.com/jhy/jsoup)
- [DiscreteSeekBar](https://github.com/AnderWeb/discreteSeekBar)
- [RxJava](https://github.com/ReactiveX/RxJava)
- [RxAndroid](https://github.com/ReactiveX/RxAndroid)
- [RecyclerViewPager](https://github.com/lsjwzh/RecyclerViewPager)
- [PhotoDraweeView](https://github.com/ongakuer/PhotoDraweeView)
- [Rhino](https://github.com/mozilla/rhino)
- [BlazingChain](https://github.com/tommyettinger/BlazingChain)

# 应用截图
<img src="./screenshot/01.png" width="250">
<img src="./screenshot/02.png" width="250">
<img src="./screenshot/03.png" width="250">
<img src="./screenshot/04.png" width="250">
<img src="./screenshot/05.png" width="250">
<img src="./screenshot/06.png" width="250">
<img src="./screenshot/07.png" width="250">
<img src="./screenshot/08.png" width="250">
<img src="./screenshot/09.png" width="250">

# 增加图源
- 继承 MangaParser 类，参照 Parser 接口的注释
- 可选地继承 MangaCategory 类，参照 Category 接口的注释
- 在 SourceManger 的 getParser() 方法中加入相应分支
- 在 UpdateHelper 的 initSource() 方法中初始化图源