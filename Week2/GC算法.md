# GC算法

![GC算法](.\GC算法.png)

串行GC时，回收老年代（代指tenured generation）堆内存的时间和年轻代（代指def new generation）的堆内存时间差不多.并行GC策略执行时，Full GC执行时间比Young GC时间短，增加堆内存的配置，Young GC执行次数少但回收数据量比较大，相应的GC暂停时间就会变大。同样配置最大堆内存的情况下，如果没有配置最小堆内存，使用默认的自适应模式，发生Young GC及Full GC频率比较多。CMS GC策略，发生CMS GC时，在初始化标记和最终标记时会发生GC暂停，其它阶段可以并发执行，中间伴随Young GC。G1 GC策略与CMS GC类似，只要在降低堆内存的配置，就可以看到Full GC。



## 串行GC

### 配置参数

- 开启串行GC

	- -XX +UseSerialGC

- 其他参数

	- -XX -USeParNewGC

		- ParNewGC是Serial GC的多线程并行版本，可以配合CMS使用

### GC算法

- 年轻代

	- mark-copy（标记 复制）

- 老年代

	- mark-sweep-compact(标记 清楚 整理)

### 特点

- 会导致STW，不能充分利用多核CPU，不管有多少CPU内核，JVM在垃圾收集时都只能使用单个核心。改GC只适合几百MB 堆内存的JVM，而且是单核CPU时比较有用

## 并行GC

### 配置参数

- 开启并行GC

	- -XX +UseParallelGC
	- -XX +UseParallelOldGC
	- -XX +UseParallelGC -XX +UseParallelOldGc

- 其他参数

	- -XX ParallelGCThreads =N

		- 用来指定GC线程数，其默认值位CPU核心数

### GC算法

- 年轻代

	- mark-copy（标记 复制）

- 老年代

	- mark-copy-compact(标记 清除 整理)

### 特点

- 会导致STW，并行GC适用于多核服务器，主要目标时增加吞吐量，因为对系统资源的有效使用，能达到更高的吞吐量，需要GC的时候所有线程一起用来GC，不需要GC的时候，不消耗任何系统资源，不一定减少GC暂停时间。

## G1

### 配置参数

- 开启G1 GC -XX +UseGC -xx：

	- MarkGCPauseMilis=S0：GC暂停时间

- 其他参数

	- -XX:G1NewSizePercent
	- -XX:G1MaxNewSizePercent
	- -XX:ConcGCThreads

		- 于java应用一起执行GC线程数量，默认是java线程的1/4

	- -XX:+InitiatingHeapOccupancyPercent
	- XX:+GCTimeRatio

		- 计算花在java应用线程上和花在GC线程上的时间比率，默认是9

	- -XX:G1HeapWastePercent

		- G1停止回收的最小内存大小，默认是堆大小的5%

	- -XX:MaxGCPauseMills

		- 预期G1每次执行GC操作的暂停时间，单位是毫秒，默认值是200毫秒，G1会尽量保证控制在这个范围内

### 特点

- 堆不再分成年轻代和老年代，而是划分位多个（同城是2048个）可以存放对象的小块堆区域
- G1不必每次都去收集每个堆空间，而是以增量的方式进行处理，每次只处理一部分内存块。
- GC暂停时间短
- 在并发阶段估算每个小堆块存活对象的总数。构建回收集的原则是：垃圾最多的小快会优先收集

## CMS GC

### 配置参数

- 开启CMS GC 

	- -XX +UseConcMarkSweepGC

### GC算法

- 年轻代

	- 并行STW方式的mark-copy(标记 复制)算法

- 老年代

	- 并发mark-sweep（标记 清除）算法

### 特点

- 不对老年代进行整理，而是使用空闲列表（free-lists）来管理内存空间的回收
- 在mark-and-sweep（标记 清除）阶段的大部分工作和应用线程一起并发执行，默认情况下，CMS使用的并发线程数等于CPU核心数的1/4
- GC暂停时间短
- CMS GC最大的问题就是老年代内存碎片问题（因为没有压缩），在某些情况下GC会造成不可预测的暂停时间，特别时堆内存较大的情况下

*XMind - Trial Version*