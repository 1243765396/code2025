<template>
  <div>
    <!--    头部区域开始-->
    <div style="height: 60px; display: flex">
      <div style="width: 240px; display: flex; align-items: center; padding-left: 20px; background-color: #16e58b">
        <img style="width: 40px; height: 40px; border-radius: 50%" src="@/assets/logo.svg" alt="">
        <span style="font-size: 20px; font-weight: bold; color: #000000; margin-left: 5px">小白</span>
      </div>
      <div style="flex: 1; display: flex; align-items: center; padding-left: 20px; border-bottom: 1px solid #ddd">
        <span style="margin-right: 5px; cursor: pointer" @click="router.push('/manager/home')">首页</span> / <span style="margin-left: 5px">{{ router.currentRoute.value.meta.name }}</span>
      </div>
      <div style="width: fit-content; padding-right: 20px; display: flex; align-items: center; border-bottom: 1px solid #ddd">
        <el-dropdown>
          <div style="display: flex; align-items: center">
            <img v-if="data.user?.avatar" style="width: 40px; height: 40px; border-radius: 50%" :src="data.user?.avatar" alt="">
            <img v-else style="width: 40px; height: 40px; border-radius: 50%" src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" alt="">
            <span style="margin-left: 5px">{{ data.user?.name }}</span>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
            <el-dropdown-item @click="router.push('/manager/person')">个人信息</el-dropdown-item>
            <el-dropdown-item @click="router.push('/manager/updatePassword')">修改密码</el-dropdown-item>
            <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
    <!--    头部区域结束-->

    <!--    下方区域开始-->
    <div style="display: flex">
      <!--      菜单区域开始-->
      <div style="width: 240px">
        <el-menu router :default-openeds="['1', '2']" :default-active="router.currentRoute.value.path" style="min-height: calc(100vh - 60px)">
          <el-menu-item index="/manager/home">
            <el-icon>
              <House/>
            </el-icon>
            <span>首页</span>
          </el-menu-item>
          <el-sub-menu index="1">
            <template #title>
              <el-icon>
                <Monitor />
              </el-icon>
              <span>信息管理</span>
            </template>
            <el-menu-item index="/manager/notice" v-if="data.user.role === 'ADMIN'">系统公告</el-menu-item>
            <el-menu-item index="/manager/notice" v-else>公告信息</el-menu-item>
            <el-menu-item index="/manager/category">旅游分类</el-menu-item>
            <el-menu-item index="/manager/introduction">旅游攻略</el-menu-item>
            <el-menu-item index="/manager/apply">请假申请</el-menu-item>
            <el-menu-item index="/manager/book">图书信息</el-menu-item>
            <el-menu-item index="/manager/record">借阅记录</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="2" v-if="data.user.role === 'ADMIN'">
            <template #title>
              <el-icon>
                <User/>
              </el-icon>
              <span>用户管理</span>
            </template>
            <el-menu-item index="/manager/admin">管理员信息</el-menu-item>
            <el-menu-item index="/manager/user">用户信息</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </div>
      <!--      菜单区域结束-->

      <!--数据渲染区域开始-->
      <div style="flex: 1; width: 0; padding: 10px; background-color: #8cd3fc">
        <RouterView @updateUser="updateUser"/>
      </div>
      <!--数据渲染区域结束-->

    </div>
    <!--    下方区域结束-->
  </div>
</template>

<script setup>

import router from "@/router/index.js";
import { reactive } from "vue";

const data = reactive({
  user: JSON.parse(localStorage.getItem('code_user') || "{}")
})

const logout = () => {
  localStorage.removeItem("code_user")
  location.href = '/login'
}

const updateUser = () => {
  data.user = JSON.parse(localStorage.getItem("code_user") || "{}")
}

// if (!data.user?.id) {
//   location.href = '/login'
// }
</script>

<style>
/*左侧大框的背景颜色和边框设置*/
.el-menu {
  background-color: #16e58b;
  border: none;
}

/*下拉菜单字体颜色和背景颜色*/
.el-sub-menu__title {
  color: #000000;
  background-color: #16e58b;
}

/*点击菜单字体颜色*/
.el-menu-item {
  height: 50px;
  color: #000000;
}

/*高亮菜单颜色*/
.el-menu .is-active {
  background-color: #2cceff;
}

/*移入下拉菜单颜色*/
.el-sub-menu__title:hover {
  background-color: #00ff97;
}

/*移入点击菜单颜色(除高亮菜单)*/
.el-menu-item:not(.is-active):hover {
  background-color: #73d9ff;
}

/*移动有小手*/
.el-dropdown {
  cursor: pointer;
}

/*个人信息无边框*/
.el-tooltip__trigger {
  outline: none;
}

/*二级菜单右靠*/
.el-menu--inline .el-menu-item {
  padding-left: 48px !important;
}
</style>