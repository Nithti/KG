<!--
 * @Description: file content
 * @Author: your name
 * @Date: 2022-01-01 12:53:00
 * @LastEditors: your name
 * @LastEditTime: 2022-01-01 13:43:38
-->
<template>
  <el-container>
    <div class="systitle">
      <!-- <span>LOGO</span> -->
      <!-- <el-menu :default-active="activeIndex2" mode="horizontal" background-color="#4285f4" text-color="#fff" active-text-color="#ffd04b" router-link> -->
      <el-menu :default-active="$route.path" mode="horizontal" background-color="#4285f4" text-color="#fff"
        active-text-color="#ffd04b">
        <!-- <el-menu-item index="1" route="">知识图谱</el-menu-item> -->
        <el-menu-item index="/firstpagev1"><router-link to="/firstpagev1">首页</router-link></el-menu-item>
        <el-menu-item index="/zhishiv1"><router-link to="/zhishiv1">知识模型</router-link></el-menu-item>
        <el-menu-item index="/modelv1"><router-link to="/modelv1">图谱构建</router-link></el-menu-item>
        <el-menu-item index="/searchv1" class="el-icon-search"><router-link to="/searchv1">图谱检索</router-link></el-menu-item>
        <el-menu-item index="/kgbuilderv1"><router-link to="/kgbuilderv1">图谱展现</router-link></el-menu-item>
      </el-menu>
    </div>

  </el-container>
  <!-- <el-container style="border-top: 0px solid #eee">
    <div style="
      height: 58px;
      /* border: 1px solid rgb(220, 223, 230); */
      background-color: #4285f4;">
      <div class="systitle">知识图谱</div>
      <el-menu>
      <el-sub-menu index="1">
        <template #title>
          <el-icon>
            <MenuIcon />
          </el-icon>
          <span>地图专题</span></template>
<el-menu-item index="1-1" route="/Index_v1">
  <el-icon>
    <Compass />
  </el-icon>地图主页</el-menu-item>
</el-sub-menu>
</el-menu>
</div>

</el-container> -->

</template>

<script>
export default {
  props: {
    data: Object
  },
  data() {
    return {
      // activeIndex2: '/',
      activeIndex2: this.$route.path,
      // navList: [
      //   {
      //     title: "首页",
      //     icon: "glyphicon glyphicon-cog",
      //     linkUrl: "http://www.miaoleyan.com",
      //     active: false,
      //     childrens: []
      //   },
      //   {
      //     title: "分享",
      //     icon: "glyphicon glyphicon-th-list",
      //     linkUrl: "javascript:void(0);",
      //     active: false,
      //     childrens: [
      //       {
      //         title: "杨青博客",
      //         icon: "",
      //         linkUrl: "http://www.yangqq.com/",
      //         active: false,
      //         childrens: []
      //       },
      //       {
      //         title: "程序猿DD",
      //         icon: "",
      //         linkUrl: "http://blog.didispace.com/",
      //         active: false,
      //         childrens: []
      //       },
      //       {
      //         title: "hAdmin",
      //         icon: "",
      //         linkUrl: "http://demo.mycodes.net/houtai/hAdmin",
      //         active: false,
      //         childrens: []
      //       }
      //     ]
      //   }
      // ]
    };
  },
  watch: {
    // '$route'(to, from) {
    //   this.activeIndex2 = to.path;
    //   // console.log(this.activeIndex2)
    // },
    // $route() {
    //   location.reload();
    // },
  },
  mounted() {
    // console.log(this.$route.path);console.log(this.activeIndex2)
  },
  components: {},
  methods: {
    selectStyle(nav) {
      var _this = this;
      this.$nextTick(function () {
        _this.navList.forEach(function (item) {
          item.active = false;
        });
        nav.active = true;
      });
    },
    outStyle(nav) {
      nav.active = false;
    },
    changeIcon() {
      this.isOpen = !this.isOpen;
    },
    searchActive() {
      this.search_active = !this.search_active;
    },
    clickNav(nav) {
      nav.active = !nav.active;
    }
  }
};
</script>
<style>
#topnav_current {
  color: #00A7EB;
}

.menu {
  height: 76px;
  width: 100%;
  background-color: #000;
}

.nav {
  height: 80px;
  width: 100%;
  margin: 0 auto;
}

.nav li {
  float: left;
  position: relative;
}

.nav li a {
  color: #bdbdbd;
  padding: 0 10px;
  display: inline-block;
  text-decoration: none
}

.nav li a:hover {
  color: #fff;
}

.nav li .sub-nav {
  position: absolute;
  top: 30px;
  width: 120px;
  background: #FFF;
  left: -20px;
  /* display: none;  */
  z-index: 9999
}

.nav li .sub-nav li {
  clear: left;
  height: 20px;
  line-height: 35px;
  position: relative;
  width: 200px;
  padding: 5px 20px
}

.nav li .sub-nav li a {
  font-size: 15px;
  font-weight: 400;
  color: #404040;
  line-height: 28px;
}

.nav li .sub-nav li a:hover {
  color: #000;
  border-left: 2px solid #000;
}

.a_active {
  color: #00A7EB !important;
}

.logo {
  float: left;
  margin-left: 30px;
  width: 380px;
  font-size: 26px;
}

.logo a {
  color: #00A7EB;
  text-decoration: none;
}

.hometitle {
  font-size: 18px;
  color: #282828;
  font-weight: 600;
  margin: 0;
  text-transform: uppercase;
  padding-bottom: 15px;
  margin-bottom: 25px;
  position: relative;
}

.hometitle:after {
  content: "";
  background-color: #282828;
  left: 0;
  width: 50px;
  height: 2px;
  bottom: 0;
  position: absolute;
  -webkit-transition: 0.5s;
  -moz-transition: 0.5s;
  -ms-transition: 0.5s;
  -o-transition: 0.5s;
  transition: 0.5s;
}

.systitle {
  /* float: left; */
  margin-left: 100px;
  text-align: left;
  width: 100%;
  height: 58px;
  font-size: 30px;
  /* display: inline-block; */
  line-height: 50px;
  vertical-align: middle;
  color: #fff;
  background-color: #4285f4;


}
</style>
