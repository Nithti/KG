import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import Index_v1 from "../views/kgbuilder/index_v1.vue";
import Search_v1 from "../components/KGSearch.vue";
import Model_v1 from "../components/KGModel.vue";
import ZhiShi_v1 from "../components/KGZhiShi.vue";
import FirstPage_v1 from "../components/KGFirstPage.vue"

// 默认页面title
const defaultTitle = "知识图谱系统"
document.title = defaultTitle;
Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    redirect: '/firstpagev1'
  },
  {
    //默认跳转
    path: "/",
    component: Home,
    name:'Home',
    children:[
      {
        path: "/firstpagev1",
        name: "firstpagev1",
        meta: {title:'首页'},
        component: FirstPage_v1
      },
      {
        path: "/kgbuilderv1",
        name: "kgbuilderv1",
        meta: { title: '知识图谱' },
        component: Index_v1,
        // component: () => import("../views/kgbuilder/index_v1.vue")
      },
      {
        path: "/searchv1",
        name: "searchv1",
        meta: { title: '图谱搜索' },
        component: Search_v1
      },
      {
        path: "/modelv1",
        name: "modelv1",
        meta: { title: '模型构建' },
        component: Model_v1
      },
      {
        path: "/zhishiv1",
        name: "zhishiv1",
        meta: { title: '知识模型' },
        component: ZhiShi_v1
      }

    ]
  },
  // {
  //   //默认跳转
  //   path: "/home",
  //   name: "Home",
  //   component: () => import(Home)
  // },
  // // {
  // //   path: "/search",
  // //   name: "search",
  // //   component: () => import(Search_v1)
  // // },
  // {
  //   path: "/kg",
  //   name: "kgbuilderv1",
  //   meta: { title: '知识图谱' },
  //   component: () => import(Index_v1)
  // }
  // {
  //   path: "/er",
  //   name: "er",
  //   component: () => import("../views/erbuilder/index.vue")
  // },
  // {
  //   path: "/ds",
  //   name: "ds",
  //   component: () => import("../views/datasource/index.vue")
  // },
  // {
  //   path: "/icon",
  //   name: "icon",
  //   component: () => import("../views/icon/index.vue")
  // },
  // {
  //   path: "/about",
  //   name: "About",
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () =>
  //     import(/* webpackChunkName: "about" */ "../views/About.vue")
  // }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
