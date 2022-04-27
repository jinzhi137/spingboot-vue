<template>
  <div>
    <el-dialog v-model="dialogVisible"  width="30%">
      <el-form :model="Form" label-width="100px">
        <el-form-item  label="编号">
          <el-input v-model="Form.fxyysId" style="width: 60%;" disabled placeholder="请输入编号"></el-input>
        </el-form-item>
        <el-form-item label="非学业因素">
          <el-input v-model="Form.fxyysIdInfo" style="width: 60%;" disabled placeholder="请输入非学业因素"></el-input>
        </el-form-item>
        <el-form-item label="权重">
          <el-input v-model="Form.qz" style="width: 60%;" placeholder="请输入权重(小数)"></el-input>
        </el-form-item>
        <el-form-item label="基本分">
          <el-input v-model="Form.jbf" style="width: 60%;" placeholder="请输入基本分"></el-input>
        </el-form-item>

        <el-button style="float: right" @click="dialogVisible=false">取消</el-button>
        <el-button style="float: right" type="primary" @click="save">确定</el-button>
        <br><br>
      </el-form>
    </el-dialog>
    <el-table :data="tableData"
              border
              style="width: 60%;margin: 30px"
              stripe>
      <el-table-column
          prop="fxyysId"
          label="编号"
          style="width: auto"/>
      <el-table-column
          prop="fxyysIdInfo"
          label="非学业因素"
          style="width: auto"/>
      <el-table-column
          prop="qz"
          label="权重"
          style="width: auto"/>
      <el-table-column
          prop="jbf"
          label="基本分"
          style="width: auto"/>

      <el-table-column align="left">
        <template #header  >
          操作
        </template>
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "FxyysQz",
  data(){
    return{
      user:{},
      Form:{},
      tableData:[],
      dialogVisible:false
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      request.get("/fxyysQz").then(res =>{
        this.tableData=res.data
      })
    },
    handleEdit(row){
      this.Form=JSON.parse(JSON.stringify(row))
      this.dialogVisible=true
    },
    save(){
      this.dialogVisible=false
      request.post("/fxyysQz",this.Form).then(res =>{
        if(res.code==='0'){
          this.$message({
            type:"success",
            message:"修改成功"
          })
        }else{
          this.$message({
            type:"error",
            message: res.msg
          })
        }
        this.load()
      })

    },

  }
}
</script>

<style scoped>

</style>