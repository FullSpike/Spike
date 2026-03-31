<template>
  <div style="padding: 20px; max-width: 500px; margin: 0 auto;">
    <h2>学生/管理员登录系统</h2>
    <el-form>
      <el-form-item label="学号">
        <el-input placeholder="请输入学号" v-model="number" />
      </el-form-item>
      <el-form-item label="密码">
        <el-input type="password" placeholder="请输入密码" v-model="password" show-password />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="handleLogin">登录</el-button>
        <el-button type="info" @click="$router.push('/Register')">注册</el-button>
      </el-form-item>
    </el-form>
    <p>默认测试账号：3125000000 / AB123456</p>
  </div>
</template>

<script>
import { ElMessage } from 'element-plus'
import axios from 'axios'



export default {
  name: 'Login',

  data() {
    return {
      number: '',
      password: '',
      loading: false,
      errorMsg: ''
    }
  },
  methods: {

    async handleLogin() {
      // 简单验证
      if (!this.number || !this.password) {
        this.errorMsg = '请输入学号和密码'
        ElMessage.error(this.errorMsg)
        return
      }

      this.loading = true
      this.errorMsg = ''

      try {
        /*// 调用登录接口
        const response = await fetch('http://localhost:8081/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            number: this.number,
            password: this.password
          })
        })*/
        // 调用登录接口
        //使用 URLSearchParams 发送表单数据
        const params = new URLSearchParams()
        params.append('number', this.number)
        params.append('password', this.password)

        const response = await axios.post('http://localhost:8081/login', params, {
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          }
        })
        if (response.data.code === "200") {
          // 保存token
          localStorage.setItem('token', response.data.data.token)
          localStorage.setItem('user', JSON.stringify(response.data.data))
          ElMessage.success('登录成功！')
          // 跳转到首页
          if(response.data.data.number.startsWith('3')) {
            this.$router.push('/StudentHome')
          }else{
            this.$router.push('/AdminHome')
          }
        } else {
          this.errorMsg = response.data.msg || '登录失败'
        }
      } catch (error) {
        this.errorMsg = '网络错误，请稍后重试'
      } finally {
        this.loading = false
      }
    }
  }
}






/*const handleLogin = () => {
  if (studentId.value === '2024001' && password.value === '123456') {
    ElMessage.success('登录成功！')
    // 登录成功后，跳转到欢迎页面

  } else {
    ElMessage.error('学号或密码错误')
  }
}*/



</script>

<style>
  body {
    background: linear-gradient(
        rgb(113, 65, 168),
        rgba(0, 123, 255, 0.5)
    );
  }

</style>
