
class EventPublic {
    constructor() {
      this.event = {}
    }
    $on(type, callback) {
      if (!this.event[type]) {
        this.event[type] = [callback]
      } else {
        this.event[type].push(callback)
      }
    }
    $emit(type, ...args) {
      if (!this.event[type]) {
        return
      }
      this.event[type].forEach(res => {
        res.apply(this, args)
      })
    }
    $off(type, callback) {
      if (!this.event[type]) {
        return
      }
      this.event[type] = this.event[type].filter(res => {
        return res != callback
      })
    }
    // 执行一次
    $once(type, callback) {
      function f() {
        callback()
        this.$off(type, f)
      }
      this.$on(type, f)
    }
  }
  export const EventBus = new EventPublic()

  // export default{
  //   mounted() {
  //     EventBus.$on("DIV", (width, height) => {
  //       console.log("Received width:", width);
  //       console.log("Received height:", height);
  //       // 在这里执行相应的操作，例如更新组件的样式或其他数据
  //     });
  //   }
  // }
  
  