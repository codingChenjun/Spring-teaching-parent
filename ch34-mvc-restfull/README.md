# 分布式软件
一个完整软件的各个部分是独立开发,部署的
分布式可以理解为像搭积木一样
## 单体架构的软件(standalone)
所有的模块都放在一起,部署在一个服务器里面
缺陷:
1.有些功能不能重用

## SOA
Service Orient Architecture(面向服务的结构)

意思就是把软件组件服务化
## Restful与Web Services
restful与webservice是SOA架构思想的一个具体的实现细节的规范

WebService: 主要是利用soap协议进行通信

## 集群与分布式区别

## 小结
1. 软件尽量的高可用,成本低,响应快
2. 要高可用就一定要集群:
    2.1 集群解决了高可用
    2.2 集群没有解决资源浪费
3. SOA:核心思想是组件可以重用.
4. SOA主要有WebService与Restful
5. 微服务(2014)


# Restful最佳实践要点
1. uri地址尽量用名词
2. 尽量用复数
3. 段不要太多,可以用查询字符串处理,比如:/users?pagenum=1&pagesize=2
4. 名字实在有很多单词时用连字符分隔,比如:/a-b-c
5. url地址都用小写字母

# swagger使用步骤
1. 添加依赖
2. 创建配置类
3. 使用配置类,如果有父子容器,尽量导入到子容器
4. 处理swagger用到的静态资源
5. 在各个控制器及其方法上添加swagger的相关注解来描述你的api
6. swagger-ui.html地址就可以看到api文档了

# 坑点:
当你的ajax像下面这样写:
```js
$.ajax({
    url:"http://127.0.0.1:8080/users/1",
    type:"PUT",
    data:{id:1,username:"yyyy"},
    success:function(result){
        alert(result.code);
    }
    
});
```
后端像下面这样写:
```java
@PutMapping("/users/{id}")
    public  ResponseDTO updateById(@PathVariable int id, UserInfoDTO userInfoDTO){

    }
```
那么发起ajax请求的时候,UserInfoDTO是收不到数据的,主要是username属性没有数据,id是有的,因为id的
值可以从url地址上获取.

## 原因如下
在Servlet规范中(https://www.ietf.org/rfc/rfc2616.txt)
,表单数据只对POST提交有效,对于PUT,DELETE,
patch(见案例中test包下的servlet类)是无效的(这句话在spring的HttpPutFormContentFilter类的注释上有说明),
也就是说,当采用表单提交方式时(contentType为application/x-www-form-urlencoded),
放置在请求体中的数据servlet容器如果发现是非POST提交,就不进行处理,你就无法通过getParameter的形式获取数据,
这样spring mvc就无法对参数进行绑定,所以就获取不到值

## 解决办法
解决的思路是利用过滤器,获取表单数据并进行数据读取,之后封装HttpServletRequest对象,
以便即使是PUT,DELETE等请求时,也可以通过getParameter方法获取到数据
spring主要提供了下面过滤器来处理

1. FormContentFilter,此过滤器主要是用来
取代HttpPutFormContentFilter(这个过滤器不支持delete请求)用的.


> 注意:HiddenHttpMethodFilter过滤器主要是解析传递过来的真正请求方法,改变getMethod的值
> 主要解决的是浏览器只支持get,post请求的情况

办法二(建议):
1. 不添加任何的过滤器
2. 请求数据直接用json文本传递过来
3. 后端的方法参数加@RequestBody注解

# 前端代码如下:
```js
$("#btnInsert").click(function(){
				
    $.ajax({
        url:"http://127.0.0.1:8080/users",
        type:"POST",
        data:{id:1,username:"yyyy"},
        success:function(result){
            alert(result.code);
        }
        
    });
});
			
$("#btnUpdate").click(function(){
    
    $.ajax({
        url:"http://127.0.0.1:8080/users/1",
        type:"PUT",
        contentType:"application/json",
        data:JSON.stringify({id:1,username:"yyyy"}),
        success:function(result){
            alert(result.code);
        }
        
    });
});


$("#btnDelete").click(function(){
    
    $.ajax({
        url:"http://127.0.0.1:8080/users/1",
        type:"DELETE",
        success:function(result){
            alert(result.code);
        }
        
    });
});
```

# 参考资料
https://www.ibm.com/developerworks/cn/java/j-lo-SpringHATEOAS/index.html(rest级别)
https://www.ruanyifeng.com/blog/2011/09/restful.html
http://www.ruanyifeng.com/blog/2018/10/restful-api-best-practices.html
https://www.ibm.com/developerworks/cn/websphere/library/techarticles/1601_clark-trs/1601_clark.html(soa与微服务)
https://www.zhihu.com/question/20004877(分布式与集群的区别)
