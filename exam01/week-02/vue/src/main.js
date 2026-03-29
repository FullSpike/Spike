import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router'  // 引入路由

const app = createApp(App)
app.use(ElementPlus)
app.use(router)
app.mount('#app')


// 在 main.js 中添加，过滤特定错误
const originalConsoleError = console.error
console.error = function(...args) {
    if (args[0] && args[0].includes && args[0].includes('Could not establish connection')) {
        return
    }
    originalConsoleError.apply(console, args)
}