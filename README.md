# 微服务Demo项目
> Spring Cloud Alibaba 微服务项目，该项目使用简单的前端实现，未使用前后端分离，旨在构建springCloud各组件学习的基础服务。
> 访问 http://localhost:8080/order 可实现简单下单.
> 
> 项目技术介绍:<br>
> (1)项目采用springCloud Alibaba体系实现微服务,使用nacos实现服务注册发现以及配置中心.
> (2)引入SpringCloud gateway作为整个微服务集群的统一入口

## 📌 技术栈
| 组件 | 版本           |
|---|--------------|
| JDK | 1.8          |
| SpringBoot | 2.7.18       |
| Spring Cloud | 2021.0.5     |
| Spring Cloud Alibaba | 2021.0.5.0   |
| Nacos | 注册中心 + 配置中心  |
| 构建工具 | Maven 3.9.12 |

## 📂 项目模块结构
```
project-root
├── 00-init # 项目启动和配置所需要的文件
├── account-service # 账号服务
├── common # 公共依赖模块
├── frontend-service # 前端调试服务
├── gateway-service # 网关服务
├── order-service # 订单服务
├── storage-service # 库存服务
├── pom.xml # 父工程 pom
```
## 🚀 项目启动步骤
> 前置环境要求
> JDK 8，Maven 3.9.12，MySQL9.5.0，Nacos 2.3.2
1. **初始化数据库并启动mysql服务**<br>
   -创建数据库 sp_account_service、sp_order_service、sp_storage_service<br>
   -进入 `00-init` 文件夹中`sql`目录，依次执行对应数据库脚本，初始化基础数据表和数据

2. **启动Nacos**<br>
   -启动本地Nacos服务（localhost:8848），打开Nacos控制台，创建命名空间(如果不需要的话,可以不用创建,记得删除nacos配置中的nacos.cnofig.namespace)。<br>
   -进入 `00-init` 文件夹中`nacos`目录，在Nacos控制台中导入配置,直接选中压缩包即可,无需解压

3. **启动Redis服务**<br>

4. **修改各个配置文件**<br>
 -mysql配置<br>
 -redis配置<br>
 -nacos配置<br>
 -修改host文件:
```
127.0.0.1 frontend-service
127.0.0.1 order-service
127.0.0.1 account-service
127.0.0.1 storage-service
127.0.0.1 gateway-service

127.0.0.1 mysql-service
127.0.0.1 nacos-server
```

5. **启动微服务模块**
```
1. account-service 账号服务
2. storage-service 库存服务
3. order-service 订单服务
4. gateway-service 网关服务
5. frontend-service 前端调试服务

common为公共依赖模块，不需要单独启动，仅提供公共实体、工具类。
```


4. **访问项目**
   访问地址：
   `http://localhost:8080/order` 触发下单接口，验证整个微服务流程。
   由订单服务访问账号服务和库存服务

5. **验证服务注册**
   打开 Nacos 控制台，查看服务列表：account-service、storage-service、order-service、gateway-service、frontend-service 全部注册成功，代表服务启动正常。