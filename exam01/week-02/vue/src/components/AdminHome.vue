<template>
  <div class="admin-container">
    <div class="admin-wrapper">
      <!-- 左侧菜单栏 -->
      <div class="sidebar">

        <div class="menu-list">
          <div
              v-for="item in menuItems"
              :key="item.id"
              class="menu-item"
              :class="{ active: activeMenu === item.id }"
              @click="activeMenu = item.id"
          >
            <span class="menu-icon">{{ item.icon }}</span>
            <span class="menu-title">{{ item.title }}</span>
          </div>
        </div>
      </div>

      <!-- 右侧内容区域 -->
      <div class="content-area">
        <div class="content-header">
          <h2>{{ currentMenu.title }}</h2>
          <p>{{ currentMenu.description }}</p>
        </div>

        <div class="content-body">
          <!-- 1. 查看所有报修单 -->
          <div v-if="activeMenu === 'viewOrders'" class="orders-panel">
            <!-- 筛选条件 -->
            <div class="filter-bar">
              <div class="filter-item">
                <label>状态筛选：</label>
                <select v-model="filterStatus">
                  <option value="">全部</option>
                  <option value="未处理">未处理</option>
                  <option value="已处理">已处理</option>
                </select>
              </div>
              <button class="search-btn" @click="filterOrders">筛选</button>
              <button class="reset-btn" @click="resetFilter">重置</button>
            </div>

            <!-- 报修单列表 -->
            <div class="orders-table">
              <div v-if="orders.length === 0" class="empty">暂无报修单</div>
              <table v-else class="data-table">
                <thead>
                <tr>
                  <th>单号</th>
                  <th>宿舍</th>
                  <th>学号</th>
                  <th>报修内容</th>
                  <th>状态</th>
                  <th>最后修改时间</th>
                  <th>评价</th>
                  <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="order in filteredOrders" :key="order.id">
                  <td>{{ order.id }}</td>
                  <td>{{ order.room }}</td>
                  <td>{{ order.number }}</td>
                  <td class="detail-text">{{ order.detail?.substring(0, 30) }}{{ order.detail?.length > 30 ? '...' : '' }}</td>
                  <td>{{ order.status }}</td>
                  <td>{{ order.update_time || order.last_time }}</td>
                  <td class="evaluation-text">{{ order.evaluation || '-' }}</td>
                  <td class="actions">
                    <button class="btn-update" @click="showUpdateStatus(order)">更新状态</button>
                    <button class="btn-delete" @click="confirmDelete(order)">删除</button>
                  </td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>



          <!-- 更新状态弹窗 -->
          <el-dialog v-model="statusDialogVisible" title="更新报修单状态" width="450px">
            <div class="status-form">
              <div class="form-row">
                <label>更新为：</label>
                <select v-model="newStatus">
                  <option value="未处理">未处理</option>
                  <option value="已处理">已处理</option>
                </select>
              </div>
            </div>
            <template #footer>
              <el-button @click="statusDialogVisible = false">取消</el-button>
              <el-button type="primary" @click="updateOrderStatus">确认更新</el-button>
            </template>
          </el-dialog>

          <!-- 修改密码 -->
          <div v-if="activeMenu === 'changePassword'" class="password-panel">
            <div class="form-group">
              <label>新密码：</label>
              <input type="password" v-model="passwordForm.newPassword" placeholder="请输入新密码" />
            </div>
            <div class="form-group">
              <label>确认密码：</label>
              <input type="password" v-model="passwordForm.confirmPassword" placeholder="请再次输入新密码" />
            </div>
            <button class="submit-btn" @click="changePassword">确认修改</button>
          </div>

          <!-- 退出登录 -->
          <div v-if="activeMenu === 'logout'" class="logout-panel">
            <div class="logout-icon">🚪</div>
            <h3>确定要退出登录吗？</h3>
            <p>退出后需要重新登录才能使用系统功能</p>
            <div class="logout-buttons">
              <button class="cancel-btn" @click="activeMenu = 'viewOrders'">取消</button>
              <button class="confirm-logout-btn" @click="handleLogout">确认退出</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ElMessage, ElMessageBox } from 'element-plus'
import request from "@/utils/request.js"

export default {
  name: 'AdminHome',
  data() {
    return {
      activeMenu: 'viewOrders',
      menuItems: [
        { id: 'viewOrders', title: '查看报修单', icon: '📋', description: '查看和管理所有报修单' },
        { id: 'changePassword', title: '修改密码', icon: '🔐', description: '修改管理员密码' },
        { id: 'logout', title: '退出登录', icon: '🚪', description: '退出当前账号' }
      ],

      // 报修单数据
      orders: [
        {
          id: '',
          room: '',
          number: '',
          detail: '',
          status: '',
          evaluation: '',
          last_time: '',
          update_time: ''
        },



      ],

      // 筛选
      filterStatus: '',
      filteredOrders: [],

      // 详情弹窗
      detailDialogVisible: false,
      currentOrder: null,

      // 更新状态
      statusDialogVisible: false,
      updateOrder: null,
      newStatus: '',

      // 修改密码
      passwordForm: {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      }
    }
  },
  computed: {
    currentMenu() {
      return this.menuItems.find(item => item.id === this.activeMenu) || this.menuItems[0]
    }
  },
  mounted() {
    request.get('/admins/allorder').then(res =>{
      this.orders = res.data || []
    })
    /*this.filterOrders()*/
  },
  methods: {
    // 筛选订单
    filterOrders() {
      if (!this.filterStatus) {
        this.filteredOrders = [...this.orders]
      } else {
        this.filteredOrders = this.orders.filter(order => order.status === this.filterStatus)
      }
    },

    // 重置筛选
    resetFilter() {
      this.filterStatus = ''
      this.filterOrders()
    },


    // 显示更新状态弹窗
    showUpdateStatus(order) {
      this.updateOrder = order
      this.newStatus = order.status
      this.statusDialogVisible = true
    },

    // 更新报修单状态
    updateOrderStatus() {
      let user=JSON.parse(localStorage.getItem('user') || '{}') ||{}
      if (!this.newStatus) {
        ElMessage.warning('请选择状态')
        return
      }

      // 如果状态没有变化
      if (this.newStatus === this.updateOrder.status) {
        ElMessage.warning('状态未发生变化')
        return
      }

      // 更新订单状态
      const index = this.orders.findIndex(o => o.id === this.updateOrder.id)
      if (index !== -1) {
        this.orders[index].status = this.newStatus
        const params = new URLSearchParams()
        params.append('status', this.newStatus)
        try {
          request.put('/admins/' + user.id + '/order/' + this.updateOrder.id,params ).then(res => {
            if (res.code === '200') {
              ElMessage.success('状态更新成功')
            } else {
              ElMessage.error(res.msg || '状态更新失败')
            }
          })
        } catch (e) {
          ElMessage.error('网络错误，请稍后重试')
        }

        ElMessage.success('状态更新成功')
        this.statusDialogVisible = false
        this.filterOrders()
      }
    },

    // 确认删除
    confirmDelete(order) {
      ElMessageBox.confirm(
          `确定要删除报修单 ${order.id} 吗？此操作不可恢复。`,
          '删除确认',
          {
            confirmButtonText: '确定删除',
            cancelButtonText: '取消',
            type: 'warning'
          }
      ).then(() => {
        this.deleteOrder(order.id)
      }).catch(() => {})
    },

    // 删除报修单
    deleteOrder(id) {
      let user=JSON.parse(localStorage.getItem('user') || '{}') ||{}
      const index = this.orders.findIndex(order => order.id === id)
      if (index !== -1) {
        this.orders.splice(index, 1)
        try {
          request.delete('/admins/' + user.id + '/order/' + id).then(res => {
            if (res.code === '200') {
              ElMessage.success('删除成功')
            } else {
              ElMessage.error(res.msg || '删除失败')
            }
          })
        } catch (e) {
          ElMessage.error('网络错误，请稍后重试')
        }
        this.filterOrders()
        ElMessage.success('删除成功')
      }
    },

    // 修改密码
    changePassword() {
      let user=JSON.parse(localStorage.getItem('user') || '{}') ||{}
      if (!this.passwordForm.newPassword) {
        ElMessage.warning('请输入新密码')
        return
      }
      if (this.passwordForm.newPassword !== this.passwordForm.confirmPassword) {
        ElMessage.warning('两次输入的密码不一致')
        return
      }
      const params = new URLSearchParams()
      params.append('password', this.passwordForm.newPassword || '')
      try {
        request.put( 'admins/'+user.id + '/password',params).then(response => {
          if (response.code === '200') {
            ElMessage.success('密码修改成功！')
          } else {
            ElMessage.error(response.msg || '密码修改失败')
          }
        })
      } catch (error) {
        ElMessage.error('网络错误，请稍后重试')
      }
    },

    // 退出登录
    handleLogout() {
      ElMessage.success('已退出登录')
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.admin-container {
  width: 100%;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

.admin-wrapper {
  display: flex;
  width: 100%;
  max-width: 1400px;
  margin: 0 auto;
  gap: 20px;
  min-height: calc(100vh - 40px);
}

/* 左侧菜单栏 */
.sidebar {
  width: 260px;
  flex-shrink: 0;
  background: white;
  border-radius: 16px;
  padding: 20px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.sidebar-header {
  text-align: center;
  padding-bottom: 20px;
  border-bottom: 2px solid #f0f0f0;
  margin-bottom: 20px;
}

.avatar {
  width: 70px;
  height: 70px;
  background: linear-gradient(135deg, #667eea, #764ba2);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28px;
  font-weight: bold;
  color: white;
  margin: 0 auto 12px;
}

.user-info .name {
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.user-info .role {
  font-size: 13px;
  color: #999;
}

.menu-list {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.menu-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 16px;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s;
  color: #666;
}

.menu-item:hover {
  background: #f8f9fa;
  color: #667eea;
}

.menu-item.active {
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
}

.menu-icon {
  font-size: 20px;
  width: 28px;
}

.menu-title {
  font-size: 15px;
  font-weight: 500;
}

/* 右侧内容区域 */
.content-area {
  flex: 1 1 auto;
  background: white;
  border-radius: 16px;
  padding: 60px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  overflow-x: auto;
}

.content-header {
  margin-bottom: 25px;
  padding-bottom: 15px;
  border-bottom: 1px solid #eee;
}

.content-header h2 {
  color: #333;
  margin-bottom: 5px;
}

.content-header p {
  color: #999;
  font-size: 14px;
}

/* 筛选栏 */
.filter-bar {
  display: flex;
  gap: 15px;
  margin-bottom: 20px;
  padding: 15px;
  background: #f8f9fa;
  border-radius: 8px;
  align-items: center;
}

.filter-item {
  display: flex;
  align-items: center;
  gap: 8px;
}

.filter-item label {
  color: #666;
  font-size: 14px;
}

.filter-item select {
  padding: 6px 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
}

.search-btn,
.reset-btn {
  padding: 6px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
}

.search-btn {
  background: #667eea;
  color: white;
}

.reset-btn {
  background: #f0f0f0;
  color: #666;
}

/* 表格样式 */
.orders-table {
  overflow-x: auto;
}

.data-table {
  width: 100%;
  border-collapse: collapse;
  min-width: 900px;
}

.data-table th,
.data-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #f0f0f0;
}

.data-table th {
  background: #f8f9fa;
  font-weight: 600;
  color: #333;
}

.data-table td {
  color: #666;
  cursor: pointer;
}

.data-table tr:hover {
  background: #fafafa;
}

.detail-text {
  max-width: 200px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.evaluation-text {
  max-width: 150px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.actions {
  display: flex;
  gap: 8px;
}

.actions button {
  cursor: pointer;
}

.btn-update,
.btn-delete {
  padding: 4px 12px;
  border: none;
  border-radius: 4px;
  font-size: 12px;
  cursor: pointer;
}

.btn-update {
  background: #e8f5e9;
  color: #67c23a;
}

.btn-delete {
  background: #fef0f0;
  color: #f56c6c;
}

.status-badge {
  display: inline-block;
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 500;
}

.status-badge.pending {
  background: #fff3e0;
  color: #e6a23c;
}

.status-badge.completed {
  background: #e8f5e9;
  color: #67c23a;
}

/* 详情弹窗 */
.order-detail {
  padding: 10px;
}

.detail-row {
  display: flex;
  margin-bottom: 15px;
}

.detail-row label {
  width: 100px;
  font-weight: 500;
  color: #333;
}

.detail-content {
  flex: 1;
  color: #666;
  line-height: 1.5;
}

/* 表单样式 */
.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
  color: #333;
}

.form-group input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
}

.status-form .form-row {
  margin-bottom: 15px;
}

.status-form select {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

/* 修改密码面板 */
.password-panel {
  max-width: 400px;
  margin: 0 auto;
}

.submit-btn {
  width: 100%;
  padding: 12px;
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
}

/* 退出登录面板 */
.logout-panel {
  text-align: center;
  padding: 40px;
}

.logout-icon {
  font-size: 80px;
  margin-bottom: 20px;
}

.logout-panel h3 {
  color: #333;
  margin-bottom: 10px;
}

.logout-panel p {
  color: #999;
  margin-bottom: 30px;
}

.logout-buttons {
  display: flex;
  gap: 15px;
  justify-content: center;
}

.cancel-btn,
.confirm-logout-btn {
  padding: 10px 30px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
}

.cancel-btn {
  background: #f5f5f5;
  color: #666;
}

.confirm-logout-btn {
  background: linear-gradient(135deg, #f56c6c, #f44336);
  color: white;
}

.empty {
  text-align: center;
  padding: 40px;
  color: #999;
}
</style>