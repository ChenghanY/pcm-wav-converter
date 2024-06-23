## 1. 前言

### 1.1 背景
系统新接入语音引擎。
语音引擎只认 pcm 格式数据。前端只认 wav 格式 。
需要后端对 pcm 和 wav 格式实现互转，特此编写工具类，并提供测试用例。
![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/91f288523ac34967aba674e7b978905f.png)

### 1.2 目标
- 提供简练的 `Java api` 实现格式转化 `pcmToWav()`  `wavToPcm()`
- 复习 `Java 7` 的文件操作
- 编写工具类和测试用例，刻意进行 `clean code` 练习
### 1.3 亮点
- 测试用例使用了 `Java 7 ` 开始引入的文件操作api
- 测试用例使用了 `Java nio` 完成文件下载功能


## 2. 用例说明
1. 执行 `AudioFormatConverterTest`
2. 观察新生成的文件
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/a8c688d30f044630914c8eb448e2b0cc.png)
3. 能支持幂等执行测试用例，可debug进行调试

## 3. 补充验证
项目中语音数据通过字节流传播，对 pcm 数据可以任意裁剪叠加。
pcm 数据增加一倍，音频播放的内容重复一遍。
![在这里插入图片描述](https://img-blog.csdnimg.cn/direct/514cfbbaf433436088ac4298532188f7.png)


## 4. 相关链接
[【Java】字节数组 pcm 与 wav 格式互转 （附原理概述）](https://blog.csdn.net/chenghan_yang/article/details/139881216)
