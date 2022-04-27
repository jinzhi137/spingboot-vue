<template>
  <div >
    艾德撒上的
    <el-form :model="fxyys_form">
      思想表现<br/>
      <el-checkbox-group v-model="fxyys_form.reaAinfo" v-for="item in fxyys_form.Ainfo" :key="item.id">
        <el-checkbox :label=item>{{item}}</el-checkbox><br>
      </el-checkbox-group>
      hhhhh
      <br>

      {{total(fxyys_form.Atotal)}}
      {{fxyys_form.Atotal}}<br>

    </el-form>
  </div>
</template>

<script>
export default {
  name: "fxyys_sc",
  data() {
    return{
      fxyys_form:{
        Ainfo:[50,30,10],
        reaAinfo:[],
        Atotal:Number(0)
      },

    }
  },
  computed:{ //计算函数，
    total(){
      return function(data){
        data=0;
        for (var i=0;i<this.fxyys_form.reaAinfo.length;i++){
          data=data+Number(this.fxyys_form.reaAinfo[i]);
        }
        this.fxyys_form.Atotal=data;
        return Number(data)
      };
    }
  }
}
</script>

<style scoped>

</style>