<template>
    <div>
      <input v-model="searchQuery" placeholder="Filter items...">
      <ul>
        <li v-for="item in filteredList" :key="item.id">
          {{ item.name }}
        </li>
      </ul>
    </div>
  </template>
   
  <script>
  export default {
    name:"TestComponent",
    data() {
      return {
        searchQuery: '',
        itemsList: [
          { id: 1, name: 'Item 1' },
          { id: 2, name: 'Item 2' },
          { id: 3, name: 'Item 3' },
          // ...
        ]
      };
    },
    computed: {
      filteredList() {
        return this.itemsList.filter(item => {
          return item.name.includes(this.searchQuery);
        });
      }
    }
  };
  </script>