<template>
  <div>
    <el-card style="width: 40%; margin: 10px">
      <el-form ref="form" :model="form" label-width="80px">

        <el-form-item label="学号">
          <el-input v-model="form.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="年级">
          <el-input v-model="form.grade" disabled></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="专业">
          <el-input v-model="form.maj"  disabled></el-input>
        </el-form-item>
        <el-form-item label="学院">
          <el-input v-model="form.xy" disabled></el-input>
        </el-form-item>
      </el-form>
      <div style="text-align: center">
        <el-button type="primary" @click="update">保存</el-button>
      </div>
    </el-card>

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Person",
  data() {
    return {
      form: {},
      user:""
    }
  },
  created() {
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)
    request.get("/stuTea/get",{
      params:{
        Id:this.user.username
      }
    }).then(res =>{
      console.log(res)
      this.form=res.data[0]
    })
  },
  methods: {
    update() {
      request.post("/stuTea/update", this.form).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "更新成功"
          })
          sessionStorage.setItem("user",this.user.toString())
          // 触发Layout更新用户信息
          this.$emit("userInfo")
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>