<template>
  <div class="light">
    <h1>Light {{ number }}: {{ status }}</h1>
    <img src="@/assets/logo.png" :alt="status" />
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-property-decorator';

@Component
export default class Light extends Vue {
  @Prop() private status!: string;
  @Prop() private number!: number;

  public setNumber(newNumber: number) {
      this.number = newNumber;
  }

  public setStatus(newStatus: string) {
    this.status = newStatus;
  }
}
</script>

<style scoped lang="scss">
    h1 {
      margin: 3px;
    }
    img {
        width: 4rem;
        height: 4rem;
        margin: 0 3px 3px 3px;
    }
    div {
      margin: 0 3px;
      border: 1px dotted black;
      padding: 0 5px;
    };
</style>