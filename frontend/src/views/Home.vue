<template>
  <div class="home">
    <div class="lights">
      <div v-for="light in lights" :key="light.number">
        <Light :status="light.status" :number="light.number"/>
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
import axios from 'axios';
import VueAxios from 'vue-axios';

Vue.use(VueAxios, axios);

const resetURL = 'http://localhost:8080/about#/about/';
const setURL = 'http://localhost:8080/about#/about/';

export default Vue.extend({
  data: () => ({
    lights: [] as Light[],
  }),
  mounted() {
    this.createLight(0, 'off');
    this.createLight(1, 'on');
    this.createLight(2, 'off');
    this.createLight(3, 'off');
  },
  methods: {
    createLight: function(index: number, status: string) {
      const light = new Light();
      light.setNumber(index);
      light.setStatus(status)
      this.lights.push(light);
    },
    resetLights: function() {
      Vue.axios
      .get(resetURL)
      .then((callback: any) => this.lights.forEach((i) => i.setStatus('Off')));
      // todo
    },
    setLights: function() {
      Vue.axios
      .get(resetURL)
      .then((callback: any) => this.lights.forEach((i) => i.setStatus(callback.statusText)));
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
      font-size: 4rem;
    };
</style>