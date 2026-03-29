<script setup>
import HelloWorld from './components/HelloWorld.vue'
import TheWelcome from './components/TheWelcome.vue'
import Login from './components/Login.vue'
import Register from './components/test.vue'
import Test from "@/components/test.vue";
import WelcomeItem from "@/components/WelcomeItem.vue";
</script>

<template>




  <div id="app">
    <router-view />
  </div>



</template>

<style scoped>

* { margin: 0 auto;
  padding: 100px;
  box-sizing: border-box;
}

body {
  background: linear-gradient(
  to right,
  rgb(0, 123, 255),
  rgba(0, 123, 255, 0.5)
  );
}






</style>
