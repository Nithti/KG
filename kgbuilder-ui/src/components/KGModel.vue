<!-- 图谱构建 -->
<template>
<!-- <el-card class="box-card">
  <div v-for="o in 4" :key="o" class="text item">
    {{'列表内容 ' + o }}
  </div>
</el-card> -->
  <div class="word-document">
    <div class="header">小型水库防洪预案知识图谱构建</div>
    <div class="content">
      <p>1. 总体技术路线</p>
      <img src="../../public/images/tupugoujian/jishuluxian.png" alt="tupugoujian">
      <p>2. 关键技术</p>
        <p>Neo4j是一种图形数据库，它提供了强大的Cypher查询语言，可以方便地进行复杂的图查询和分析。使用Cypher查询语言，我们可以轻松地从水库数据知识图谱中提取所需的信息，并进行各种查询和分析操作。</p>
        <p>使用Neo4j数据库构建数据图谱：水库数据通常包含大量的实体和关系，如水库、河流、水位、建筑参数等。通过构建知识图谱，可以将这些实体和关系以图谱的形式进行建模，并通过节点和边的连接关系来表示它们之间的关联性。通过构建水库数据知识图谱，我们可以发现数据中的潜在关系和模式。通过分析节点之间的连接关系和属性特征，我们可以揭示水库数据中的隐藏规律和趋势。</p>
        <p>例如，我们可以使用Cypher查询语言来查找与特定水库相关的所有节点和关系。我们可以通过指定相关属性和关系类型来筛选出感兴趣的数据，并通过节点和边的连接关系来了解水库与河流、水位等实体之间的关联性。</p>
        <p>通过构建水库数据知识图谱并利用Cypher查询语言进行分析，我们可以更好地理解水库数据中的信息，并发现其中的隐藏规律和趋势。这将为水库管理和决策提供有力的支持和指导。</p>
        <p>在Neo4j中存储并构建数据图谱如下所示：</p>
      <img src="../../public/images/tupugoujian/neo4j.png" alt="neo4j">
    </div>
  </div>

</template>

<script>
export default {
  
};
</script>
<style>
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

/* 每段文字前空两个空格 */
/* body {
  text-indent: 2em; 
} */

img {
  width: 846px;
  height: 550px;
  /* 将图片转换为块级元素 */
  display: block;
  /* 设置左右外边距为自动，实现水平居中 */ 
  margin: 0 auto; 
}

  .text {
    font-size: 14px;
  }

  .item {
    padding: 18px 0;
  }

  .box-card {
    width: 480px;
  }
</style>
<style lang="scss" scoped></style>
