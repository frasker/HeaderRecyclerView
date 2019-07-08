# HeaderRecyclerView

HeaderRecyclerView实现了像ListView方便添加头部和尾部的功能，支持LinearLayoutManager,GridLayoutManager。实现了无侵入性的头部和尾部添加。
## 使用方式
```
// 添加头部
recyclerView.addHeaderView(headerView); 
// 移除某个头部
recyclerView.removeHeaderView(headerView);  
// 添加尾部
recyclerView.addFooterView(footerView);  
// 移除某个尾部
recyclerView.removeFooterView(footerView);  

```
## 依赖
```
dependencies {
  implementation 'com.github.frasker:headerrecyclerview:0.0.1'
}
```
