import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import "@/assets/css/gobal.css"
import * as ElIconModules from '@element-plus/icons-vue'
const app=createApp(App)
for (const iconName in ElIconModules) {
    if (Reflect.has(ElIconModules, iconName)) {
        const item = ElIconModules[iconName]
        app.component(iconName, item)
    }
}

app.use(store)
app.use(router)
app.use(ElementPlus, {locale: zhCn})
app.mount('#app')

