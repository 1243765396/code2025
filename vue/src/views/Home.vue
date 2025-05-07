<template>
  <div>
    <div class="card" v-if="data.user.role === 'USER'">今生有梦一起走</div>
    <div class="card" style="margin-top: 10px; width: 50%" v-if="data.user.role === 'USER'">
      <div style="font-size: 18px; margin-bottom: 20px">系统公告</div>
      <el-timeline style="max-width: 600px">
        <el-timeline-item :timestamp="item.time" color="#0bbf87" placement="top" v-for="item in data.noticeData">
          <el-card>
            <h4>{{ item.title }}</h4>
            <p>{{ item.content }}</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </div>
    <div class="demo-collapse" style="width: 50%" v-if="data.user.role === 'USER'">
      <el-collapse v-model="activeName" accordion>
        <el-collapse-item :title="item.title" :name="item.id" v-for="item in data.noticeData">
          <div style="padding: 0 20px">
            {{ item.content }}
          </div>
        </el-collapse-item>
      </el-collapse>
    </div>
    <div v-if="data.user.role === 'ADMIN'">
      <div style="display: flex; grid-gap: 10px">
        <div class="card" style="height: 400px;width: 50%;" id="pie"></div>
        <div class="card" style="height: 400px;width: 50%;" id="bar"></div>
      </div>

      <div class="card" style="height: 400px;width: 100%; margin-top: 10px" id="line"></div>
    </div>
  </div>
</template>

<script setup>

import {reactive, ref, onMounted} from "vue";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";
import * as echarts from "echarts";

const data = reactive({
  user: JSON.parse(localStorage.getItem('code_user') || "{}"),
  noticeData: [],
})

const activeName = ref('')

const loadNotice = () => {
  request.get('notice/selectAll').then(res => {
    if (res.code === '200') {
      data.noticeData = res.data
      if (data.noticeData.length > 3) {
        data.noticeData = data.noticeData.slice(0, 3)
      }
      activeName.value = data.noticeData[0].id
    } else {
      ElMessage.error(res.msg)
    }
  })
}

loadNotice()

const loadPie = () => {
  request.get('echarts/pie').then(res => {
    if (res.code === '200') {
      let chartDom = document.getElementById('pie')
      let myChart = echarts.init(chartDom)
      pieOptions.series[0].data = res.data
      myChart.setOption(pieOptions);
    }
  })
}

const loadBar = () => {
  request.get('echarts/bar').then(res => {
    if (res.code === '200') {
      let chartDom = document.getElementById('bar')
      let myChart = echarts.init(chartDom)
      barOptions.xAxis.data = res.data.xAxis
      barOptions.series[0].data = res.data.yAxis
      myChart.setOption(barOptions);
    }
  })
}

const loadLine = () => {
  request.get('echarts/line').then(res => {
    if (res.code === '200') {
      let chartDom = document.getElementById('line')
      let myChart = echarts.init(chartDom)
      lineOptions.xAxis.data = res.data.xAxis
      lineOptions.series[0].data = res.data.yAxis
      myChart.setOption(lineOptions);
    }
  })
}

onMounted(() => {
  loadPie()
  loadBar()
  loadLine()
})

// 饼图
let pieOptions = {
  title: {
    text: 'Referer of a Website', // 主标题
    subtext: 'Fake Data', // 副标题
    left: 'center'
  },
  tooltip: {
    trigger: 'item',
    formatter: '{a} <br/>{b} : {c} ({d}%)'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      name: '比例', // 鼠标移上去显示内容
      type: 'pie',
      radius: '50%',
      center: ['50%', '60%'],
      data: [
        { name: 'Search Engine', value: 1048  }, // 实例数据: name表示维度,value表示对应的值
      ]
    }
  ]
}

// 柱状图
let barOptions = {
  title: {
    text: 'Referer of a Website', // 主标题
    subtext: 'Fake Data', // 副标题
    left: 'center'
  },
  grid: {
    top: '20%',
    bottom: '20%' // bottom就是距离底部的距离
  },
  xAxis: {
    name: '攻略分类',
    type: 'category',
    data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'], // 横坐标
    axisLabel: {
      show: true, // 是否显示刻度标签,默认显示
      interval: 0, // 坐标轴刻度标签的显示间隔,在类目轴中有效;默认会采用标签不重叠的策略间隔显示标签;可以设置成0强制显示所有标签;如果设置为1,表示隔一个标签显示一个标签
      rotate: -60, // 刻度标签旋转的角度,在类目轴中类目标签显示不下的时候可以通过旋转防止标签之间重叠;旋转的角度从-90度到90度
      inside: false, // 刻度标签是否朝内,默认朝外
      margin: 6, // 刻度标签与轴线之间的距离
    }
  },
  yAxis: {
    name: '攻略数量',
    type: 'value'
  },
  tooltip: {
    trigger: 'item',
  },
  series: [
    {
      data: [120, 200, 150, 80, 70, 110, 130], // 纵坐标
      type: 'bar',
      itemStyle: {
        normal: {
          color: function () {
            return "#" + Math.floor(Math.random() * (256 * 256 * 256 - 1)).toString(16);
          }
        }
      }
    }
  ]
}

let lineOptions = {
  title: {
    text: 'Referer of a Website', // 主标题
    subtext: 'Fake Data', // 副标题
    left: 'center'
  },
  legend: {
    data: [],
    template: ""
  },
  grid: {
    left: '3%',
    right: '4%', // bottom就是距离底部的距离
    bottom: '3%',
    containLabel: true
  },
  tooltip: {
    trigger: 'item',
  },
  xAxis: {
    name: "日期",
    type: 'category',
    data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'], // 横坐标
  },
  yAxis: {
    name: '攻略数量',
    type: 'value'
  },
  series: [
    {
      name: "攻略数量",
      data: [820, 932, 901, 936, 1290, 1330, 1320],
      type: 'line',
      smooth: true,
      markLine: {
        data: [{ type: 'average', name: '最近一周场攻略发布数量平均值' }]
      },
      markPoint: {
        data: [
          { type: 'max', name: '最大值' },
          { type: 'min', name: '最小值' }
        ]
      }
    }
  ]
}
</script>