<template>
    <el-card :style="infoCardStyle" v-show="isVisible" id="info">
        <div slot="header" class="clearfix">
            <span>{{ cardName }}</span>
        </div>
        <p>{{ "预警等级：" + yujingLevel }}</p>
        <p>{{ "所在流域：" + river }}</p>
        <hr>
        <p v-for="(item, index) in waterLevelData" :key="index">
            {{ waterLevelRelationship[index].name + ' : ' + item.name + 'm' }}
        </p>
    </el-card>
</template>
<script>
export default {
    props: {
        data: Object
    },
    data() {
        return {
            cardName: '',
            yujingLevel: '',
            isVisible: false,
            river: "",
            waterLevelData: [],
            waterLevelRelationship: [],
            top: "0px",
            left: "0px",
            color: ""
        };
    },
    computed: {
        infoCardStyle() {
            return {
                position: "absolute",
                top: this.top + 'px',
                left: this.left + 'px',
                color: this.color,
                "z-index": 9999,
            };
        }
    },
    methods: {
        init(data) {
            this.top = data.top;
            this.left = data.left;
            this.cardName = data.name;
            this.color = data.color;
            this.yujingLevel = data.yujingLevel;
            this.river = data.river;
            this.waterLevelData = data.waterLevelData;
            this.waterLevelRelationship = data.waterLevelRelationship;
            this.isVisible = data.show;
        }
    }
}
</script>
