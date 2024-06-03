<template>
  <div id="app">
    <!-- <Index></Index> -->
    <!-- <Test></Test> -->
    <router-view></router-view>
  </div>
</template>

<script>
// import Index from './components/Index.vue'
// import Test from './components/Test.vue'
export default {
  name: 'App',
  components: {
    // Index,
    // Test,
  },
  data(){
    return{
      user:JSON.parse(sessionStorage.getItem("CurUser"))
    }
  },
  watch:{
    '$store.state.menu':{
      handler(val,old){
        if (!old && this.user && this.user.username){
          this.$store.commit("setMenu",val)
        }
      },
      immediate:true
    }
  }
}
</script>

<style>
#app {
    height: 100%;
}
</style>
