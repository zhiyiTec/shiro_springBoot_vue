<template>
  <div>

    <b-card>
      <b-media v-if="signalRole">
        <b-img
          slot="aside"
          blank
          blank-color="#ccc"
          width="64"
          alt="placeholder"
          src="../images/20.jpg"
        />

        <h5 class="mt-0">Media Title</h5>
        <p>
          Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin.
          Cras purus odio, vestibulum in vulputate at, tempus viverra turpis. Fusce condimentum nunc
          ac nisi vulputate fringilla. Donec lacinia congue felis in faucibus.
        </p>
        <p>
          Donec sed odio dui. Nullam quis risus eget urna mollis ornare vel eu leo. Cum sociis natoque
          penatibus et magnis dis parturient montes, nascetur ridiculus mus.
        </p>

        <b-media>
          <b-img
            slot="aside"
            blank
            blank-color="#ccc"
            width="64"
            alt="placeholder"
          />

          <h5 class="mt-0">Nested Media</h5>
          <p class="mb-0">
            Fusce condimentum nunc ac nisi vulputate fringilla. Donec lacinia congue felis in
            faucibus.
          </p>
        </b-media>
      </b-media>
    </b-card>
    <div>
      <b-jumbotron
        header="Bootstrap Vue"
        lead="Bootstrap 4 Components for Vue.js 2"
      >
        <p>For more information visit website</p>
        <b-button
          variant="primary"
          href="http://localhost:8081/"
        >退出</b-button>
      </b-jumbotron>
    </div>

  </div>
</template>

<script>
var host="http://localhost:8080/";
 export default{
   data() {
     return{
       userName:this.$route.query.userName,
       permissions:this.$route.query.permissions,
       roles:this.$route.query.roles,
       signalRole:false,
     }
   },
   created () {
       var _this=this;
        if(_this.roles.indexOf("admin")!=-1){
         _this.signalRole=true
        }else   if(_this.roles.indexOf("test")!=-1){
         _this.signalRole=true
        }
     
     },
   methods:{
     
   }
 }
</script>