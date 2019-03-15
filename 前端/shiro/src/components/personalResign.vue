<template>
  <div>
    <div>

      <el-dialog
        title="提示"
        :visible.sync="dialogVisible"
        width="30%"
      >
        <span>{{message}}</span>
        <span
          slot="footer"
          class="dialog-footer"
        >
          <el-button @click="cancel()">取 消</el-button>
          <el-button
            type="primary"
            @click="confirm()"
          >确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <div>
      <el-menu
        :default-active="activeIndex"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
      >
        <el-menu-item index="1">处理中心</el-menu-item>
        <el-submenu index="2">
          <template slot="title">我的工作台</template>
          <el-menu-item index="2-1">选项1</el-menu-item>
          <el-menu-item index="2-2">选项2</el-menu-item>
          <el-menu-item index="2-3">选项3</el-menu-item>
          <el-submenu index="2-4">
            <template slot="title">选项4</template>
            <el-menu-item index="2-4-1">选项1</el-menu-item>
            <el-menu-item index="2-4-2">选项2</el-menu-item>
            <el-menu-item index="2-4-3">选项3</el-menu-item>
          </el-submenu>
        </el-submenu>
        <el-menu-item
          index="3"
          disabled
        >消息中心</el-menu-item>
        <el-menu-item index="4">
          订单管理
        </el-menu-item>
        <el-menu-item
          index="8"
          v-on:click="exit()"
        >
          退出
        </el-menu-item>
      </el-menu>
      <div class="line"></div>
      <el-menu
        :default-active="activeIndex2"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b"
      >

      </el-menu>
    </div>
    <div>
      <el-row class="tac">
        <el-col :span="5">

          <el-menu
            default-active="2"
            class="el-menu-vertical-demo"
            @open="handleOpen"
            @close="handleClose"
          >
            <el-submenu index="1">
              <template slot="title">
                <i class="el-icon-location"></i>
                <span>员工设置</span>
              </template>
              <el-menu-item-group>
                <!-- <template slot="title">员工设置</template> -->
                <el-menu-item
                  index="1-1"
                  @click="searchUser()"
                ><i class="el-icon-search">查询员工</i></el-menu-item>
                <div v-show="role1">

                  <el-menu-item index="1-2"><i class="el-icon-edit">修改员工</i></el-menu-item>
                </div>
                <div v-show="role0">
                  <el-menu-item index="1-2"><i class="el-icon-delete">删除员工</i></el-menu-item>
                </div>
                <div v-show="role0">
                  <el-menu-item index="1-2"><i class="el-icon-circle-plus-outline">添加员工</i></el-menu-item>
                </div>

              </el-menu-item-group>

            </el-submenu>
            <el-menu-item index="2">
              <i class="el-icon-menu"></i>
              <span slot="title">导航二</span>
            </el-menu-item>
            <el-menu-item
              index="3"
              disabled
            >
              <i class="el-icon-document"></i>
              <span slot="title">导航三</span>
            </el-menu-item>
            <el-menu-item index="4">
              <i class="el-icon-setting"></i>
              <span slot="title">导航四</span>
            </el-menu-item>
          </el-menu>
        </el-col>
        <el-col :span="9">
          <div id="container">
            <el-table
              :data="getData"
              border
              style="width: 100%"
            >
              <el-table-column
                fixed
                prop="userId"
                label="用户编号"
                width="150"
              >
              </el-table-column>
              <el-table-column
                prop="userName"
                label="用户名"
                width="120"
              >
              </el-table-column>

              <el-table-column
                fixed="right"
                label="操作"
                width="100"
              >
                <template slot-scope="scope">
                  <el-button
                    @click="handleClick(scope.row)"
                    type="text"
                    size="small"
                  >查看</el-button>
                  <el-button
                    type="text"
                    size="small"
                  >编辑</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-col>
      </el-row>
    </div>

  </div>

</template>
<script>
  export default {
       data() {
      return {
        activeIndex: '1',
        activeIndex2: '1',
        role0:false,
        role1:false,
        role2:true, 
        userName:this.$route.query.userName,
       permissions:this.$route.query.permissions,
       roles:this.$route.query.roles,
        dialogVisible: false,
        message:"",
        loginSig:false,
        URLs:{
            getUser:"http://localhost:8080/get/getUser",
            confirmUser:"http://localhost:8080/get/confirmUser"
        },
        getData:"",
      };
    },
     created () {
       var _this=this;
        if(this.userName==null||this.userName==""){
             this.message="非法登录";
            this.dialogVisible=true;
            this.loginSig=true;
        }
        $.ajax({
            url: this.URLs.confirmUser,
            data: "userName=" + this.userName,
            type: "get",
            //加上这句话,允许浏览器向服务器跨域请求时携带cookie
            xhrFields: {
                withCredentials: true
            },
            crossDomain: true,
            success: function(data) {
               console.log(data)

            }
        })
        //  this.$axios({
        //     method: 'get',
        //     url:this.URLs.confirmUser,
            
        //     params: {
        //            userName:_this.userName,
                  
        //     },
        //        xhrFields: {
        //         withCredentials: true
        //     },
        //     crossDomain: true,
        //     },
            
        //     ).then((res)=>{
        //         var st=res.data;
        //        console.log(st)
        //     })

        if(_this.roles.indexOf("admin")!=-1){
         _this.role0=true;
         _this.role1=true;
         _this.role2=true;
        }else   if(_this.roles.indexOf("test")!=-1){
         _this.role1=true
          _this.role2=true;
        }else   if(_this.roles.indexOf("guest")!=-1){
         _this.role2=true
        }
     
     },
    methods: {
        cancel(){
            this.dialogVisible=false;
            if(this.loginSig){
                this.$router.push('/')
            }
        },
        confirm(){
             this.dialogVisible=false;
          this.$router.push('/')
        },
      handleOpen(key, keyPath) {
        // console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        // console.log(key, keyPath);
      },
       handleSelect(key, keyPath) {
        // console.log(key, keyPath);
      },
      exit(){
          this.message="确认退出";
          this.dialogVisible=true;
        //   this.$router.push('/')
      },
    searchUser(){
            this.$axios({
            method: 'get',
            url:this.URLs.getUser,
            // params: {
            //        userName:_this.userName,
            //        passWord:_this.passWord 
            // },
                headers:{
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
            }
            }).then((res)=>{
                var st=res.data;
                this.getData=st.users;
                // $("#container") .empty();
            })
    },
    }
  }
</script>
<style scoped>
</style>
