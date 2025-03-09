<template>
  <div id="search" class="container">
    <div class="title" >
      <span style="font-size: 40px; color: #409eff;">图谱检索</span>
    </div>
    <div class="input-group">
      <el-input v-model="nodeName" class="centered-input" placeholder="输入水库名称搜索" @change="redirectToTarget" clearable>
      </el-input>
      <el-button style="font-size: 18px;" @click="redirectToTarget()" type="primary">检索</el-button>
    </div>
  </div>
</template>

<script>
import { kgBuilderApi } from "@/api";
export default {
  name:'search',
  data() {
    return {
      nodeName:'', //搜索内容
      pageSize: 500,
    };
  },
  mounted() {},
  created() {
  },
  methods: {
    //搜索功能
    redirectToTarget() {
      let data = {
        domain: 'ba',
        nodeName: this.nodeName,
        pageSize: this.pageSize
      };
    this.$router.push({ path: '/kgbuilderv1', query: { param: data } });
    // 延迟清除data数据
    // setTimeout(() => {
    //   data.domain = '';
    //   data.nodeName = '';
    //   data.pageSize = '';
    // }, 10);
    },
    
  },
};
</script>

<style lang="scss" scoped>
.container {
  display: flex;
  flex-direction: column;
  justify-content: center; //水平居中
  align-items: center; // 垂直居中
  height: 90vh; //整个页面高度
}

.title {
  // margin-bottom: 150px;
  transform: translateY(-130px);
}

.input-group {
  display: flex; // 添加flex布局以使输入框和按钮水平排列
  justify-content: center; /* 水平居中 */
  align-items: center; // 保持输入框和按钮垂直居中对齐
  transform: translateY(-80px); // 向上移动50px
}

.centered-input {
  // top: -10%;
  width: 400px; //搜索框宽度
  border:solid #409eff;
  border-radius: 4px
}

</style>