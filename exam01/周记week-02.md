# 周记week-02

### 文件储存
         没有用oss对象存储，直接用本地文件存储
        这是因为oss收费，以后做项目时，需要考虑是否用oss对象存储
        而这是一个简单的项目，所以直接用本地文件存储
        我自己有认真学习过oss过，但之前已经用本地文件存储了
        想再改不知道能不能成功运行，故不在用oss
        再通过本地的文件映射到http://localhost:8081/order/让前端访问

### 后端内容框架
        用springboot框架搭建后端内容，用mybatis框架搭建数据库操作
        再想着给后端添加jwt认证，首先有个生成token的util类，放在utils包下，用于生成token，token过期时间为1小时
        然后有个拦截器，放在interceptor包下，用于拦截请求，判断是否有token，是否有过期，是否有权限等
        同时还需要再这之中判断解析出来的用户id是否与请求中的用户id一致，若不一致，返回403错误码
        最后必须放行一些请求，如登录请求，注册请求等，否则会报错，所以再增加一个配置config类，用于配置放行的请求
        其中还有用于映射配置资源，就是图片的存储问题，放在resource目录下，图片的存储路径为/order/，前端通过http://localhost:8081/order/访问
        
        而关于异常处理，我用的是全局异常处理，放在exception包下，用于处理所有异常，返回json数据
        并且考虑到前端发送的内容需要是正确格式，再后端我需要进行最后的校验，若校验失败，返回400错误码
        我创建了一个serviceException类，用于处理后端抛出的异常，在全局异常处理中捕获并返回json数据

        而返回给前端的格式为json，包含code，data，msg三个字段
        code为状态码，data为数据，msg为错误信息
        其中code为200表示成功，400表示请求参数错误，403表示权限不足，500表示服务器错误

        代码采用三层架构，分别为mapper，service，controller
        其中mapper负责数据库操作，service负责业务逻辑，controller负责处理请求和返回响应


        并且我的前后端是放置在同一个项目下的，这方便了这个小小型项目的开发

### 前端内容框架
        用element-plus框架搭建前端内容，每一个组件大体框架有ai生成，我加以修改代码和增加与后端交互的功能
        以及后端api有采用restful风格，前端通过axios发送请求，后端返回json数据
        具体后端api有
        /students/{id}/order/{id}
        /students/{id}/order/{id}
        /students/{id}/order/{id}
        /students/{id}/order

        我需要在前端实现网页的跳转，就需要使用vue-router框架
        在app.vue中引入vue-router框架，配置路由规则
        同时在index.js(在router目录下)配置路由规则，将不同的组件映射到不同的路由
        其中每个路由都有一个组件，用于渲染对应的页面
        
        而拦截方面，我用的是axios的拦截器，用于拦截请求，判断是否有token，是否有过期，是否有权限等
        在request.js中配置，创建一个axios实例，用于发送请求，同时配置请求头，添加token
        这样每次发送请求时，都会自动添加token到请求头中，同时需要都后端的返回的数据中进行后续处理
        例如let res = response.data让接受数据的组件直接使用res.data
        如果返回类型是string，需要先解析为json数据,在进行发送，如果出现错误，利用router跳转到登录页

        关于前端样式的展现，每一个组件大体框架有ai生成

        而在这过程中，遇到过很多问题，例如报错Cannot read properties of undefined (reading 'map')
        这是我每次导入params时，没有正确使用，导致报错，并且前端界面只有背景色，没有内容
        组件内容穿插，叠加，导致前端界面布局混乱
        无法访问后端api，导致前端无法正常运行
        出现两次成功登录的弹窗
        无法push到其他页面等，大多数都靠我自己琢磨和ai帮助完成了修改
