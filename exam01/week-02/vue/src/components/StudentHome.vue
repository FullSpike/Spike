<template>
  <div class="home">
    <!-- 左侧菜单 -->
    <div class="left-menu">


      <div class="menu-items">
        <div
            v-for="item in menuList"
            :key="item.id"
            class="menu-item"
            :class="{ active: currentMenu === item.id }"
            @click="currentMenu = item.id"
        >
          <span class="icon">{{ item.icon }}</span>
          <span class="text">{{ item.name }}</span>
        </div>
      </div>
    </div>

    <!-- 右侧内容 -->
    <div class="right-content">
      <div class="content-title">
        <h2>{{ getCurrentMenu.name }}</h2>
        <p>{{ getCurrentMenu.desc }}</p>
      </div>

      <div class="content-box">
        <!-- 1. 绑定宿舍 -->
        <div v-if="currentMenu === 'bind'" style="padding: 100px; max-width: 600px; margin: 0 auto;">

          <div class="form-row">
            <label>房间号：</label>
            <input type="text" v-model="room" placeholder="请输入房间号">
          </div>
          <div class="form-row">
            <label>当前宿舍：</label>
            <input type="text" :value="currentRoom" disabled>
          </div>
          <button class="btn-submit" @click="handleBind">确认绑定</button>
        </div>

        <!-- 2. 创建报修 -->
        <div v-if="currentMenu === 'repair'">

          <div class="form-row">
            <label>故障描述：</label>
            <textarea v-model="repairData.desc" rows="4" placeholder="请描述故障情况"></textarea>
          </div>
          <div class="form-row">
            <label>联系电话：</label>
            <input type="text" v-model="repairData.phone" placeholder="请输入电话">
          </div>
          <button class="btn-submit">提交报修</button>
        </div>

        <!-- 3. 查看记录 -->
        <div v-if="currentMenu === 'records'">
          <table class="record-table">
            <thead>
            <tr>
              <th>单号</th>
              <th>类型</th>
              <th>描述</th>
              <th>状态</th>
              <th>时间</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="item in recordList" :key="item.id">
              <td>{{ item.id }}</td>
              <td>{{ item.type }}</td>
              <td>{{ item.desc }}</td>
              <td>
                <span :class="'status-' + item.status">{{ getStatus(item.status) }}</span>
              </td>
              <td>{{ item.time }}</td>
            </tr>
            </tbody>
          </table>
        </div>

        <!-- 4. 取消报修 -->
        <div v-if="currentMenu === 'cancel'">
          <div class="form-row">
            <label>选择报修单：</label>
            <select v-model="cancelData.id">
              <option value="">请选择</option>
              <option v-for="item in pendingList" :key="item.id" :value="item.id">
                {{ item.id }} - {{ item.type }}
              </option>
            </select>
          </div>
          <div class="form-row">
            <label>取消原因：</label>
            <textarea v-model="cancelData.reason" rows="3" placeholder="请输入取消原因"></textarea>
          </div>
          <button class="btn-submit btn-danger">确认取消</button>
        </div>

        <!-- 5. 修改密码 -->
        <div v-if="currentMenu === 'password'">
          <div class="form-row">
            <label>原密码：</label>
            <input type="password" v-model="pwdData.old" placeholder="请输入原密码">
          </div>
          <div class="form-row">
            <label>新密码：</label>
            <input type="password" v-model="pwdData.new" placeholder="请输入新密码">
          </div>
          <div class="form-row">
            <label>确认密码：</label>
            <input type="password" v-model="pwdData.confirm" placeholder="请再次输入">
          </div>
          <button class="btn-submit">确认修改</button>
        </div>

        <!-- 6. 退出登录 -->
        <div v-if="currentMenu === 'logout'" class="logout-box">
          <div class="logout-icon">🚪</div>
          <h3>确定要退出登录吗？</h3>
          <div class="logout-btns">
            <button class="btn-cancel">取消</button>
            <button class="btn-confirm">确认退出</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ElMessage } from 'element-plus'
import axios from "axios";
import request from '@/utils/request'

export default {
  name: 'StudentHome',
  data() {
    return {
      currentMenu: 'bind',
      menuList: [
        { id: 'bind', name: '绑定/修改宿舍', icon: '🏠', desc: '绑定或修改宿舍信息' },
        { id: 'repair', name: '创建报修单', icon: '🔧', desc: '提交新的报修申请' },
        { id: 'records', name: '查看报修记录', icon: '📋', desc: '查看历报修记录' },
        { id: 'cancel', name: '取消报修单', icon: '❌', desc: '取消未处理的报修' },
        { id: 'password', name: '修改密码', icon: '🔐', desc: '修改登录密码' },
        { id: 'logout', name: '退出登录', icon: '🚪', desc: '退出当前账号' }
      ],
      room: '',
      currentRoom: '',
      repairData: { type: '', desc: '', phone: '' },
      cancelData: { id: '', reason: '' },
      pwdData: { old: '', new: '', confirm: '' },
      recordList: [
        { id: 'R001', type: '电路维修', desc: '灯不亮了', status: 'pending', time: '2024-01-15' },
        { id: 'R002', type: '水管维修', desc: '水龙头漏水', status: 'processing', time: '2024-01-14' }
      ]
    }
  },
  computed: {
    getCurrentMenu() {
      return this.menuList.find(m => m.id === this.currentMenu) || this.menuList[0]
    },
    pendingList() {
      return this.recordList.filter(r => r.status === 'pending')
    }
  },
  methods: {
    getStatus(status) {
      const map = { pending: '待处理', processing: '处理中', completed: '已完成' }
      return map[status] || status
    },

    async handleBind() {
      let user=JSON.parse(localStorage.getItem('user') || '{}')
      if (!this.room) {
        ElMessage.error('请输入房间号')
        return
      }
      const params = new URLSearchParams()
      params.append('room', this.room)
      try {
          request.put('/students/'+user.id+'/room', params).then(res => {
          if (res.code === '200') {
            ElMessage.success('绑定成功！')
            this.currentRoom = this.room
          } else {
            ElMessage.error(res.msg || '绑定失败')
          }
        })
      } catch (error) {
        ElMessage.error('网络错误，请稍后重试')
      }
    }
  }
}
</script>

<style scoped>
/* 基础重置 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

/* 主容器 - 使用flex水平布局 */
.home {
  display: flex;
  width: 100%;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

/* 左侧菜单 - 固定宽度 */
.left-menu {
  flex: 1 1 auto;
  width: 260px;
  background: white;
  border-radius: 12px;
  padding: 20px;
  margin-right: 20px;
}


/* 菜单项 */
.menu-items {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.menu-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 15px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s;
  color: #666;
}

.menu-item:hover {
  background: #f5f5f5;
}

.menu-item.active {
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
}

.menu-item .icon {
  font-size: 20px;
  width: 28px;
}

.menu-item .text {
  font-size: 14px;
}

/* 右侧内容 - 自适应宽度 */
.right-content {
  flex: 1 1 auto;
  background: white;
  border-radius: 12px;
  padding: 100px;
}

.content-title {
  margin-bottom: 25px;
  padding-bottom: 15px;
  border-bottom: 1px solid #eee;
}

.content-title h2 {
  color: #333;
  margin-bottom: 5px;
}

.content-title p {
  color: #999;
  font-size: 14px;
}

.content-box {
  max-width: 500px;
}

/* 表单样式 */
.form-row {
  margin-bottom: 18px;
}

.form-row label {
  display: block;
  margin-bottom: 6px;
  font-weight: 500;
  color: #333;
  font-size: 14px;
}

.form-row input,
.form-row select,
.form-row textarea {
  width: 100%;
  padding: 10px 12px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 14px;
  font-family: inherit;
}

.form-row input:focus,
.form-row select:focus,
.form-row textarea:focus {
  outline: none;
  border-color: #667eea;
}

.form-row input:disabled {
  background: #f5f5f5;
  color: #999;
}

.btn-submit {
  width: 100%;
  padding: 12px;
  background: linear-gradient(135deg, #667eea, #764ba2);
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 16px;
  cursor: pointer;
  margin-top: 10px;
}

.btn-submit:hover {
  opacity: 0.9;
}

.btn-danger {
  background: linear-gradient(135deg, #f56c6c, #f44336);
}

/* 表格样式 */
.record-table {
  width: 100%;
  border-collapse: collapse;
  min-width: 500px;
}

.record-table th,
.record-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #eee;
}

.record-table th {
  background: #f8f9fa;
  font-weight: 600;
  color: #333;
}

.record-table td {
  color: #666;
}

.status-pending {
  display: inline-block;
  padding: 4px 12px;
  background: #fff3e0;
  color: #e6a23c;
  border-radius: 20px;
  font-size: 12px;
}

.status-processing {
  display: inline-block;
  padding: 4px 12px;
  background: #e8f4ff;
  color: #409eff;
  border-radius: 20px;
  font-size: 12px;
}

/* 退出登录样式 */
.logout-box {
  text-align: center;
  padding: 30px;
}

.logout-icon {
  font-size: 70px;
  margin-bottom: 20px;
}

.logout-box h3 {
  margin-bottom: 25px;
  color: #333;
}

.logout-btns {
  display: flex;
  gap: 15px;
  justify-content: center;
}

.btn-cancel,
.btn-confirm {
  padding: 10px 30px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
}

.btn-cancel {
  background: #f5f5f5;
  color: #666;
}

.btn-confirm {
  background: linear-gradient(135deg, #f56c6c, #f44336);
  color: white;
}

/* 响应式 */
@media (max-width: 700px) {
  .home {
    flex-direction: column;
  }

  .left-menu {
    width: 100%;
    margin-right: 0;
    margin-bottom: 20px;
  }

  .menu-items {
    flex-direction: row;
    flex-wrap: wrap;
  }

  .menu-item {
    flex: 1;
    justify-content: center;
  }
}
</style>