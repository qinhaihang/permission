# permission

## 轻量级Android运行时权限请求库

## 引入方式
1. 在项目的root build.gradle 中添加：
Add it in your root build.gradle at the end of repositories:

```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

2. 在引入的 module 中添加依赖
```gradle
	dependencies {
	        implementation 'com.github.qinhaihang:permission:Tag'
	}
```
