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
      .then((callback: any) => {
        for (let i = 0; i < callback.data; i++) {
          this.createLight(i, '-');
        }
      });
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
      .then((callback: any) => this.lights.forEach((i) => i.setStatus(('0'))));
    },
    setLights() {
      Vue.axios
      .get(setURL)
      .then((callback: any) => {
        if (callback.status === 200) {
          callback.data.forEach((light: any) => {
            this.lights[light.lightIndex].setStatus(light.brightness.brightness);
          });
        }
      });
    },
  },
  components: {
      Light,
    },
});
</script>

<style scoped lang="scss">
    
    div.lights {
      display: flex;
      justify-content: center;
      margin: 15px 0;
    };

    button {
      height: 120px;
      margin: 5px;
      width: 30%;
      font-size: 4rem;
    };
</style>