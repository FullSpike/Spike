// src/api/user.js
import request from '@/utils/request'

/**
 * 用户登录
 * @param {Object} data - 登录信息
 * @param {string} data.username - 用户名
 * @param {string} data.password - 密码
 * @returns {Promise} 返回登录结果
 */
export function login(data) {
    return request({
        url: '/login',
        method: 'get',
        params: data
    })
}

/**
 * 用户登出
 */
export function logout() {
    return request({
        url: '/api/user/logout',
        method: 'post'
    })
}

/**
 * 获取用户信息
 */
export function getUserInfo() {
    return request({
        url: '/api/user/info',
        method: 'get'
    })
}