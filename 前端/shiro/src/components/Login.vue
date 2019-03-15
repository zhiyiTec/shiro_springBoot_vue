<template>
  <b-container fluid>
    <b-row
      class="my-1"
      v-for="type in types"
      :key="type"
    >
      <b-col sm="3">
        <label
          :for="`type-${type}`"
          v-if="type=='text'"
        > 用户名:</label>
        <label
          :for="`type-${type}`"
          v-else
        > 密码:</label>
      </b-col>

      <b-col sm="9">
        <div v-if="type=='text'">
          <b-form-input
            :id="`type-${type}`"
            :type="type"
            style="width:45%"
            v-model="userName"
          />

        </div>
        <div v-else>
          <b-form-input
            :id="`type-${type}`"
            :type="type"
            style="width:45%"
            v-model="passWord"
          />
        </div>
      </b-col>

    </b-row>
    <b-alert
      :show="dismissCountDown"
      dismissible
      variant="danger"
      @dismissed="dismissCountDown=0"
      @dismiss-count-down="countDownChanged"
    >
      <p>{{loginInfo}}</p>
      <b-progress
        variant="danger"
        :max="dismissSecs"
        :value="dismissCountDown"
        height="4px"
      />
    </b-alert>
    <b-row>
      <b-col sm="1"></b-col>
      <b-col sm="6">
        <div style="margin-right:-75%;margin-top:2%;">
          <b-button
            variant="success"
            v-on:click="sub()"
          >登录</b-button>
          <b-button
            variant="danger"
            v-on:click="reset()"
          >重置</b-button>
        </div>
      </b-col>

    </b-row>
    <router-view></router-view>
  </b-container>
</template>

<script>
  var host="http://localhost:8080/";


  export default {
  
    data() {
      return {
        types: [
          'text',
          'password',
          
        ],
       userName:'',
       passWord:'',
       dismissSecs: 3,
      dismissCountDown: 0,
      showDismissibleAlert: false,
      loginInfo:"",
      }
    },
    methods:{
       countDownChanged(dismissCountDown) {
        this.dismissCountDown = dismissCountDown
      },
      showAlert() {
        this.dismissCountDown = this.dismissSecs
      },
      sub:function(){
        var _this=this;
       
    this.$axios({
    method: 'get',
    url:host+'shiro/login',
    params: {
           userName:_this.userName,
           passWord:_this.passWord 
    },
     headers:{
      'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
    }
    }).then((res)=>{
      var st=res.data;
      
       
        if(st.status==1){
                      _this.loginInfo="没有您这个用户";
                     }else if(st.status==2){
                       _this.loginInfo="您密码不正确";
                     }else if(st.status==3){
                        _this.loginInfo="您的账户已被冻结";
                     }else if(st.status==4){
                        _this.loginInfo="未知错误";
                     }
                 if(st.status==0){
                // _this.$router.push('/personalResign')
                  //  _this.$router.push('/test')
        _this.$router.push('/personalResign?userName='+ _this.userName+"&&permissions="+st.permissions+"&&roles="+st.roles)
    

      }else{
        _this.showAlert()
      }    
    })
      },
      reset:function(){
        $("#type-text").val("");
        $("#type-password").val("");
      },
      sendAxious:function(method,url,data,successfunction,errorfun){

        this.$axios({
    method: method,
    url:url,
    params:data,
    //加上这句话,允许浏览器向服务器跨域请求时携带cookie
            xhrFields: {
                withCredentials: true
            },
            crossDomain: true,
    }).then((res)=>{
      successfunction;
    })
      }
    },
  }
</script>