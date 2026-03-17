package Package.tool;

import Package.mapper.userMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Register {
    public Register(){
        // 加载mybatis配置文件 创建sqlSessionFactory
        String resource = "mybatis.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // 从sqlSessionFactory中获取sqlSession
        SqlSession sqls=sqlSessionFactory.openSession();
        userMapper um=sqls.getMapper(userMapper.class);

        Scanner sc=new Scanner(System.in);
        System.out.println("请选择角色（1-学生，2-维修人员）：");
        int role=sc.nextInt();
        while(role!=1&&role!=2){
            System.out.println("请输入正确的角色");
            role=sc.nextInt();//重新获取用户输入的角色
        }
        if(role==1){
            System.out.println("请输入学号（前缀3125或3225，共10位数字）：");
            String number=sc.next();
            while(!number.startsWith("3125")&&!number.startsWith("3225")&&number.matches("\\d{10}")||um.selectStudentByNumber(number)!=0){
                System.out.println("请输入正确的学号格式或该学号已被注册");
                System.out.println("请重新输入:");
                number=sc.next();//重新获取用户输入的学号
            }
            System.out.println("请输入密码（6位数字）：");
            String password=sc.next();
            while(!password.matches("\\d{6}")){
                System.out.println("请输入6位数字密码");
                password=sc.next();//重新获取用户输入的密码
            }
            System.out.println("请确认密码：");
            String confirmPassword=sc.next();
            while(!confirmPassword.equals(password)){
                System.out.println("两次密码不一致");
                System.out.println("请确认密码：");
                confirmPassword=sc.next();//重新获取用户输入的确认密码
            }
            um.registerStudent(number,password);
            sqls.commit();
            System.out.println("注册成功！请返回主界面登录。");
        }else{
            System.out.println("请输入维修人员学号（前缀为0025，共10位数字）：");
            String number=sc.next();
            while(!number.startsWith("0025")&&number.matches("\\d{10}")||
                    um.selectAdminByNumber(number)!=0){
                System.out.println("请输入正确的学号格式或该学号已被注册");
                System.out.println("请重新输入:");
                number=sc.next();//重新获取用户输入的学号
            }
            System.out.println("请输入密码（6位数字）：");
            String password=sc.next();
            while(!password.matches("\\d{6}")){
                System.out.println("请输入6位数字密码");
                password=sc.next();//重新获取用户输入的密码
            }
            System.out.println("请确认密码：");
            String confirmPassword=sc.next();
            while(!confirmPassword.equals(password)){
                System.out.println("两次密码不一致");
                System.out.println("请确认密码：");
                confirmPassword=sc.next();//重新获取用户输入的确认密码
            }
            um.registerAdmin(number,password);
            sqls.commit();
            System.out.println("注册成功！请返回主界面登录。");
        }

    }
}
