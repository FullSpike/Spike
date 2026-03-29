import axios from 'axios'
import router from "@/router/index.js";

const request = axios.create({
    baseURL: 'http://localhost:8081',
    timeout: 30000,
})



request.interceptors.request.use(function (config) {
    config.headers['content-type'] = 'application/x-www-form-urlencoded;charset=UTF-8'

    let user=localStorage.getItem('user')?JSON.parse(localStorage.getItem('user')):null
    // 从localStorage中获取token
    config.headers['Authorization'] = user?.token
    return config
},error => {
    return Promise.reject(error)
})

request.interceptors.response.use(function (response) {
    let res = response.data

    if(typeof res === 'string') {
        res = res?JSON.parse(res) : res
    }
    return res
}, error => {
    router.push('/login')
    return Promise.reject(error)
})

export default request;