# mock-core
# 介绍

待业空窗期emmmmm......

mock核心服务，使用与Java匹配的groovy脚本方式达到动态模拟返回数据的场景

该代码库为mock数据处理返回服务，

业务配置请求该服务地址并且在平台上配置了对应接口，服务在请求接口时则会根据配置类型做对应处理，目前有返回固定内容、根据groovy脚本匹配指定值返回不同内容

#快速开始
1、拉取代码  

        git pull

2、修改resource中application.properties配置

    数据库/redis连接配置修改
    log4j2.xml文件baseDir路径修改

3、编译产出 mvn clean package

4、启动服务
    
    nohup java -jar mock-core-0.0.1-SNAPSHOT.jar >/dev/null 2>/dev/null &
    启动后日志会在log4j2.xml指定的路径下生成日志目录

后续期望

    1、接入注册中心，使在微服务架构中能直接使用
    2、在未配置接口或接口mock关闭的情况下降级请求原服务，避免需要配置业务服务所有接口的情况


