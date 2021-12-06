# LearnAndroid

## android动画 
1. [矢量动画](https://juejin.cn/post/6847902224396484621)   
2. [属性动画](https://www.jianshu.com/p/f9f73f26e317)

## 系统学习 
1. [kotlin](https://play.kotlinlang.org/byExample/overview)



## AIDL 使用
### app作为服务端提供方法供client端使用
1.  服务端 app 在java目录右键创建ALDL 文件，文件会在main->aidl->包名里出现然后  build->make build app 会在build-》out 生成对应的文件
2. app 里写上自己 服务MyService 一定要在Mainfest.xml 中注册


3.将服务端aidl代码同等的 考入到  client 的main 下，然后根据client中MainActivity方式使用app提供的功能

