<template>
  <div class="home">
    <div class="lights">
      <div v-for="light in lights" :key="light.number">
        <Light status="Off" :number="light.number"/>
      </div>
    </div>
    <div id="buttons">
      <button type="button" v-on:click="resetLights">Reset lights</button>
      <button type="button" v-on:click="setLights">Set lights</button>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import Light from '@/components/Light.vue';

export default Vue.extend({
  data: () => ({
    lights: [] as Light[],
  }),
  mounted() {
    this.createLight(0);
    this.createLight(1);
    this.createLight(2);
    this.createLight(3);
  },
  methods: {
    createLight: function(index: number) {
      const light = new Light();
      light.setNumber(index);
      this.lights.push(light);
    },
    resetLights: () => {
      // todo
    },
    setLights: () => {
      // todo
    },
  },
  components: {
      Light,
    },
});
</script>

<style scoped lang="scss">
    div.light {
      float: left;
      min-width: 15%;
      margin: 10px;
      border: 1px solid black;
      padding: 10px;
    };
    
    div.lights {
      border: 1px solid blue;
      margin: 0 auto;
      width: 70%;
    };

    div#buttons {
      clear: both;
      min-width: 200px;
      margin: 0 auto;
    };

    button {
      min-height: 200px;
      height: 300px;
      margin: 5px;
      width: 30%;
    };
</style>