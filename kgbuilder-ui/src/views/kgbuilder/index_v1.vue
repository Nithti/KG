<template>
  <div class="mind-box">
    <!-- 左侧 -->
    <el-scrollbar class="mind-l">
      <h2 class="hometitle ml-ht">图谱列表</h2>
      <template>
        <div>
          <el-tree class="tree-line" :data="Tree" node-key="id" :props="defaultProps" :expand-on-click-node="false"
                   @node-click="node_click" :indent="0" ref="tree">
            <span slot-scope="{ node, data }" class="custom-tree-node" :class="{ 'is-selected': selectedNodeId === data.label }">
              <i v-if="data.type == 'catalog'" class="el-icon-folder"/>
              <i v-if="data.type == 'menu'" class="el-icon-tickets"/>
              <span>{{ node.label }}</span>
            </span>
          </el-tree>
        </div>
      </template>
    </el-scrollbar>
    <!-- 左侧over -->
    <!-- 右侧 -->
    <div class="mind-con">
      <!-- 头部工具栏 -->
      <div class="mind-top clearfix">
        <span>
          <div class="dibmr" v-show="domain != ''">
            <span>当前领域:</span>
            <span style="color:#ff0000">{{ domain }}</span>
          </div>
        </span>
        <!-- 搜索框 -->
        <!-- <div v-show="domain != ''" class="fl" style="display: flex">
          <div class="search">
            <el-input placeholder="请输入关键词" v-model="nodeName" @keyup.enter.native="getDomainGraph"></el-input>
            <el-button @click="getDomainGraph(0)">
              <svg class="icon" aria-hidden="true">
                <use xlink:href="#icon-search"></use>
              </svg>
            </el-button>
          </div>
        </div> -->
        <div class="fr" v-show="domain != ''">
          <!--<a href="javascript:void(0)" @click="showYjNums = true" class="svg-a-sm">-->
          <!--<i class="el-icon-tickets">预警等级统计</i>-->
          <!--</a>-->
          <!--<a href="javascript:void(0)" @click="showJsonData" class="svg-a-sm">-->
          <!--  <i class="el-icon-tickets">查看数据</i>-->
          <!--</a>-->
          <a href="javascript:void(0)" @click="saveImage" class="svg-a-sm">
            <i class="el-icon-camera-solid">截图</i>
          </a>
        </div>
      </div>
      <!-- 头部over -->
      <div class="lower-section">
        <!--        <div id="nodeDetail" class="node_detail">-->
        <!--          <h5>详细数据</h5>-->
        <!--          <span class="node_pd" v-for="(m, k) in nodeDetail" :key="k">{{ k }}:{{ m }}</span>-->
        <!--        </div>-->
        <!-- 中部图谱画布 -->
        <div id="graphContainer" class="graphContainer">
          <kgbuilder ref="kg_builder" :styles="style" :initData="graphData" :domain="domain" :domainId="domainId"
                     :ring-function="RingFunction" @editForm="editForm"/>
        </div>
        <div class="right-section">
          <div id="generalInfo" class="generalInfo">
            <h3 style="text-align: center; margin-top:-1%; font-size: 18px; font-weight: bold;">当前领域水库流域统计</h3>
            <el-table
              :data="generalInfo"
              :show-header="true"
              class="warning-table"
              style="width: 95%"
            >
              <el-table-column prop="count" label="水库数量" align="center" />
              <el-table-column prop="region" label="主要流域" align="center" />
            </el-table>
          </div>
          <div id="waterPredict" class="waterPredict">
            <h3 style="text-align: center; margin-top: -1%; font-size: 18px; font-weight: bold;">当前水库水位预测图谱</h3>
            <!--<a href="javascript:void(0)" @click="showLevelPrediction = true" class="svg-a-sm">-->
            <!--  <i class="el-icon-tickets" style="margin-left: 26%; font-size: large">查看水位预测及预警方案</i>-->
            <!--</a>-->
            <div id="waterLevelPrediction"></div>
          </div>
          <div id="yjContainer" class="yjContainer">
            <h3 style="text-align: center; margin-top: 1%; font-size: 18px; font-weight: bold;">当前领域水库预警等级统计</h3>
            <!-- 表格 -->
            <el-table
              :data="warningData"
              :show-header="true"
              class="warning-table"
              style="width: 95%"
            >
              <el-table-column prop="level" label="预警等级" align="center" />
              <el-table-column prop="count" label="水库数量" align="center" />
            </el-table>
            <a href="javascript:void(0)" @click="showYjNums = true" class="svg-a-sm">
              <i class="el-icon-tickets" style="text-align: center; margin-top: 2%; font-size: large; display: block">点击查看图谱</i>
            </a>
          </div>
        </div>

        <el-dialog :visible.sync="showLevelPrediction" title="水位预测" width="50%" align-center
                   id="waterLevelPredictionDialog"
                   destroy-on-close
                   :append-to-body="false"
                   :modal="false">
          <el-row justify="right">
            <el-col :span="2" :offset="20">
              <el-button @click="showEmergenceInfo">
                预警方案
              </el-button>
            </el-col>
          </el-row>
          <div id="waterLevelPrediction"></div>
        </el-dialog>

        <el-dialog :visible.sync="showYjNums" title="水库预警等级统计" @open="openstatisticbar()">
          <el-form :inline="true" size="medium">
            <el-row :gutter="10" style="align-content: center">
              <el-col :xs="24" :sm="24" :md="24" :lg="24">
                <el-form-item label="">
                  <div id="statisticbarEcharts" style="width:700px;height:400px;padding-top:40px;padding-left:40px">
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="showYjNums = false">
            确定
          </el-button>
        </span>
          </template>
        </el-dialog>
      </div>
    </div>

    <div v-loading="kgLoading">
      <kg-form ref="kg_form" @batchCreateNode="batchCreateNode" @batchCreateChildNode="batchCreateChildNode"
               @batchCreateSameNode="batchCreateSameNode" @createNode="createNode" @initNodeImage="initNodeImage"
               @initNodeContent="initNodeContent" @saveNodeImage="saveNodeImage" @saveNodeContent="saveNodeContent"
               @getDomain="getDomain">
      </kg-form>
    </div>
    <!-- 富文本展示 -->
    <div>
      <node-richer ref="node_richer"></node-richer>
    </div>
    <div>
      <kg-json ref="kg_json" :data="graphData"></kg-json>
    </div>
    <div>
      <kg-help ref="kg_help"></kg-help>
    </div>
    <div>
      <kg-wanted ref="kg_wanted"></kg-wanted>
    </div>
  </div>
</template>
<script>
import * as echarts from 'echarts';
import * as d3 from "d3";
import _ from "lodash";
import {kgBuilderApi} from "@/api";
import KgForm from "@/views/kgbuilder/components/kg_form";
import NodeRicher from "@/views/kgbuilder/components/node_richer";
import KgFocus from "@/components/KGFocus";
import KgWanted from "@/components/KGWanted";
import KgJson from "@/views/kgbuilder/components/kg_json";
import KgHelp from "@/views/kgbuilder/components/kg_help";
import html2canvas from "html2canvas";
import kgbuilder from "@/components/KGBuilder_v1";
import {EventBus} from "@/utils/event-bus.js";

export default {
  name: "kgBuilderv1",
  components: {
    KgForm,
    NodeRicher,
    KgFocus,
    KgJson,
    KgHelp,
    KgWanted,
    kgbuilder,
    // waterLevelPrediction,
  },
  provide() {
    return {
      _thisKey: this._thisKey,
      Dset: this.Dset,
      updateLinkName: this.updateLinkName,
      editLinkName: this.editLinkName,
      deleteLinkName: this.deleteLinkName,
      quickAddNodes: this.btnQuickAddNode,
      createSingleNode: this.createSingleNode,
      updateCoordinateOfNode: this.updateCoordinateOfNode,
      getNodeDetail: this.getNodeDetail,
      getMoreRelationNode: this.getMoreRelationNode
    };
  },
  data() {
    return {
      // 表格数据：预警等级和水库数量，预警等级包含正常、一级预警、二级预警、三级预警、四级预警共五种
      warningData: [
        { level: null, count: null },
        { level: null, count: null },
        { level: null, count: null },
        { level: null, count: null },
        { level: null, count: null }
      ],
      generalInfo:[{count: null, region: null}],
      amount: 40,
      style: null,
      width: null,
      height: null,
      citys: [],
      towns: [],
      counts: [],
      Tree: [], //目录
      defaultProps: '',
      treehighest: '',//目录最高权限
      RingFunction: [
        {
          title: "新建",
          icon: {
            type: "icon",
            content: "#icon-add"
          },
          defaultEvent: (d, _this, d3) => {
            // console.log("level2Group");
          },
          childrens: [
            {
              title: "点",
              icon: {
                type: "text",
                content: "点"
              },
              defaultEvent: (d, _this, d3) => {
                this.$refs.kg_form.initBatchAddChild(
                  true,
                  "batchAddChild",
                  d,
                  this.domain
                );
              },
              childrens: []
            },
            {
              title: "块",
              icon: {
                type: "text",
                content: "块"
              },
              defaultEvent: (d, _this, d3) => {
                this.$message({message: "开发中", type: "success"});
              }
            },
            {
              title: "集",
              icon: {
                type: "text",
                content: "集"
              },
              defaultEvent: (d, _this, d3) => {
                this.$message({message: "开发中", type: "success"});
              }
            }
          ]
        },
        {
          title: "编辑",
          icon: {
            type: "icon",
            content: "#icon-editor"
          },
          defaultEvent: (d, _this, d3) => {
            _this.$nextTick(() => {
              let formNode = {
                uuid: d.uuid,
                name: d.name,
                r: parseInt(d.r),
                color: d.color
              };
              _this.$emit(
                "editForm",
                true,
                "nodeEdit",
                formNode,
                _this.domainId
              );
            });
          },
          childrens: []
        },
        {
          title: "展开",
          icon: {
            type: "icon",
            content: "#icon-salescenter-fill"
          },
          defaultEvent: (d, _this, d3) => {
            let data = {domain: _this.domain, nodeId: d.uuid};
            //如果是搜索的节点，要给domain赋值
            if (data.domain === '') {
              data.domain = this.treehighest
            }
            //console.log(data)
            kgBuilderApi.getMoreRelationNode(data).then(result => {
              console.log(result)
              if (result.code == 200) {
                //把不存在于画布的节点添加到画布
                this.mergeNodeAndLink(
                  result.data.node,
                  result.data.relationship
                );
                //重新绘制
                //_this.updateGraph();

              } else {
                _this.$message.error("展开失败 :" + item.executionTime);
              }

            });
          },
          childrens: []
        },
        {
          title: "预测",
          icon: {
            type: "icon",
            content: "#icon-tradingvolume"
          },
          defaultEvent: (d, _this, d3) => {
            console.log(d, _this, d3)
            let data = {domain: _this.domain, nodeId: d.uuid};
            //如果是搜索的节点，要给domain赋值
            if (data.domain === '') {
              data.domain = this.treehighest
            }
            // this.showLevelPrediction = true
            this.showWaterLevelPrediction(d, data)
          },
          childrens: []
        },
        {
          title: "连线",
          icon: {
            type: "icon",
            content: "#icon-link"
          },
          defaultEvent: (data, _this, d3) => {
            this.createLink(data);
            //_this.updateGraph();
          },
          childrens: []
        },
        {
          title: "预警方案",
          icon: {
            type: "icon",
            content: "#icon-docjpge-fill"
          },
          defaultEvent: (d, _this, d3) => {
            //如果是搜索的节点，要给domain赋值
            if (this.domain === '') {
              this.domain = this.treehighest
            }  //给this.domain赋值，之后面板信息会有，否则还是没有
            this.$refs.kg_form.getYujingfangan(
              true,
              "yujingfangan",
              d,
              this.domain
            );
          },
          childrens: []
        },
      ],
      _thisView: null,
      timer: null,
      tooltip: null,
      nodeDetail: null,
      pageSizeList: [
        {size: 500, isActive: true},
        {size: 1000, isActive: false},
        {size: 2000, isActive: false},
        {size: 5000, isActive: false}
      ],
      domain: "",
      domainId: 0,
      nodeName: "",
      nodeLevel: "",//水位
      pageSize: 500,
      activeNode: null,
      nodeImageList: [],
      showImageList: [],
      editorContent: "",
      selectNodeData: [],//{}, // 选中节点的详细信息展示
      selectNodeRelationData: [],
      cardName: "",//面板标题
      showCard: false,
      nodes: [],
      links: [],
      color: "",
      waterLevelId: 0,
      waterLevelData: [], //水位数据
      waterLevelRelationship: [], //水位数据关系
      isCollapse: false,
      citynode: [],
      jinanshiNodeNum: [],
      yujingLevel: "",//预警等级
      river: "",//所在流域
      pageModel: {
        pageIndex: 1,
        pageSize: 200,//nodelist数量
        totalCount: 0,
        totalPage: 0,
        nodeList: []
      },
      graphData: {
        nodes: [],
        links: []
      },
      jsonShow: false,
      helpShow: false,
      showYjNums: false,
      yjNums: [],
      kgLoading: false,
      showLevelPrediction: false,
      selectedNodeId: null, // 记录当前点击节点的 id, 然后让其变色
      lastClickedCountyNodeId: null, // 记录最近点击的县级节点的 id
      lastClickedCityNodeId: null, // 记录最近点击的县级节点的父节点（市级节点）的 id
      expandedKeys: [] // 控制展开的节点
    };
  },
  filters: {
    labelFormat: function (value) {
      let domain = value.substring(1, value.length - 1);
      return domain;
    }
  },
  mounted() {

  },
  created() {
    // console.log(this.graphData)
    this.getDomain();
    this.$nextTick(() => {
      this.width = document.getElementsByClassName(
        "graphContainer"
      )[0].offsetWidth;
      console.log(this.width);
      //this.height = document.getElementsByClassName('graphContainer')[0].offsetHeight
      this.height = window.screen.height;
      console.log("height" + this.height);
      this.style = {
        width: this.width + "px",
        height: this.height + "px"
      };
      // console.log(this.style)
      EventBus.$emit("DIV", this.width, this.height);
    });
    // this.calculateNodeNum();
    var strs = [];
    var url = window.location.href;
    console.log("url:")
    console.log(url);
    var start = url.indexOf("adcd=")
    var end = url.length
    var adcd_url = url.slice(start, end)
    console.log(adcd_url)
    strs = adcd_url.split("=");
    console.log(strs[1]);
    var adcd = strs[1];
    if (!adcd)
      adcd = 370000;
    let data = {
      adcd: adcd,
    };

    kgBuilderApi.getLocation(data).then(result => {
      console.log("getLocation返回的数据如下：")
      console.log("data[0]" + result.data[0], "data[1]" + result.data[1], "data[2]" + result.data[2], "data[3]" + result.data[3],)

      let num = result.data.length;
      console.log(num)

      var all = 0
      for (let i = 0; i < result.data[2].length; i++) {
        all = parseInt(all) + parseInt(result.data[2][i])
      }
      // let town = 0;
      // let coun = 0;
      //处理城市数据

      this.citys = result.data[0]  //citys是市一级
      for (let i = 0; i < this.citys.length; i++) {
        var town = []
        var count = []
        for (let k = 0; k < result.data[3][i]; k++) {
          town.push(result.data[1].shift());
          count.push(result.data[2].shift())
        }
        this.towns.push(town)  // town是市下面的区县
        this.counts.push(count)  // count是每个区县的水库的总数
      }
      if (this.citys.length > 1) {
        this.Tree = [{
          label: '山东省' + '  (' + all + ')',
          children: []
        }]
      }
      for (let i = 0; i < this.citys.length; i++) {
        var child = []
        var total = 0
        for (let j = 0; j < this.towns[i].length; j++) {
          var item = {label: this.towns[i][j] + '  (' + this.counts[i][j] + ')'}
          child.push(item)
          total = parseInt(total) + parseInt(this.counts[i][j])
        }
        var city = {
          label: this.citys[i] + '  (' + total + ')',
          children: child
        }
        if (this.citys.length == 1) {
          this.Tree.push(city)
        } else {
          this.Tree[0].children.push(city)
        }

        all = parseInt(total) + parseInt(all)
      }
      this.treehighest = this.Tree[0].label.split(' ')[0]; //使用 split(' ') 方法将字符串按空格分割成数组，再取得数组的第一个元素
      console.log('tree')
      console.log(this.Tree)
      // console.log(JSON.stringify(this.Tree))
      console.log('treehighest')
      console.log(this.treehighest)
      console.log('citys')
      console.log(this.citys)
      console.log('towns')
      console.log(this.towns)
      console.log('counts')
      console.log(this.counts)
      var _this = this
      //统计预警水库
      // kgBuilderApi.getYjNum().then(result => {
      //   _this.yjNums = result;
      //   console.log(_this.yjNums, result)
      //   _this.showYjNums = true
      // })

      if (this.$route.query.param) {
        //搜索页面跳转过来展示结果，放在这里是因为需要获取Tree的最高层节点(权限)
        let param = this.$route.query.param;//接收搜索页面传来的参数
        if (this.treehighest === '山东省') {
          this.treehighest = 'ba'
        }
        //必须更改domain的值，要根据权限搜，而不是整个山东省
        param.domain = this.treehighest
        console.log('param   ' + JSON.stringify(param))
        this.getSearchDomainGraph(param); // 调用目标页面的函数，并传递参数
      }

    });
  },
  methods: {
    _thisKey(item) {
      this._thisView = item;
    },
    Dset(item) {
      this.d3 = item;
    },
    prev() {
      if (this.pageModel.pageIndex > 1) {
        this.pageModel.pageIndex--;
        this.getDomain();
      }
    },
    next() {
      if (this.pageModel.pageIndex < this.pageModel.totalPage) {
        this.pageModel.pageIndex++;
        this.getDomain();
      }
    },
    editForm(flag, action, data, domainId) {
      this.$refs.kg_form.initNode(flag, action, data, domainId);
    },
    openstatisticbar() {
      this.$nextTick(() => {
        //  执行echarts方法
        this.initstatisticbarEcharts()
      })
    },
    // 创建方法：直方图
    initstatisticbarEcharts() {
      // 基于准备好的dom，初始化echarts实例
      const myChart = echarts.init(document.getElementById('statisticbarEcharts'));
      // 绘制图表
      const option = {
        title: {
          //text: '应收 vs 实收',
          //subtext: 'Fake Data'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            // name: '/等级',
            data: ['正常', '一级预警', '二级预警', '三级预警', '四级预警'], //this.xData
            axisTick: {
              alignWithLabel: true
            }
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '/座'
          }
        ],
        series: [
          {
            name: '水库数量',
            type: 'bar',
            barWidth: '60%',
            data: this.yjNums,
            label: {
              show: true,
              position: 'top',
              formatter: '{c}'
            }
          }
        ]
      };
      myChart.setOption(option)
    },
    calculateNodeNum() {
      // kgBuilderApi.getDomains().then(result => {
      //   if (result.code == 200) {
      //     if (result.data != null) {
      //       this.citynode = result.data.nodeList
      //     }
      //     console.log("nodenum", _this.nodes.length)
      //     for (let i = 0; this.jinanshi[i] != null; i++) {
      //       const item = this.citynode.find(
      //         (item1) => item1.name === this.jinanshi[i]
      //       );
      //       this.domain = item.name;
      //       //console.log("domain.name"+this.domain)
      //       this.domainId = item.id;
      //       this.getDomainGraph();
      //       //this.jinanshiNodeNum[i]
      //     }
      //   }
      // });
      this.getDomain();
      console.log(this.pageModel.nodeList)
      for (let i = 0; this.jinanshi[i] != null; i++) {
        const item = this.pageModel.nodeList.find(
          (item1) => item1.name === this.jinanshi[i]
        );
        this.domain = item.name;
        //console.log("domain.name"+this.domain)
        this.domainId = item.id;
        console.log('id', this.domainId)
        this.getDomainGraph();
      }
    },
    //动态二级目录
    selectCityByValue(value) {
      var item = this.pageModel.nodeList.find(
        (item1) => item1.name === value, //"德州市"
        //console.log("baocuo")
      );

      this.domain = item.name;
      //console.log("domain.name"+this.domain)
      this.domainId = item.id;
      this.getDomainGraph();
      //const num = this.getDomainGraph().node.length

      // this.pageModel.nodeList.map(n => {
      //   if (n.name == item.name) {
      //     n.type = "success";
      //     //console.log("success.name"+n.name)
      //   } else {
      //     n.type = "";
      //   }
      //   return n;
      // });
    },
    //二级目录连接
    selectCity(value) {
      //this.count = count
      //this.matchDomainGraph(m,$event);
      //console.log(this.pageModel.nodeList)
      // console.log('selectCity', value)
      this.showCard = false
      this.kgLoading = true
      var item = this.pageModel.nodeList.find(
        (item1) => item1.name === value, //"德州市"
        //console.log("baocuo")
      );
      this.domain = item.name;
      //console.log("domain.name"+this.domain)
      this.domainId = item.id;
      console.log('Did', this.domainId)
      this.getDomainGraph();
      this.kgLoading = false

    },
    node_click(data, e) {
      this.selectedNodeId = e.label

      console.log("handleNodeClick", e, data);
      if (e.childNodes.length == 0) {
        this.selectCity(e.parent.label.split(' ')[0] + data.label.split(' ')[0])
      }
      e.childNodes; //子节点
      e.parent; //父节点

    },
    //创建节点
    createNode(graphNode) {
      let data = graphNode;
      console.log(data)
      //console.log(data.color)
      data.domain = this.domain;
      let _this = this;
      kgBuilderApi.createNode(data).then(result => {
        if (result.code == 200) {
          //删除旧节点，由于我们改变的是属性，不是uuid,此处我们需要更新属性，或者删除节点重新添加
          let newNode = result.data;
          // console.log(JSON.stringify(result.data))
          for (let i = 0; i < _this.graphData.nodes.length; i++) {
            if (_this.graphData.nodes[i].uuid == graphNode.uuid) {
              console.log(JSON.stringify(_this.graphData.nodes[i]))
              _this.graphData.nodes.splice(i, 1);
            }
          }
          _this.graphData.nodes.push(newNode);
        }
      });
    },
    saveNodeImage(data) {
      let image = data.imagePath;
      let nodeId = data.nodeId;
      let _this = this;
      kgBuilderApi.saveNodeImage(JSON.stringify(data)).then(result => {
        if (result.code == 200) {
          _this.graphData.nodes
            .filter(n => n.uuid == nodeId)
            .map(m => {
              m.image = image;
              return m;
            });
          _this.$message({
            message: "操作成功",
            type: "success"
          });
        }
      });
    },
    //上传富文本
    saveNodeContent(data) {
      kgBuilderApi.saveNodeContent(JSON.stringify(data)).then(result => {
        if (result.code == 200) {
          this.$message({message: "操作成功", type: "success"});
        }
      });
    },
    //画布直接添加节点
    createSingleNode(left, top) {
      let data = {name: "", r: 30};
      data.domain = this.domain;
      kgBuilderApi.createNode(data).then(result => {
        if (result.code == 200) {
          let newNode = result.data;

          _.assignIn(newNode, {
            x: left,
            y: top,
            fx: left,
            fy: top,
            r: parseInt(newNode.r),
            image: ""
          });
          this.graphData.nodes.push(newNode);
        }
      });
    },
    updateCoordinateOfNode(nodes) {
      let data = {domain: this.domain, nodes: nodes};
      kgBuilderApi.updateCoordinateOfNode(data).then(result => {
      });
    },
    //删除节点
    deleteNode(out_buttongroup_id) {
      let _this = this;
      _this
        .$confirm(
          "此操作将删除该节点及周边关系(不可恢复), 是否继续?",
          "三思而后行",
          {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }
        )
        .then(function () {
          let data = {domain: _this.domain, nodeId: _this.selectNode.nodeId};
          kgBuilderApi.deleteNode(data).then(result => {
            if (result.code == 200) {
              _this.svg.selectAll(out_buttongroup_id).remove();
              let rShips = result.data;
              // 删除节点对应的关系
              for (let m = 0; m < rShips.length; m++) {
                for (let i = 0; i < _this.graphData.links.length; i++) {
                  if (_this.graphData.links[i].uuid == rShips[m].uuid) {
                    _this.graphData.links.splice(i, 1);
                    i = i - 1;
                  }
                }
              }
              // 找到对应的节点索引
              let j = -1;
              for (let i = 0; i < _this.graphData.nodes.length; i++) {
                if (_this.graphData.nodes[i].uuid == _this.selectNode.nodeId) {
                  j = i;
                  break;
                }
              }
              if (j >= 0) {
                _this.selectNode.nodeId = 0;
                _this.graphData.nodes.splice(j, 1); // 根据索引删除该节点
                //_this.updateGraph();
                _this.$message({
                  type: "success",
                  message: "操作成功!"
                });
              }
            }
          });
        })
        .catch(function () {
          _this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    //删除连线
    deleteLinkName(sdata) {
      let _this = this;
      _this
        .$confirm("此操作将删除该关系(不可恢复), 是否继续?", "三思而后行", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
        .then(function () {
          let data = {domain: _this.domain, shipId: sdata.uuid};
          kgBuilderApi.deleteLink(data).then(result => {
            if (result.code == 200) {
              let j = -1;
              for (let i = 0; i < _this.graphData.links.length; i++) {
                if (_this.graphData.links[i].uuid == sdata.uuid) {
                  j = i;
                  break;
                }
              }
              if (j >= 0) {
                _this.graphData.links.splice(j, 1);
              }
            }
          });
        })
        .catch(function () {
          _this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    //添加连线
    createLink(data) {
      kgBuilderApi.createLink(data).then(result => {
        if (result.code == 200) {
          let newShip = result.data;
          this.graphData.links.push(newShip);
        }
      });
    },
    //更新连线名称
    updateLinkName(sdata) {
      let _this = this;
      this.$prompt("请输入关系名称", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputValue: sdata.cname
      })
        .then(function (res) {
          let value = res.value;
          let data = {
            domain: _this.domain,
            shipId: sdata.uuid,
            shipName: value
          };
          kgBuilderApi.updateLink(data).then(result => {
            if (result.code == 200) {
              let newShip = result.data;
              _this.graphData.links.forEach(function (m) {
                if (m.uuid == newShip.uuid) {
                  m.name = newShip.name;
                }
              });
            }
          });
        })
        .catch(function () {
        });
    },
    //更新节点名称
    updateNodeName(d) {
      let _this = this;
      _this
        .$prompt("编辑节点名称", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          inputValue: d.name
        })
        .then(function (res) {
          let value = res.value;
          let data = {domain: _this.domain, nodeId: d.uuid, nodeName: value};
          kgBuilderApi.updateNodeName(data).then(result => {
            if (result.code == 200) {
              if (d.uuid != 0) {
                for (let i = 0; i < _this.graphData.nodes.length; i++) {
                  if (_this.graphData.nodes[i].uuid == d.uuid) {
                    _this.graphData.nodes[i].name = value;
                  }
                }
              }
              //_this.updateGraph();
              _this.$message({
                message: "操作成功",
                type: "success"
              });
            }
          });
        })
        .catch(function () {
          _this.$message({
            type: "info",
            message: "取消操作"
          });
        });
    },
    //初始化节点富文本内容
    initNodeContent(data) {
      let param = {domainId: data.domainId, nodeId: data.nodeId};
      kgBuilderApi.getNodeContent(param).then(response => {
        if (response.code == 200) {
          if (response.data) {
            this.$refs.kg_form.initContent(response.data.content);
          } else {
            this.$message.warning("暂时没有更多数据");
          }
        }
      });
    },
    //初始化节点添加的图片
    initNodeImage(data) {
      let param = {domainId: data.domainId, nodeId: data.nodeId};
      kgBuilderApi.getNodeImage(param).then(response => {
        if (response.code == 200) {
          if (response.data) {
            let nodeImageList = [];
            for (let i = 0; i < response.data.length; i++) {
              nodeImageList.push({
                file: response.data[i].fileName,
                imageType: response.data[i].imageType
              });
              this.$refs.kg_form.initImage(nodeImageList);
            }
          } else {
            this.$message.warning("暂时没有更多数据");
          }
        }
      });
    },
    //一次性获取富文本和图片
    getNodeDetail(nodeId, left, top) {
      let data = {domainId: this.domainId, nodeId: nodeId};
      //   // kgBuilderApi.getNodeDetail(data).then(result => {
      //   //   if (result.code == 200) {
      //   //     if (result.data) {
      //   //       this.$refs.node_richer.init(
      //   //         result.data.content,
      //   //         result.data.imageList,
      //   //         left,
      //   //         top
      //   //       );
      //   //     } else {
      //   //       this.$message.warning("暂时没有更多数据");
      //   //     }
      //   //   }
      //   // });

      //获取面板信息
      data = {domain: this.domain, nodeId: nodeId};
      kgBuilderApi.getMoreRelationNode(data).then(result => {
        if (result.code == 200) {
          console.log(result)
          this.selectNodeData = result.data.node
          this.selectNodeRelationData = result.data.relationship
          this.cardName = this.selectNodeData[1].name

          if (this.cardName.indexOf("水库") > 0) {
            this.showCard = true
          } else {
            this.showCard = false
          }
          this.color = result.data.node[1].color
          this.yujingLevel = result.data.node[2].yj//预警等级
          this.river = this.selectNodeData[5].name//所在流域
          this.selectNodeData.splice(1, 1)  //删除水库名称节点
          this.waterLevelId = result.data.node[3].uuid
          let leveldata = {domain: this.domain, nodeId: this.waterLevelId};
          kgBuilderApi.getMoreRelationNode(leveldata).then(result => {
            if (result.code == 200) {
              this.waterLevelData = result.data.node
              this.waterLevelRelationship = result.data.relationship
              this.waterLevelData.splice(1, 1)
              this.waterLevelData.splice(1, 1) //删除水库名称节点
              this.waterLevelRelationship.splice(1, 1)
            }
          });
        }
      });
    },
    //全屏
    requestFullScreen() {
      let element = document.getElementById("graphcontainerdiv");
      let width = window.screen.width;
      let height = window.screen.height;
      this.svg.attr("width", width);
      this.svg.attr("height", height);
      if (element.requestFullscreen) {
        element.requestFullscreen();
      }
      // FireFox
      else if (element.mozRequestFullScreen) {
        element.mozRequestFullScreen();
      }
      // Chrome等
      else if (element.webkitRequestFullScreen) {
        element.webkitRequestFullScreen();
      }
      // IE11
      else if (element.msRequestFullscreen) {
        element.msRequestFullscreen();
      }
    },
    //获取图谱节点及关系
    getDomainGraph() {
      let data = {
        domain: this.domain,
        nodeName: this.nodeName,
        pageSize: this.pageSize
      };
      let _this = this;
      kgBuilderApi.getDomainGraph(data).then(result => {
        let yjCounts = { '正常':0, '一级': 0, '二级': 0, '三级': 0, '四级': 0 }
        let region = [];
        // let newyjCounts = this.calculateYJLevelCounts(data.domain, result, yjCounts)    // 统计每一种预警等级下的水库总数
        this.calculateYJLevelCounts(data.domain, result, yjCounts, region).then(newyjCounts => {
          _this.warningData[0].count  = newyjCounts.yjCounts['正常'];
          _this.warningData[0].level = "正常";
          _this.warningData[1].count = newyjCounts.yjCounts['一级'];
          _this.warningData[1].level = "一级预警";
          _this.warningData[2].count = newyjCounts.yjCounts['二级'];
          _this.warningData[2].level = "二级预警";
          _this.warningData[3].count = newyjCounts.yjCounts['三级'];
          _this.warningData[3].level = "三级预警";
          _this.warningData[4].count = newyjCounts.yjCounts['四级'];
          _this.warningData[4].level = "四级预警";
          _this.yjNums = [newyjCounts.yjCounts['正常'],newyjCounts.yjCounts['一级'],newyjCounts.yjCounts['二级'],
            newyjCounts.yjCounts['三级'],newyjCounts.yjCounts['四级']]
          _this.generalInfo[0].region = newyjCounts.region[0];
        });
        _this.generalInfo[0].count = result.data.node.length  //统计当前领域下水库的总数

        if (result.code == 200) {
          if (result.data != null) {
            _this.graphData = {nodes: [], links: []};
            _this.graphData.nodes = result.data.node;
            _this.graphData.links = result.data.relationship;
            _this.links = _this.graphData.links
            _this.nodes = _this.graphData.nodes
          }
          console.log(_this.graphData);
          console.log("水库节点的数量nodeNum为", _this.nodes.length)
        }
      });
    },
    calculateYJLevelCounts(domain, result, yjCounts, region) {
      let nodes = result.data.node;
      let temp = { '正常': '正常', 1: '一级', 2: '二级', 3: '三级', 4: '四级' };
      let promises = nodes.map(node => {
        let nodeId = node.uuid;
        let data = { domain: domain, nodeId: nodeId };
        return kgBuilderApi.getMoreRelationNode(data).then(result => {
          if (result.code === 200 && result.data.node.length > 0) {
            let yujingLevel = result.data.node[0].yj; // 预警等级
            let regionTemp = result.data.node[3].bas;  // 途径流域 (索引 `0`)
            if (temp.hasOwnProperty(yujingLevel)) {
              return { yujingLevel: temp[yujingLevel], regionTempKey: regionTemp }; // 返回对象
            }
          }
          return null; // 处理异常情况
        });
      });

      return Promise.all(promises).then(results => {
        results.forEach(item => {
          if (item !== null) {
            yjCounts[item.yujingLevel] += 1;
            region.push(item.regionTempKey); // 追加到 region 数组
          }
        });
        let filteredRegion = [...new Set(region)];  //去除重复的流域
        region = filteredRegion;
        return { yjCounts, region }; // 返回包含两个数据的对象
      });
    },
    //搜索节点展示
    getSearchDomainGraph(data) {
      let _this = this;
      // console.log(this.treehighest)
      // data.domain = this.treehighest;
      // console.log('searchdata   '+ JSON.stringify(data))
      kgBuilderApi.getDomainGraph(data).then(result => {
        // console.log('22222'+JSON.stringify(data))
        if (result.code == 200) {
          if (result.data != null) {
            _this.graphData = {nodes: [], links: []};
            _this.graphData.nodes = result.data.node;
            _this.graphData.links = result.data.relationship;
            _this.links = _this.graphData.links
            _this.nodes = _this.graphData.nodes
          }
          console.log(_this.graphData);
          console.log("searchnodenum", _this.nodes.length)
        }
      });
    },
    //展开更多节点
    getMoreNode() {
      let data = {domain: this.domain, nodeId: this.selectNode.nodeId};
      kgBuilderApi.getMoreRelationNode(data).then(result => {
        if (result.code == 200) {
          //把不存在于画布的节点添加到画布
          this.mergeNodeAndLink(result.data.node, result.data.relationship);
          //this.selectNodeData=result.data.node
          //重新绘制
          // this.simulation.force("collide", null);
          // this.updateGraph();

        }
      });
    },
    //快速添加
    btnQuickAddNode() {

      this.$refs.kg_form.init(true, "batchAdd", this.domain);
    },
    //删除领域
    deleteDomain(id, value) {
      this.$confirm(
        "此操作将删除该标签及其下节点和关系(不可恢复), 是否继续?",
        "三思而后行",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(function (res) {
          let data = {domainId: id, domain: value};
          kgBuilderApi.deleteDomain(data).then(result => {
            if (result.code == 200) {
              this.getDomain();
              this.domain = "";
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    //创建新领域
    createDomain(value) {
      this.$prompt("请输入领域名称", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消"
      })
        .then(res => {
          value = res.value;
          let data = {domain: value, type: 0};
          kgBuilderApi.createDomain(data).then(result => {
            if (result.code == 200) {
              this.getDomain();
              this.domain = value;
              this.getDomainGraph();
            }
          });
        })
        .catch(() => {
        });
    },
    //获取领域标签
    getLabels(data) {
      kgBuilderApi.getDomains(data).then(result => {
        if (result.code == 200) {
          this.pageModel = result.data;
          this.pageModel.totalPage =
            parseInt((result.data.totalCount - 1) / result.data.pageSize) + 1;
          this.pageModel.nodeList.map(n => {
            n.type = "";
            //console.log("n.name"+n.name)
            return n;
          });
        }
        //console.log(this.pageModel.nodeList)
        //获取水库节点数量
        // for (let i = 0; this.jinanshi[i] != null; i++) {
        //   const item = this.pageModel.nodeList.find(
        //     (item1) => item1.name === this.jinanshi[i]
        //   );
        //   this.domain = item.name;
        //   //console.log("domain.name"+this.domain)
        //   this.domainId = item.id;
        //   this.getDomainGraph();
        // }
      });
    },
    getDomain(pageIndex) {
      this.pageModel.pageIndex = pageIndex
        ? pageIndex
        : this.pageModel.pageIndex;
      let data = {
        pageIndex: this.pageModel.pageIndex,
        pageSize: this.pageModel.pageSize,
        command: 0
      };
      this.getLabels(data);
    },
    matchDomainGraph(domain) {
      this.domain = domain.name;
      //console.log("domain.name"+domain.name)
      this.domainId = domain.id;
      this.getDomainGraph();
      this.pageModel.nodeList.map(n => {
        if (n.name == domain.name) {
          n.type = "success";
          //console.log("success.name"+n.name)
        } else {
          n.type = "";
        }
        return n;
      });
    },
    //保存图片
    saveImage() {
      html2canvas(document.querySelector(".graphContainer"), {
        width: document.querySelector(".graphContainer").offsetWidth, // canvas画板的宽度 一般都是要保存的那个dom的宽度
        height: document.querySelector(".graphContainer").offsetHeight, // canvas画板的高度  同上
        scale: 1
      }).then(function (canvas) {
        let a = document.createElement("a");
        a.href = canvas.toDataURL("image/png"); //将画布内的信息导出为png图片数据
        let timeStamp = Date.parse(new Date());
        a.download = timeStamp; //设定下载名称
        a.click(); //点击触发下载
      });
    },
    showJsonData() {
      this.$refs.kg_json.init();
    },
    wanted() {
      this.$refs.kg_wanted.init();
    },
    //导入图谱
    importGraph() {
      if (!this.domain || this.domain == "") {
        this.$message.warning("请选择一个领域");
        return;
      }
      this.$refs.kg_form.init(true, "import", this.domain);

    },
    exportGraph() {
      if (!this.domain || this.domain == "") {
        this.$message.warning("请选择一个领域");
        return;
      }
      let data = {domain: this.domain};
      kgBuilderApi.exportGraph(data).then(result => {
        if (result.code == 200) {
          window.location.href = result.fileName;
        }
      });
    },
    help() {
      this.$refs.kg_help.init();
    },
    //设置画布内最大的点个数
    setMatchSize(m) {
      for (let i = 0; i < this.pageSizeList.length; i++) {
        this.pageSizeList[i].isActive = false;
        if (this.pageSizeList[i].size == m.size) {
          this.pageSizeList[i].isActive = true;
        }
      }
      this.pageSize = m.size;
      this.getDomainGraph();
    },
    //合并节点和连线
    mergeNodeAndLink(newNodes, newLinks) {
      let _this = this;
      newNodes.forEach(function (m) {
        let sobj = _this.graphData.nodes.find(function (x) {
          return x.uuid === m.uuid;
        });
        if (typeof sobj == "undefined") {
          _this.graphData.nodes.push(m);
        }
      });
      newLinks.forEach(function (m) {
        let sobj = _this.graphData.links.find(function (x) {
          return x.uuid === m.uuid;
        });
        if (typeof sobj == "undefined") {
          _this.graphData.links.push(m);
        }
      });
      var svg = d3.select('svg');
      var width = svg.attr('width');
      var height = svg.attr('height');
      var forceSimulation = d3.forceSimulation(_this.graphData.nodes)
        .force("link", d3.forceLink().id(d => d.uuid))
      //  .force("charge", d3.forceManyBody().strength(-100))
      //  .force("center", d3.forceCenter(svg.node().parentElement.clientWidth / 2, svg.node().parentElement.clientHeight / 2));
      //forceSimulation.start()
      //设置图形 中心点
      // forceSimulation.force('center')
      //   .x(width/2)//设置x坐标
      //   .y(height/2)//设置y坐标
      //console.log("33")
    },
    //批量添加节点
    batchCreateNode(param) {
      let data = {
        domain: this.domain,
        sourceName: param.sourceNodeName,
        targetNames: param.targetNodeNames,
        relation: param.relation
      };
      kgBuilderApi.batchCreateNode(data).then(result => {
        if (result.code == 200) {
          //把不存在于画布的节点添加到画布
          this.mergeNodeAndLink(result.data.nodes, result.data.ships);
          //重新绘制
          //this.updateGraph();
          this.$message({
            message: "操作成功",
            type: "success"
          });
        }
      });
    },
    //批量添加子节点
    batchCreateChildNode(param) {
      let data = {
        domain: this.domain,
        sourceId: param.sourceUuid,
        targetNames: param.targetNodeNames,
        relation: param.relation
      };
      kgBuilderApi.batchCreateChildNode(data).then(result => {
        if (result.code == 200) {
          //把不存在于画布的节点添加到画布
          this.mergeNodeAndLink(result.data.nodes, result.data.ships);
          //重新绘制
          this.$message({
            message: "操作成功",
            type: "success"
          });
        }
      });
    },
    //批量添加同级节点
    batchCreateSameNode(param) {
      let data = {
        domain: this.domain,
        sourceNames: param.sourceNodeName
      };
      kgBuilderApi.batchCreateSameNode(data).then(result => {
        if (result.code == 200) {
          //把不存在于画布的节点添加到画布
          this.mergeNodeAndLink(result.data, null);
          this.$message({
            message: "操作成功",
            type: "success"
          });
        }
      });
    },
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    //水位预测
    showWaterLevelPrediction(d, data) {
      this.nodeId = data.nodeId
      var that = this
      kgBuilderApi.getMoreRelationNode(data).then(result => {
        if (result.code == 200) {
          var wrz = result.data.node[4].wrz //汛限水位
          new Promise(function (resolve, reject) {
            setTimeout(() => {
              var chartDom = document.getElementById('waterLevelPrediction');
              chartDom.setAttribute('style', 'height:' + that.height * 0.25 + 'px; margin-left:5%; width:' + that.width * 0.4 + 'px;margin-top:-6%')
              //chartDom.setAttribute('style', 'height:' + that.height+'%; margin:auto; width:' + that.width+'%')
              if (chartDom != null) {
                resolve(chartDom);
              } else {
                reject();
              }
            }, 200);
          }).then(function (chartDom) {
            var myChart = echarts.init(chartDom);
            var option;
            var r_data = {
              res_cd: d.res_cd,
            }
            kgBuilderApi.getWaterLevelPrediction(r_data).then((res) => {
              option = {
                // title: { text: _this.res_nm },
                tooltip: {
                  trigger: 'axis',
                  axisPointer: {
                    type: 'cross'
                  }
                },
                grid: {
                  left: '10%', // 保持 grid 不变，确保绘图区域不变
                  right: '10%',
                  top: '15%',
                  bottom: '15%'
                },
                xAxis: {
                  name: '时间',
                  type: 'category',
                  axisLabel: {
                    fontSize: 10 // 缩小 X 轴字体
                  },
                  nameTextStyle: {
                    fontSize: 10 // 缩小 X 轴名称字体
                  },
                  // boundaryGap: false,
                  data: []
                },
                yAxis: {
                  name: '水位',
                  type: 'value',
                  min: 0, // 设置 Y 轴最小值
                  max: 60, // 设置 Y 轴最大值
                  axisLabel: {
                    formatter: '{value} m',
                    fontSize: 10, // 缩小 Y 轴字体
                  },
                  nameTextStyle: {
                    fontSize: 10 // 缩小 Y 轴名称字体
                  },
                  axisTick: {
                    length: 3 // 缩短刻度线的长度，使视觉间距更小
                  },
                  axisLine: {
                    lineStyle: {
                      width: 1 // 保持轴线宽度
                    }
                  }
                },
                visualMap: {
                  show: false,
                  dimension: 0,
                  pieces: [
                    {
                      lte: 20,
                      color: 'green'
                    },
                    {
                      gt: 20,
                      lte: 30,
                      color: 'blue'
                    }
                  ]
                },
                series: [
                  {
                    name: '水位',
                    data: res.data,
                    type: 'line',
                    symbol: 'circle', // 保持数据点样式不变
                    symbolSize: 4, // 保持数据点大小不变
                    markLine: {
                      // silent: true,
                      data: [{yAxis: wrz}],
                      symbol: ['none', 'none'],
                      label: {
                        position: 'insideEnd',
                        formatter: '汛限\n水位',
                        color: 'red'
                      },
                      lineStyle: {
                        color: 'red',
                        // width: 2  // 保持折线粗细不变（默认值）
                      }
                    }
                  }
                ]
              }
              option && myChart.setOption(option);
            })
          });
        }
      });
    },
    showEmergenceInfo() {
      var node = {
        uuid: this.nodeId,
      }
      this.$refs.kg_form.getYujingfangan(true, 'allLevel', node, this.domain);
    }
    //d3初始化
    // d3init() {
    //   this.d3render()
    //   // 数据状态初始化
    //   //this.stateInit()
    // },
    // //利用d3.forceSimulation()定义关系图 包括设置边link、排斥电荷charge、关系图中心点
    // d3render() {
    //   var simulation = d3.forceSimulation(this.nodes)
    //     // .force("link", d3.forceLink().id(d => d.id))
    //     .force("charge", d3.forceManyBody().strength(-100))
    //   // .force("center", d3.forceCenter(width / 2, height / 2)
    //   // .force("center", d3.forceCenter(svg.node().parentElement.clientWidth / 2, svg.node().parentElement.clientHeight / 2))
    //   // .force("collision", forceCollide)
    //   simulation.on("tick", ticked)
    // }
  }
};
</script>
<style>
@import '../styles/index_v1.css';
</style>
<style>
/* 调整表格样式 */
/* 表格样式 */
/* 表格样式 */
.warning-table {
  width: auto; /* 表格宽度自适应内容 */
  margin: 10px; /* 四个方向与边界保持 10px 距离 */
}

/* 统一表头和单元格背景色，并确保居中 */
.el-table >>> th,
.el-table >>> td {
  background-color: #f5f7fa; /* 统一背景色 */
  text-align: center !important; /* 强制水平居中 */
  vertical-align: middle !important; /* 强制垂直居中 */
  padding: 5px; /* 减少内边距 */
}

/* 表头字体加粗 */
.el-table >>> th {
  font-weight: bold; /* 表头加粗 */
  color: #333; /* 表头字体颜色 */
}

/* 单元格字体样式 */
.el-table >>> td {
  font-weight: normal; /* 单元格字体正常 */
  color: #606266; /* 单元格字体颜色 */
}

/* 去除表格的固定高度，让表格高度自适应内容 */
.el-table >>> .el-table,
.el-table >>> .el-table__body-wrapper,
.el-table >>> .el-table__header-wrapper {
  height: auto !important; /* 高度自适应内容 */
}
/* 确保表格整体高度由内容决定 */
.el-table >>> .el-table__body,
.el-table >>> .el-table__header {
  width: auto !important; /* 宽度自适应内容 */
}
</style>
<style>
body {
  height: 100%;
  margin: 0;
  padding: 0;
}
</style>

<style lang="scss" scoped>
@import '../styles/index_v1.scss';
</style>

<style lang="scss">
.tree-line {
  .el-tree-node {
    position: relative;
    padding-left: 16px; // 缩进量
  }

  .el-tree-node__children {
    padding-left: 16px; // 缩进量
  }

  // 竖线
  .el-tree-node::before {
    content: "";
    height: 100%;
    width: 1px;
    position: absolute;
    left: -3px;
    top: -26px;
    border-width: 1px;
    border-left: 1px dashed #52627C;
  }

  // 当前层最后一个节点的竖线高度固定
  .el-tree-node:last-child::before {
    height: 40px; // 可以自己调节到合适数值
  }

  // 横线
  .el-tree-node::after {
    content: "";
    width: 15px;
    height: 20px;
    position: absolute;
    left: 0px;
    top: 12px;
    border-width: 1px;
    border-top: 1px dashed #52627C;
  }

  // 去掉最顶层的虚线，放最下面样式才不会被上面的覆盖了
  & > .el-tree-node::after {
    border-top: none;
  }

  & > .el-tree-node::before {
    border-left: none;
  }

  // 展开关闭的icon
  //.el-tree-node__expand-icon{
  //  font-size: 16px;
  //  // 叶子节点（无子节点）
  //  &.is-leaf{
  //    color: transparent;
  //    // display: none; // 也可以去掉
  //  }
  //}
}
</style>
<style lang="scss">
.el-tree .el-tree-node__expand-icon.expanded {
  -webkit-transform: rotate(0deg);
  transform: rotate(0deg);
}

.el-tree .el-icon-caret-right:before {
  content: "\e723";
  font-size: 16px;

}

.el-tree .el-tree-node__expand-icon.expanded.el-icon-caret-right:before {
  content: "\e722";
  font-size: 16px;

}

.el-tree {
  color: black;
  background-color: #f7f9fc;
  text-align: left;
}

.el-tree-node:focus > .el-tree-node__content {
  background-color: #e4efff; //节点的背景颜色
  color: black; //节点的字体颜色
}

.el-tree .el-tree-node__content {
}


.title {
  //margin: 250px 0 10px;
  //margin-left: 25%;
  font-size: 18px;
  font-weight: bold;
}
.is-selected {
  color: rgb(66, 133, 244) !important;
}

.custom-tree-node.is-selected i,
.custom-tree-node.is-selected span {
  color: rgb(66, 133, 244) !important;
}
</style>
