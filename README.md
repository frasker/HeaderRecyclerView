# HeaderRecyclerView

HeaderRecyclerView实现了像ListView方便添加头部和尾部的功能，支持LinearLayoutManager,GridLayoutManager.
## 添加头部
```
recyclerView.addHeaderView(headerView);  
```
## 移除头部
```
recyclerView.removeHeaderView(headerView);  
```
## 添加尾部
```
recyclerView.addFooterView(footerView);  
```
## 移除尾部
```
recyclerView.removeFooterView(footerView);  
```
# 如何引用
**Step 1.** Add the JitPack repository to your build file
```
 allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
**Step 2.** Add the dependency
```
	dependencies {
	        implementation 'com.github.frasker:HeaderRecyclerView:v1.0.0'
	}
```
