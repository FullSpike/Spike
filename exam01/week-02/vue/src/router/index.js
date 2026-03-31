import { createRouter, createWebHistory } from 'vue-router'
import Test from "@/components/test.vue";
import StudentHome from "@/components/StudentHome.vue";
import AdminHome from "@/components/AdminHome.vue";
import Register from "@/components/Register.vue";


// 定义路由
const routes = [
    {
        path: '/',
        redirect: '/login'
    },
    {
        path: '/login',
        name: 'Login',
        component: Test,
        meta: {requiresAuth: false}
    },
    {
        path: '/StudentHome',
        name: 'StudentHome',
        component: StudentHome,
        meta: {requiresAuth: true}
    },
    {
        path: '/AdminHome',
        name: 'AdminHome',
        component: AdminHome,
        meta: {requiresAuth: true}
    },
    {
        path: '/Register',
        name: 'Register',
        component: Register,
        meta: {requiresAuth: false}
    }
]

// 创建路由实例
const router = createRouter({
    history: createWebHistory(),
    routes
})

/*
router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token')

    // 需要登录的页面
    if (to.meta.requiresAuth) {
        if (!token) {
            // 未登录，跳转到登录页
            next({
                path: '/login',
                query: { redirect: to.fullPath }
            })
        } else {
            // 已登录，可以访问
            next()
        }
    }
})
*/



export default router