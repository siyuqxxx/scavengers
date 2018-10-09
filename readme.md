# 项目概述

## 背景

增量更新代码时，需要通过 svn 或者 git 把需要更新的代码清单整理出来，然后根据这个清单，把对应的编译后的文件，根据路径上传到服务器上指定项目目录下。

## 基本原理

基于标准 maven web 工程；
根据 srcListFile 文件中获取到的由相对路径描述的源码清单；
从指定的项目路径 projectDir 中复制 target 目录下的编译文件到指定的 exportDir 路径中；
如果定义了 serverProjectDir，则创建该路径，结果放到该路径中。

## 操作模式

两种模式，
简单模式：在工程目录下双击工程执行，
配置模式：用命令像git一样配置多个输入输出的配置

### 简单模式操作流程

+ 解析入参
  + 解析 srcListFile
    + 判断是否设置了 srcListFile
      + 如果设置了，判断 srcListFile 文件是否合法
      + 如果未设置，或者设置的文件非法，查找工程路径下是否存在默认命名，默认名称为SrcLists.txt
  + 解析 projectDir
    + 判断是否设置了 projectDir
      + 如果设置了，判断 projectDir 是否合法
      + 如果未设置，或者设置的文件夹非法
        + 判断 srcListFile 父路径下是否有合法的项目
        + 判断工程所在路径是否存在合法的项目
  + 解析 exportDir
    + 判断是否设置了 exportDir
      + 如果设置了，判断 exportDir 是否合法
        + 如果合法，判断路径下是否已经存在导出的内容，
          + 如果存在，先清除
      + 如果未设置，或者设置的文件夹非法，在工程路径下创建默认的导出文件夹
+ 读取文件 srcListFile 中的源码列表
  + 逐行读取 srcList
  + 过滤 srcList 中不合法的路径
    + 找不到的文件不处理
    + deleted 类型的文件不处理
+ 基于 srcList 根据路径匹配规则，从 target 文件夹中获取 class 文件路径
  + 注意类中存在静态文件的情况（eg：EnumUtil$WEEKLY.class）
+ 根据 target 文件路径，复制文件到 exportDir

## 入参

+ srcListFile 存放源码清单的文件
+ projectDir 项目路径
+ exportDir 导出路路径
+ serverProjectDir 服务器上项目目录

## srcListFile 源码列表存储样式

type | path
--- | ---
Modified | src/main/java/com/reading/controller/common/LibraryInterceptor.java
Added | src/main/java/com/reading/controller/library/LibraryCheckInDeviceController.java
Deleted | src/main/java/com/reading/data/mapping/ExaminationBatchRecordMapper.xml

Deleted 类的文件不需要处理
