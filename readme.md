# 项目概述

## 背景

增量更新代码时，需要通过 svn 或者 git 把需要更新的代码清单整理出来，然后根据这个清单，把对应的编译后的文件，根据路径上传到服务器上指定项目目录下。

## 基本原理

根据 srcFileList 文件中获取由相对路径描述的源码清单，从指定的项目路径 projectDir 中复制 target 目录下的编译文件到指定的 exportDir 路径中（如果定义了 prefixDir，则创建该路径，结果放到该路径中）

+ srcFileList 存放源码清单的文件
+ projectDir 项目路径
+ exportDir 导出路路径
+ prefixDir 前缀路径，服务器上项目目录