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
    img {
        width: 5rem;
        height: 5rem;
    }
</style>