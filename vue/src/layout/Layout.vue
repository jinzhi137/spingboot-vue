<template>

  <div>
    <Header/>
    <div style="display: flex">
      <Aside/>
      <!--      <router-link to="/">Home</router-link> |-->
      <!--      <router-link to="/about">About</router-link>|-->
      <!--      <router-link to="/fxyys_sc">非学业因素上传</router-link>-->
      <router-view style="flex: 1"  />
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header";
import Aside from "@/components/Aside";
export default {
  name: "Layout",
  components:{
    Header,
    Aside
  }
}
</script>

<style scoped>

</style>