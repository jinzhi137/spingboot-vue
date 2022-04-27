<template>
  <div class="about">
    <h1>This is an about page</h1>
    {{ this.file.toString() }}
    {{ this.file.includes(2) }}
    <el-upload
        action="/api/files/upload"
        :on-success="filesUploadSuccess"
        multiple
    >
      <el-button type="primary">点击上传</el-button>
      <template #tip>
        <div class="el-upload__tip">
          测试
        </div>
      </template>
    </el-upload>
    <el-icon><add-location /></el-icon>
    <el-icon><menu /></el-icon>
    <el-button icon="menu" @click="dialogVisible=true"></el-button>
    <el-dialog  v-model="dialogVisible">
      <el-select transfer="true" :teleported="false" v-model="Form.fxyysJjfId" placeholder="请选择编号(A-J)"   >
        <el-option value="A"/><el-option value="B"/><el-option value="C"/>
        <el-option value="D"/><el-option value="E"/><el-option value="F"/>
        <el-option value="G"/><el-option value="H"/><el-option value="I"/>
        <el-option value="J"/><el-option value=""/>
      </el-select>
      <el-button @click="dialogVisible=false">关闭</el-button>
    </el-dialog>
  </div>
</template>
<script>
export default {
  data(){
    return{
      file:[0,1,2],
      dialogVisible:false,
      Form:{
        fxyysJjfId:""
      }
    }
  },
  methods:{
    filesUploadSuccess(res){
      if(res.code==='0'){
        this.$message({
          type:"success",
          message:"上传成功"
        })
        console.log(res)
      }else{
        this.$message({
          type:"error",
          message: res.msg
        })
      }
    },
  }
}
</script>