- 1 SpringCould 与 Dubbo的区别
> - 1 通信机制：SpringCould采用Https，Dubbo采用RPC
- 2 微服务是什么？
- 3 微服务与微服务架构
> - 1 微服务：强调一个一个小模块、服务（个体）
> - 2 微服务架构：架构模式，提倡程序按照（业务）拆分，服务之间相互配合、协调，（整体）
- 4 微服务优缺点
> - 优点
> - 1 高内聚，低耦合
> - 2 开发简单、效率高
> - 3 独立
> - 4 易于与第三方集成
> - 5 **微服务只是业务逻辑代码，不会和HTML，CSS或其他界面组件混合**
> - 6 **每个微服务有自己的存储能力，可以有自己的数据库，也可以有统一数据库**
> - 缺点
> - 1 复杂
> - 2 运维难度
> - 3 部署依赖
> - 4 服务间通信成本
> - 5 数据一致性
> - 6 系统集成测试
> - 7 性能监控...
- 5 微服务技术栈（多种技术的集合体和落地维度）
> 服务治理、注册、调用、负载均衡、服务监控
- 6 SpringCloud REST微服务案例
> - 1 maven工程管理，父工程（root），子工程（公共模块API，消费者，提供者）
> - 2 maven 模块管理
> - 3 RestTemplate 使用方法（RestTemplate提供了多种便捷访问远程HTTP服务的方法，是一种简单便捷的访问restful服务类模板，是Spring提供的用于访问Rest服务的客户端模板工具集；<br>
restTemplate.getForObject(url, responseType);<br>
restTemplate.postForObject(url, request, responseType);
<br>String url：rest请求地址;<br>
Object request：请求参数<br>
Class<T> responseType：Http响应转换被转换成的对象类型
> - 4 消费者只消费，不提供服务，所以没有service层（暂时不太理解）