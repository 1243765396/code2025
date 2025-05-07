<template>
  <div style="min-height: 1000px">
    <el-menu
        default-active="1"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
    >
      <el-menu-item index="1">系统首页</el-menu-item>
      <el-menu-item @click="logout">退出</el-menu-item>
    </el-menu>

    <div style="margin-bottom: 20px">
      <el-carousel height="500px">
<!--        <el-carousel-item v-for="item in data.carouselData" :key="item">-->
<!--          <img :src="item" alt="" style="height: 500px; width: 100%">-->
<!--        </el-carousel-item>-->
        <el-carousel-item v-for="item in data.introductionData" :key="item">
          <img :src="item.img" alt="" style="height: 500px; width: 100%; cursor: pointer" @click="naTo('/front/introductionDetail?id=' + item.id)">
        </el-carousel-item>
      </el-carousel>
    </div>

    <div style="width: 80%; margin: 20px auto">
      <div style="font-size: 20px; border-left: 5px solid #005eff; padding-left: 5px; height: 30px; line-height: 30px">购物平台</div>
      <div style="margin-top: 20px">
        <el-row :gutter="20">
          <el-col :span="6" v-for="item in data.introductionData" style="margin-bottom: 20px">
            <div style="height: 280px;">
              <img :src="item.img" alt="暂无图片" style="width: 100%; height: 280px; border-radius: 5px">
            </div>
            <div style="height: 20px; font-size: 16px; font-weight: bold; margin-top: 5px">{{ item.title }}</div>
            <div style="display: flex; align-items: center; margin-top: 10px; grid-gap: 10px">
              <img v-if="item.userAvatar" :src="item.userAvatar" alt="暂无图片" style="width: 30px; height: 30px; border-radius: 50%">
              <img v-else src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" style="width: 30px; height: 30px; border-radius: 50%">
              <div style="font-size: 15px">{{ item.userName }}</div>
              <div>{{ item.time }}</div>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>

    <div style="width: 80%; margin: 20px auto">
      <div style="font-size: 20px; border-left: 5px solid #005eff; padding-left: 5px; height: 30px; line-height: 30px">购物平台</div>
      <div style="margin-top: 20px; display: flex; grid-gap: 20px" v-for="item in data.introductionData">
        <div style="flex: 1">
          <img @click="naTo('/front/introductionDetail?id=' + item.id)" :src="item.img" alt="" style="width: 100%; height: 280px; display: block; border-radius: 5px; cursor: pointer">
        </div>
        <div style="flex: 3">
          <div style="font-size: 20px; font-weight: bold">{{ item.title }}</div>
          <div style="margin-top: 10px; font-size: 15px; line-height: 25px; height: 125px; text-align: justify" class="line5">{{ item.description }}</div>
          <div style="display: flex; align-items: center; margin-top: 10px; grid-gap: 10px">
            <img v-if="item.userAvatar" :src="item.userAvatar" alt="暂无图片" style="width: 30px; height: 30px; border-radius: 50%">
            <img v-else src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" style="width: 30px; height: 30px; border-radius: 50%">
            <div style="font-size: 15px">{{ item.userName }}</div>
            <div>{{ item.time }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>

import {reactive} from "vue";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";
import lun1 from '@/assets/imgs/lun-1.png';
import lun2 from '@/assets/imgs/lun-2.png';
import lun3 from '@/assets/imgs/lun-3.png';

const data = reactive({
  user: JSON.parse(localStorage.getItem('code_user') || "{}"),
  introductionData: [],
  carouselData: [lun1, lun2, lun3]
})

const loadIntroduction = () => {
  request.get('introduction/selectAll').then(res => {
    if (res.code === '200') {
      data.introductionData = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}

loadIntroduction()

const logout = () => {
  localStorage.removeItem("code_user")
  location.href = '/login'
}

const naTo = (url) => {
  location.href = url
}
</script>
<style>
.line5 {
  word-break: break-all;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 5; /* 超出几行省略 */
  overflow: hidden;
}
</style>