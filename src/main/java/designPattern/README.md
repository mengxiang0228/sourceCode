# 设计模式

## 创造型模式

将对象的创建与使用分开

#### 抽象工厂模式

#### 工厂模式 Factory

#### 单例模式 singleton


#### 建造者模式

一个产品有多个属性，每个属性都有不同的实现，建造者模式就是将不同属性组装成一个产品

## 结构型模式
#### 代理模式
示例：AOP切面编程

#### 适配器模式 adapter

有三个重要角色
- 目标对象(Target)：要转换成的目标接口 
- 源角色(adaptee):需要被转换的源接口
- 适配器(adapter)： 核心是实现Target接口, 组合Adaptee接口

使用 targer实现adaptee的方法，用户只需要调用targer就能调用

#### 桥接模式 bridge

示例：JDBC设计模式

## 行为型模式

#### 策略模式 strategy

一个功能可以有多重算法实现，我们可以根据不同的特点选择不同的算法实现

示例：商城不同用户打折力度不同，出行选择不同的交通方式
