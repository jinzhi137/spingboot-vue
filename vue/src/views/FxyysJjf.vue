<template>
  <div>
    <div style="margin: 10px ">
      <el-button type="primary" @click="checkall">查看全部</el-button>
      <el-button type="primary" @click="createNew">新增</el-button>
    </div>
    <div style="margin: 10px ">
      <el-select  v-model="fxyysJjfId" placeholder="请选择查询项目" clearable >
        <el-option value="A">思想表现</el-option><el-option value="B">文明守纪</el-option>
        <el-option value="C">学习态度</el-option><el-option value="D">社会工作</el-option>
        <el-option value="E">实践公益</el-option><el-option value="F">团队精神</el-option>
        <el-option value="G">科研创新</el-option><el-option value="H">文体素质</el-option>
        <el-option value="I">技能素质</el-option><el-option value="J">特殊经历</el-option>
      </el-select>
      <el-button type="primary" @click="load" style="margin-left: 5px"><el-icon ><search /></el-icon>查询</el-button>
    </div>
    <el-dialog v-model="dialogVisible"  width="30%">
      <el-form :model="Form" label-width="100px">

        <el-form-item  label="编号">
          <el-select :disabled="disabled" transfer="true" :teleported="false" v-model="Form.fxyysJjfId" placeholder="请选择编号(A-J)"   >
            <el-option value="A">思想表现</el-option><el-option value="B">文明守纪</el-option>
            <el-option value="C">学习态度</el-option><el-option value="D">社会工作</el-option>
            <el-option value="E">实践公益</el-option><el-option value="F">团队精神</el-option>
            <el-option value="G">科研创新</el-option><el-option value="H">文体素质</el-option>
            <el-option value="I">技能素质</el-option><el-option value="J">特殊经历</el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="加\减分">
          <el-radio :disabled="disabled" v-model="Form.fxyysZf" label="1" size="large">加分</el-radio>
          <el-radio :disabled="disabled" v-model="Form.fxyysZf" label="-1" size="large">减分</el-radio>
        </el-form-item>
        <el-form-item label="分数">
          <el-input v-model="Form.fs" style="width: 60%;" placeholder="请输入分数"></el-input>
        </el-form-item>
        <el-form-item label="详细信息">
          <el-input v-model="Form.fxyysJjfInfo" autosize  type="textarea" style="width: 100%;" placeholder="请输入详细信息"></el-input>
        </el-form-item>
        <el-button style="float: right" @click="dialogVisible=false">取消</el-button>
        <el-button style="float: right" type="primary" @click="save">确定</el-button>
        <br><br>
      </el-form>
    </el-dialog>
    <el-table :data="tableData"
              border
              style="width:calc(100vw - 280px);margin: 10px"
              stripe>
      <el-table-column
          label="非学业因素"
          fixed
          style="width: auto">
        <template #default="scope">
          <span v-if='scope.row.fxyysJjfId==="A"'>思想表现</span>
          <span v-if='scope.row.fxyysJjfId==="B"'>文明守纪</span>
          <span v-if='scope.row.fxyysJjfId==="C"'>学习态度</span>
          <span v-if='scope.row.fxyysJjfId==="D"'>社会工作</span>
          <span v-if='scope.row.fxyysJjfId==="E"'>实践公益</span>
          <span v-if='scope.row.fxyysJjfId==="F"'>团队精神</span>
          <span v-if='scope.row.fxyysJjfId==="G"'>科研创新</span>
          <span v-if='scope.row.fxyysJjfId==="H"'>文体素质</span>
          <span v-if='scope.row.fxyysJjfId==="I"'>技能素质</span>
          <span v-if='scope.row.fxyysJjfId==="J"'>特殊经历</span>
        </template>
      </el-table-column>
      <el-table-column
          label="加\减分"
          style="width: auto">
        <template #default="scope">
          <span v-if='scope.row.fxyysZf==="1"'>加分</span>
          <span v-if='scope.row.fxyysZf==="-1"'>减分</span>
        </template>
      </el-table-column>
      <el-table-column
          prop="jjfId"
          label="加\减分id"
          style="width: auto"/>
      <el-table-column
          prop="fs"
          label="分数"
          style="width: auto"/>
      <el-table-column
          prop="fxyysJjfInfo"
          label="详细信息"
          style="width: auto"
          />
      <el-table-column fixed="right" align="left">
        <template #header  >
          操作
        </template>
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="确认要删除吗"  @confirm="handleDelete(scope.row)">
            <template #reference>
              <el-button
                  class="buttonDiv"
                  size="small"
                  type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "FxyysJjf",
  data(){
    return{
      fxyysJjfId:"",
      fxyysZf:"",
      disabled:true,
      user:{},
      Form1:{
        fxyysJjfId:""
      },
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
      request.get("/fxyysJjf",{
        params:{
          fxyysJjfId:this.fxyysJjfId,
          fxyysZf:this.fxyysZf
        }
      }).then(res =>{
        this.tableData=res.data
      })
    },
    change(){
      this.$forceUpdate()
    },
    handleEdit(row){
      this.Form=JSON.parse(JSON.stringify(row))
      this.disabled=true
      this.dialogVisible=true
    },
    save(){
      this.dialogVisible=false
      if(this.disabled==true)
      {
        request.post("/fxyysJjf",this.Form).then(res =>{
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
        return
      }
      request.get("/fxyysJjf",{
        params:{
          fxyysJjfId:this.Form.fxyysJjfId,
          fxyysZf:this.Form.fxyysZf
        }
      }).then(res =>{
        this.Form.jjfId=res.data.length+1
        request.post("/fxyysJjf",this.Form).then(res =>{
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
      })
    },
    checkall(){
      this.fxyysJjfId=""
      this.load()
    },
    createNew(){
      this.Form=JSON.parse(JSON.stringify(this.Form1))
      this.disabled=false
      this.dialogVisible=true
    },
    handleDelete(row){
      console.log(row)
      request.delete("/fxyysJjf/"+row.fxyysJjfId+"/"+row.fxyysZf+"/"+row.jjfId).then(res =>{
        if(res.code==='0'){
          this.$message({
            type:"success",
            message:"删除成功"
          })
        }else{
          this.$message({
            type:"error",
            message: res.msg
          })
        }
        this.load()
      })
    }

  }
}
</script>

<style scoped>

</style>