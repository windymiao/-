import vue from 'vue'
import Vuex from 'vuex'
import router,{resetRouter} from "../router";
import createPersistedState from 'vuex-persistedstate'
vue.use(Vuex)

function addNewRoute(menuList) {
    console.log("menuList")
    console.log(menuList)   // 查询出来的目录集合
    let routes = router.options.routes   // 原有的一级路由
    console.log("routes")
    console.log(routes)
    // 遍历所有的一级路由，如果为 /Index，则往其中添加相应的二级路由 （查询出来的目录集合）
    routes.forEach(routeItem=>{
        if(routeItem.path=="/Index"){
            // 遍历目录集合，封装为二级路由，插入到Index中
            menuList.forEach(menu=>{
                let childRoute =  {
                    path:'/'+menu.menuclick,
                    name:menu.menuname,
                    meta:{
                        title:menu.menuname
                    },
                    component:()=>import('../components/'+menu.menucomponent)
                }
                // 将封装好的二级路由信息，插入到 Index一级路由中
                routeItem.children.push(childRoute)
            })
        }
    })

    resetRouter()   // 重置清空原有的路由信息
    router.addRoutes(routes)      // 将新封装好的路由信息回传给router.js
}
export default new Vuex.Store({
    state: {
        menu: []
    },
    mutations: {
        setMenu(state,menuList) {
            state.menu = menuList
            addNewRoute(menuList)
        }
    },
    getters: {
        getMenu(state) {
            console.log("----"+state.menu)
            return state.menu
        }
    },
    plugins: [createPersistedState()],
})