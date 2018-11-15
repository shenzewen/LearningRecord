## 安卓笔记

* **EditText 自动换行 设置**

```
EditText editText = new EditText(this);
//设置EditText的显示方式为多行文本输入  
editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
//文本显示的位置在EditText的最上方  
editText.setGravity(Gravity.TOP);
//改变默认的单行模式  
editText.setSingleLine(false);
//水平滚动设置为False  
editText.setHorizontallyScrolling(false);
```

* **自定义带标题边框的Layout**

自定义View[TitleBorderLayout.java](/android/file/TitleBorderLayout.java) 

自定义attribute

```
<declare-styleable name="TitleBorderLayout">
        <!-- The title of BorderTitleLayout. -->
        <attr name="title" format="string" />
        <!-- The size of title. -->
        <attr name="titleTextSize" format="dimension" />
        <!-- The title start postion. -->
        <attr name="titlePosition" format="dimension" />
        <!-- The color of title. -->
        <attr name="titleTextColor1" format="reference|color" />
        <!-- The size of border. -->
        <attr name="borderSize" format="dimension" />
        <!-- The color of border. -->
        <attr name="borderColor" format="reference|color" />
</declare-styleable>
```

