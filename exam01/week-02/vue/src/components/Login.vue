<template>
  <div class="login-container">
    <div class="login-card">
      <div class="login-header">
        <h2>学生登录系统</h2>
        <p>请使用学号和密码登录</p>
      </div>

      <el-form
          ref="loginFormRef"
          :model="loginForm"
          :rules="loginRules"
          label-width="80px"
          class="login-form"
      >
        <el-form-item label="学号" prop="studentId">
          <el-input
              v-model="loginForm.studentId"
              placeholder="请输入学号"
              prefix-icon="User"
              clearable
          />
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input
              v-model="loginForm.password"
              type="password"
              placeholder="请输入密码"
              prefix-icon="Lock"
              show-password
              clearable
          />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleLogin" :loading="loading" class="login-button">
            登录
          </el-button>
          <el-button @click="resetForm" class="reset-button">重置</el-button>
        </el-form-item>
      </el-form>

      <div class="login-footer">
        <p>默认测试账号：2024001 / 123456</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'

// 表单数据
const loginForm = reactive({
  studentId: '',
  password: ''
})

// 表单引用
const loginFormRef = ref(null)

// 登录状态
const loading = ref(false)

// 表单验证规则
const loginRules = reactive({
  studentId: [
    { required: true, message: '请输入学号', trigger: 'blur' },
    { pattern: /^\d{7,12}$/, message: '学号应为7-12位数字', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度应在6-20位之间', trigger: 'blur' }
  ]
})

// 登录处理
const handleLogin = async () => {
  if (!loginFormRef.value) return

  try {
    // 表单验证
    await loginFormRef.value.validate()

    loading.value = true

    // 模拟登录请求（实际项目中替换为真实API调用）
    await mockLoginAPI(loginForm.studentId, loginForm.password)

    ElMessage.success('登录成功！')

    // 这里可以添加登录成功后的路由跳转
    // router.push('/dashboard')

  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error(error.message || '登录失败，请检查学号或密码')
    }
  } finally {
    loading.value = false
  }
}

// 模拟登录API
const mockLoginAPI = (studentId, password) => {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      // 简单验证：学号以2024开头，密码为123456
      if (studentId.startsWith('2024') && password === '123456') {
        // 模拟存储用户信息
        const userInfo = {
          studentId: studentId,
          name: `学生_${studentId}`,
          loginTime: new Date().toISOString()
        }
        localStorage.setItem('userInfo', JSON.stringify(userInfo))
        resolve(userInfo)
      } else {
        reject(new Error('学号或密码错误'))
      }
    }, 800)
  })
}

// 重置表单
const resetForm = () => {
  if (loginFormRef.value) {
    loginFormRef.value.resetFields()
  }
  loginForm.studentId = ''
  loginForm.password = ''
  ElMessage.info('已重置表单')
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

.login-card {
  width: 100%;
  max-width: 450px;
  background: white;
  border-radius: 16px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  padding: 40px;
  transition: transform 0.3s ease;
}

.login-card:hover {
  transform: translateY(-5px);
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.login-header h2 {
  color: #333;
  font-size: 28px;
  margin-bottom: 8px;
}

.login-header p {
  color: #666;
  font-size: 14px;
}

.login-form {
  margin-top: 20px;
}

.login-button {
  width: 100%;
  margin-bottom: 12px;
  height: 44px;
  font-size: 16px;
}

.reset-button {
  width: 100%;
  height: 44px;
  font-size: 16px;
}

.login-footer {
  text-align: center;
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid #f0f0f0;
}

.login-footer p {
  color: #999;
  font-size: 12px;
  margin: 0;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .login-card {
    padding: 30px 20px;
  }

  .login-header h2 {
    font-size: 24px;
  }
}
</style>