import { createRouter, createWebHistory } from 'vue-router'
import Layout from "@/layout/Layout";
const routes = [
  {
    path: '/',
    name: 'layout',
    component: Layout,
    redirect:"/home",
    children:[
      {
        path: '/home',
        name: 'Home',
        component: () => import('../views/Home')
      },
      {
        path: '/examine',
        name: 'Examine',
        component: () => import('../views/Examine')
      },
      {
        path: '/fxyysSc',
        name: 'fxyysSc',
        component: () => import('../views/fxyysSc')
      },
      {
        path: '/about',
        name: 'About',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
      },
      {
        path: '/person',
        name: 'Person',
        component: () => import('../views/Person')
      },
      {
        path: '/history',
        name: 'History',
        component: () => import('../views/History')
      },
      {
        path: '/im',
        name: 'Im',
        component: () => import('../views/Im')
      },
      {
        path: '/user',
        name: 'User',
        component: () => import('../views/User')
      },
      {
        path: '/fxyysqz',
        name: 'FxyysQz',
        component: () => import('../views/FxyysQz')
      },
      {
        path: '/fxyysjjf',
        name: 'FxyysJjf',
        component: () => import('../views/FxyysJjf')
      },
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register')
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
