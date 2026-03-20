# 周记week-01

### 关于maven
    maven是Java 项目最流行的项目管理和构建自动化工具，它通过标准化的项目结构（约定优于配置）和强大的依赖管理，
    大幅简化了 Java 项目的编译、测试、打包和部署流程，其中包含以下重要内容
       pom.xml 是 Maven 项目的核心配置文件，定义项目坐标、依赖、插件等
       坐标 (GAV)是 GroupId（组织标识）+ ArtifactId（项目名）+ Version（版本），唯一标识一个项目或依赖
       依赖管理是自动从中央仓库或镜像仓库下载 JAR 包，解决传递依赖和版本冲突
    创建maven项目后，要对pom核心配置文件设置依赖和其他信息
### 关于mybatis
    mabatis是较为流行的持久层框架。被大规模应用与项目开发，其中
        在resource中创建myabtis配置文件中设置核心信息包括驱动，url，数据库用户名和密码，mapper配置文件位置
        mapper配置文件用于映射sql语句，要定义一个接口，其配置文件命名空间和mapper名字相同。通常mapper取名为xxxMapper
        这种通过mapper代理的形式可以解决硬编码的问题
    导入依赖便可使用框架
### 三层框架
    分别为dao，service，control，目前对其不熟悉，基本是按照周记的想法搭建框架，很不规范
    目前没用spring框架，没有依赖注入，service和mapper都是new出来的（
        
    
