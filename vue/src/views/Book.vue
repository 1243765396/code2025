<template>
  <div>

    <div class="card" style="margin-bottom: 5px">
      <el-input clearable @clear="load" style="width: 240px; margin-right: 5px" v-model="data.name" placeholder="请输入标题查询" :prefix-icon="Search"></el-input>
      <el-button type="primary" @click="load">查 询</el-button>
      <el-button @click="reset">重 置</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px" v-if="data.user.role === 'ADMIN'">
      <el-button type="primary" @click="handleAdd">新增</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <el-table :data="data.tableData" style="width: 100%"
                :header-cell-style="{ color: '#333', backgroundColor: '#56e0fc'}">
        <el-table-column label="图书封面">
          <template #default="scope">
            <el-image v-if="scope.row.img" :src="scope.row.img" :preview-src-list="[scope.row.img]" :preview-teleported="true" style="width: 40px; height: 40px; border-radius: 5px; display: block" />
          </template>
        </el-table-column>
        <el-table-column prop="name" label="图书名字" />
        <el-table-column prop="price" label="图书价格" />
        <el-table-column prop="author" label="图书作者" />
        <el-table-column prop="num" label="剩余数量" />
        <el-table-column label="操作" width="100px">
          <template #default="scope" v-if="data.user.role === 'ADMIN'">
            <el-button type="primary" icon="Edit" circle @click="handleEdit(scope.row)"></el-button>
            <el-button type="danger" icon="Delete" circle @click="del(scope.row.id)"></el-button>
          </template>
          <template #default="scope" v-else>
            <el-button type="primary" @click="borrow(scope.row)">借阅</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="card">
      <el-pagination
          v-model:current-page="data.pageNum"
          v-model:page-size="data.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :page-sizes="[5, 10, 20]"
          :total="data.total"
          @current-change="load"
          @size-change="load"
      />
    </div>

    <el-dialog title="图书信息" v-model="data.formVisible" width="30%" destroy-on-close>
      <el-form ref="formRef" :model="data.form" :rules="data.rules" label-width="80px" style="padding: 20px 30px 10px 0">
        <el-form-item prop="name" label="图书名字">
          <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入图书名字" />
        </el-form-item>
        <el-form-item prop="price" label="图书价格">
          <el-input v-model="data.form.price" autocomplete="off" placeholder="请输入图书价格" />
        </el-form-item>
        <el-form-item prop="author" label="图书作者">
          <el-input v-model="data.form.author" autocomplete="off" placeholder="请输入图书作者" />
        </el-form-item>
        <el-form-item prop="num" label="图书数量">
          <el-input v-model="data.form.num" autocomplete="off" placeholder="请输入图书数量" />
        </el-form-item>
        <el-form-item prop="img" label="图书封面">
          <el-upload
              action="http://localhost:8088/files/upload"
              :headers="{ token: data.user.token }"
              :on-success="handleFileSuccess"
              list-type="picture"
          >
            <el-button type="primary">上传图片</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">保 存</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>
<script setup>
import {Search} from "@element-plus/icons-vue";
import {reactive, ref} from "vue";
import request from "@/utils/request.js";
import {ElMessage, ElMessageBox} from "element-plus";

const formRef = ref()

const data = reactive({
  user: JSON.parse(localStorage.getItem('code_user') || "{}"),
  name: null,
  content: null,
  pageNum: 1,
  pageSize: 5,
  total: 0,
  tableDate: [],
  form: {},
  formVisible: false,
  rules: {
    name: [
      { required: true, message: '请填写图书名字', trigger: 'blur' }
    ],
    price: [
      { required: true, message: '请填写图书价格', trigger: 'blur' }
    ],
    num: [
      { required: true, message: '请填写图书数量', trigger: 'blur' }
    ],
  }
})

const load = () => {
  request.get('/book/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name
    }
  }).then(res => {
    if (res.code === '200') {
      data.tableData = res.data?.list
      data.total = res.data?.total
    } else {
      ElMessage.error(res.msg)
    }
  })
}

load()

const handleAdd = () => {
  data.form = {}
  data.formVisible = true
}

const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}

const add = () => {
  request.post('book/add', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success("新增成功")
      data.formVisible = false
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const update = () => {
  request.put('book/update', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('更新成功')
      data.formVisible = false
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const save = () => {
  formRef.value.validate(valid => {
    if (valid) {
      data.form.id ? update() : add()
    }
  })
}

const del = (id) => {
  ElMessageBox.confirm('删除后无法恢复,您确认删除吗?', '删除确认', { type: 'warning'}).then(res => {
    request.delete('book/delete/' + id).then(res => {
      if (res.code === '200') {
        ElMessage.success('删除成功');
        load();
      } else {
        ElMessage.error(res.msg);
      }
    })
  }).catch(err => {})
}

const reset = () => {
  data.name = null
  load()
}

const handleFileSuccess = (res) => {
  data.form.img = res.data
}

const borrow = (row) => {
  request.post('record/add', {
    userId: data.user.id,
    bookId: row.id
  }).then(res => {
    if (res.code === '200') {
      ElMessage.success('操作成功,等待管理员审核')
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}
</script>