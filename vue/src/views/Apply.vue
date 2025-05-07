<template>
  <div>

    <div class="card" style="margin-bottom: 5px">
      <el-input clearable @clear="load" style="width: 240px; margin-right: 5px" v-model="data.title" placeholder="请输入标题查询" :prefix-icon="Search"></el-input>
      <el-button type="primary" @click="load">查 询</el-button>
      <el-button @click="reset">重 置</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px" v-if="data.user.role === 'USER'">
      <el-button type="primary" @click="handleAdd">提交请假申请</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <el-table :data="data.tableData" style="width: 100%"
                :header-cell-style="{ color: '#333', backgroundColor: '#56e0fc'}">
        <el-table-column prop="title" label="请假标题" />
        <el-table-column prop="content" label="请假说明" />
        <el-table-column prop="userName" label="请假人" />
        <el-table-column prop="time" label="提交时间" />
        <el-table-column prop="status" label="审核状态">
          <template v-slot="scope">
            <el-tag type="warning" v-if="scope.row.status === '待审核'">{{ scope.row.status }}</el-tag>
            <el-tag type="success" v-if="scope.row.status === '审核通过'">{{ scope.row.status }}</el-tag>
            <el-tag type="danger" v-if="scope.row.status === '审核拒绝'">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100px">
          <template #default="scope" v-if="data.user.role === 'USER'">
            <el-button :disabled="scope.row.status !== '待审核'" type="primary" icon="Edit" circle @click="handleEdit(scope.row)"></el-button>
            <el-button :disabled="scope.row.status !== '待审核'" type="danger" icon="Delete" circle @click="del(scope.row.id)"></el-button>
          </template>
          <template #default="scope" v-if="data.user.role === 'ADMIN'">
            <el-button :disabled="scope.row.status !== '待审核'" type="primary" @click="handleEdit(scope.row)">审核</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog title="请假信息" v-model="data.formVisible" width="30%" destroy-on-close>
      <el-form ref="formRef" :model="data.form" :rules="data.rules" label-width="80px" style="padding: 20px 30px 10px 0">
        <el-form-item prop="title" label="请假标题" v-if="data.user.role === 'USER'">
          <el-input v-model="data.form.title" autocomplete="off" placeholder="请输入请假标题" />
        </el-form-item>
        <el-form-item prop="content" label="请假说明" v-if="data.user.role === 'USER'">
          <el-input type="textarea" rows="3" v-model="data.form.content" autocomplete="off" placeholder="请输入请假说明" />
        </el-form-item>
        <el-form-item prop="status" label="审核状态" v-if="data.user.role === 'ADMIN'">
          <el-radio-group v-model="data.form.status">
            <el-radio-button label="待审核" value="待审核" />
            <el-radio-button label="审核通过" value="审核通过" />
            <el-radio-button label="审核拒绝" value="审核拒绝" />
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="reason" label="请假说明" v-if="data.user.role === 'ADMIN' && data.form.status === '审核拒绝'">
          <el-input v-model="data.form.reason" autocomplete="off" placeholder="请输入请假说明" />
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

const data = reactive({
  user: JSON.parse(localStorage.getItem('code_user') || "{}"),
  pageNum: 1,
  pageSize: 5,
  total: 0,
  title: null,
  tableData: [],
  formVisible: false,
  form: {},
  rules: {
    title: [
      { required: true, message: '请填写请假标题', trigger: 'blur' }
    ],
    content: [
      { required: true, message: '请填写请假说明', trigger: 'blur' }
    ],
  }
})

const formRef = ref()

const load = () => {
  request.get('apply/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      title: data.title
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
  request.post('apply/add', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success("提交成功,等待管理员审核")
      data.formVisible = false
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const update = () => {
  request.put('apply/update', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success("操作成功")
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
    request.delete('apply/delete/' + id).then(res => {
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
  data.title = null
  load()
}


</script>