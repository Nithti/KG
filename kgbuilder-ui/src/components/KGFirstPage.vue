<!-- 首页 -->
<template>
  <div class="word-document">
    <div class="header">小型水库防洪预案知识图谱平台介绍</div>
    <div class="content">
      <p>知识图谱技术应用于水利领域，使用水利文本数据构建水利专业知识图谱，可以利用结构化的水利专业知识建立知识驱动的分析模型及应用系统，提高水利信息处理的智能化程度。融合内容分析、自然语言处理等技术，支持知识抽取和融合、知识存储、知识搜索和知识表达，为水利知识图谱构建提供底层支撑。利用水利知识图谱技术，将结构化后的数据以图谱的形式呈现给用户，更加直观呈现水利数据中各个实体间的关联信息。</p>
      <p>1. 水库数据图谱化展示</p>
      <p>本系统按照水库所属的省市区进行目录划分，包含山东省五千多座水库节点，以及各地区水库分布情况；此外，系统会根据水位信息判定水库当前的预警等级，并根据预警等级将水库节点显示为不同的颜色。</p>
      <img src="../../public/images/tupu.png" alt="tupu">
      <p>2. 图谱基础功能</p>
      <p>2.1 水库数据的基础信息以信息面板的方式呈现。只需将鼠标放置在水库节点上即可显示具体信息。</p>
      <img src="../../public/images/mianban.png" alt="mianban">
      <p>2.2 图谱的主要功能以图谱功能圈的形式呈现，点击水库节点可以显示可操作功能圈。</p>
      <img src="../../public/images/gongnengquan.png" alt="gongnengquan">
      <p>2.2.1 次级节点的便捷展开</p>
      <p>为了方便用户快速了解水库的关键信息，系统提供一键展开功能，迅速展示水库的次级节点内容。</p>
      <img src="../../public/images/zhankai.png" alt="zhankai">
      <p>2.2.2 节点内容的灵活编辑</p>
      <p>用户可以针对水库的各个次级节点进行内容添加或修改，这些节点包括水位设计、预警等级、所属流域和责任人等关键信息。</p>
      <img src="../../public/images/bianji.png" alt="bianji">
      <p>2.2.3 节点信息的动态更新</p>
      <p>用户可以随时修改节点的信息，这些更改将实时反映在系统中，确保数据的即时性和准确性。</p>
      <img src="../../public/images/gengxin.png" alt="gengxin">
      <p>2.2.4 预警信息的动态展示</p>
      <p>系统根据水库当前的状况，自动评估预警级别，并以视觉化的方式将预警内容实时呈现，使用户对水库状况一目了然。</p>
      <img src="../../public/images/yujing.png" alt="yujing">
      <p>2.2.5 水位预测和可视化</p>
      <p>基于历史水位数据，系统能预测水库未来的水位走势，并将预测结果以直观的图表形式展现给用户。</p>
      <img src="../../public/images/shuiwei.png" alt="shuiwei">
      <p>2.3 预警水库数量统计</p>
      <p>系统会根据用户权限统计当前目录下各预警等级水库的数量，并将结果以直观的图表形式展现给用户。</p>
      <img src="../../public/images/yujingtongji.png" alt="yujingtongji">
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  mounted() {},
  methods: {},
};
</script>

<style lang="scss" scoped>
// .container {
//   display: flex;
//   flex-direction: column;
//   justify-content: center; //水平居中
//   align-items: center; // 垂直居中
// }

.word-document {
  font-family: Arial, sans-serif;
  margin: 20px;
  padding: 10px;
  border: 1px solid #ccc;
}

.header {
  font-size: 20px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 10px;
}

.content {
  line-height: 1.5;
  text-indent: 2em; 
}

img {
  width: 960px;
  height: 471px;
  display: block; /* 将图片转换为块级元素 */
  margin: 0 auto; /* 设置左右外边距为自动，实现水平居中 */
}
</style>