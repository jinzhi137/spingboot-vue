<template>
  <div>
    <el-menu
        class="el-menu-vertical-demo"
        icon="menu"
        style="width: 200px; min-height: calc(100vh - 50px)"
        router
        :default-active="$route.path.substring(0)"
    >
      <el-sub-menu index="1">
        <template #title>
          <el-icon><setting /></el-icon>
          <span>非学业因素信息</span>
        </template>
        <el-menu-item index="/home">各项基本分及权重</el-menu-item>
        <el-menu-item index="/fxyysSc">非学业因素申报</el-menu-item>
        <el-menu-item index="/history">非学业因素申请情况</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="2" v-if="this.user.role>=1">
        <template #title>
          <el-icon><location /></el-icon>
          <span>学生管理</span>
        </template>
          <el-menu-item index="/examine">非学业因素审核</el-menu-item>
          <el-menu-item index="/user">登录账号管理</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="3" v-if="this.user.role>=1">
        <template #title>
          <el-icon><compass /></el-icon>
          <span>非学业因素管理</span>
        </template>
        <el-menu-item index="/fxyysqz">权重基本分管理</el-menu-item>
        <el-menu-item index="/fxyysjjf">评分规则管理</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="4">
        <template #title>
          <el-icon><setting /></el-icon>
          <span>个人中心</span>
        </template>
        <el-menu-item index="/person">用户信息</el-menu-item>
        <el-menu-item index="/about">测试</el-menu-item>
        <el-menu-item index="/im">聊天界面</el-menu-item>
      </el-sub-menu>

      <el-menu-item index="3" disabled>
        <el-icon><document /></el-icon>
        <span>导航三</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
import {
  Menu as IconMenu,
} from '@element-plus/icons-vue'
import request from "@/utils/request";
export default {
  name: "Aside",
  data(){
    return{
      user:{},
    }
  },
  created() {
    let userStr = sessionStorage.getItem("user") || "{}"
    if(this.user.role===null){
      this.user.role=0
    }
    this.user = JSON.parse(userStr)
    if(this.user!=={}){
      // 请求服务端，确认当前登录用户的合法信息
      request.get("/user/" + this.user.username).then(res => {
        if (res.code === '0') {
          this.user = res.data
        }
      })
    }

  }
}
</script>

<style scoped>

</style>