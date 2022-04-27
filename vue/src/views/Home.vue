<template>
  <div style="padding: 10px">
    <h1 style="text-align: center">非学业因素量化考评规则</h1>
    <p>非学业因素总评分（百分制）=（A）*0.15+（B）*0.15+（C）*0.1+（D）*0.1+（E）*0.15+（F）*0.1+（G）*0.05+（H）*0.1+（I）*0.1+J</p>
    <p>其中A-I每项最高分为100分，J项按实际计分。</p>
    <p>加分项每项需提交相关证明材料，减分项也可以由自己填写，请自觉填写，老师也会对有减分项的学生单独录入，请不要有侥幸心理</p>
    <p>每个人同时只能申请一份，二次提交会将上次的进行覆盖</p>
    <div>
      非学业因素详细评定办法详情请看下面文件
      <br>
      <el-button type="text" @click="filedownload">非学业因素评定办法</el-button>
    </div>

    <el-table :data="qzForm" style="width: 40%;"
              border
              stripe>
      <el-table-column
          prop="fxyysId"
          label="编号"/>
      <el-table-column
          prop="fxyysIdInfo"
          label="非学业因素"/>
      <el-table-column
          prop="qz"
          label="所占权重"/>
      <el-table-column
          prop="jbf"
          label="基本分"/>
    </el-table>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Home",
  data(){
    return{
      qzForm:[],
    }
  },
  created() {
    request.get("/fxyysQz").then(res =>{
      console.log("权重和基本分")
      console.log(res)
      this.qzForm=res.data
    })
  },
  methods:{
    filedownload(){
      location.href="http://localhost:8081/files/54e24efd0cbf419eab70d38f7b33385c.doc"
    }
  }
}
</script>

<style scoped>

</style>