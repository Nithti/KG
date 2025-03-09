<template>
  <el-container style="border-top: 0px solid #31068e">
    
  <div id="kg_container" class="home">
    <el-header style="text-align: center; font-weight: bold;">
      <KGHeader></KGHeader>
      
    </el-header>
    <router-view ></router-view>
  </div>
  </el-container>
  
 
</template>

<script>
// @ is an alias to /src
import KGHeader from "@/components/KGHeader.vue";
export default {
  name: "Home",
  components: {
    KGHeader
  }
};
</script>
<style>
#kg_container {
  height: 100vh;
  width: 100%;
}
.el-header, .el-footer {
    background-color: #4285f4;
    color: #333;
    text-align: center;
    line-height: 60px;
    
  }
  .mind-box {
    height: calc(93vh) !important;
    overflow: hidden;
}
</style>
