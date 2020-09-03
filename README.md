# Warden简介
Warden专注于对象的属性核查框架，致力于简化参数校验流程从而简化开发。
#warden vs 主流的hibernate.validate
# 性能
相较而言warden更轻量级，代码风格是领域驱动，测试模块覆盖100%代码，性能优秀，一个类只需加载一次可重复使用。

# 线程安全
底层数据结构采用concurentHashmap和ThreadLocal
## 功能性：
功能更为丰富
- 全类型：可以核查所有类型，基本类型，复杂类型，集合和Map等各种有固定属性（泛型暂时不支持）的类型
