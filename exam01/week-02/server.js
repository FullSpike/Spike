const express = require('express')
const cors = require('cors')
const app = express()

app.use(cors())
app.use(express.json())
app.use(express.urlencoded({ extended: true }))  // 解析表单数据
/*// 测试接口
app.get('/test', (req, res) => {
    res.json({ message: '后端服务运行正常！' })
})*/

// 登录接口
app.post('/login', (req, res) => {
    console.log('收到登录请求:', req.body)

    const { number, password } = req.body

    if (number === '3125000000' && password === 'AB261216') {
        res.json({
            code: '200',
            msg: 'success',
            data: {
                number: number,
                room: '744',
                token: 'test-token-123'
            }
        })
    } else {
        res.json({
            code: '401',
            msg: '学号或密码错误'
        })
    }
})

const PORT = 8081
app.listen(PORT, () => {
    console.log('=' .repeat(50))
    console.log(`✅ 后端服务运行在 http://localhost:${PORT}`)
    console.log(`测试账号: 3125000000 / AB261216`)
    console.log('=' .repeat(50))
})