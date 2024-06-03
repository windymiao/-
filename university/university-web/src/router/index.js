import VueRouter from "vue-router";

const routes = [
    {
        path:'/',
        name:'login',
        component:()=>import('../components/Login')
    },
    {
        path:'/Index',
        name:'index',
        // redirect: { name: 'home' },
        component:()=>import('../components/Index'),
        children:[
            {
                path:'/Home',
                name:'home',
                component:()=>import('../components/Home'),
            },
        ]
    }
]

const router = new VueRouter({
    mode:'history',
    routes
})
export function resetRouter() {
    router.matcher = new VueRouter({
        mode:'history',
        routes: []
    }).matcher
}
// 解决处于当前路由，再次该路由跳转的错误
const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (to) {
    return VueRouterPush.call(this, to).catch(err => err)
}
export default router;