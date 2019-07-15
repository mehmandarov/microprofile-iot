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

const numberOfLightsURL = 'http://localhost:9080/hue/lights/';
const resetURL = 'http://localhost:9080/hue/resetlights/';
const setURL = 'http://localhost:9080/hue/';

export default Vue.extend({
  data: () => ({
    lights: [] as Light[],
  }),
  mounted() {
      Vue.axios
      .get(numberOfLightsURL)
      .then((callback: any) => this.createLight(this.lights.length, 'Unknown'));
  },
  methods: {
    createLight(index: number, status: string) {
      const light = new Light();
      light.setNumber(index);
      light.setStatus(status);
      this.lights.push(light);
    },
    resetLights() {
      Vue.axios
      .get(resetURL)
      .then((callback: any) => this.lights.forEach((i) => i.setStatus('Off')));
    },
    setLights() {
      Vue.axios
      .get(setURL)
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