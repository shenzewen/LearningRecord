## 安卓笔记

* **EditText 自动换行 设置**

```java
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

```xml
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

* **Android 更改默认按钮大写的设定**

  * 全局修改

  在AppTheme中添加`<item name="textAllCaps">false</item>`,如下

  ```xml
  <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
      <item name="colorPrimary">@color/colorPrimary</item>
      <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
      <item name="colorAccent">@color/colorAccent</item>
      <!-- 关闭按钮默认大写 -->
      <item name="textAllCaps">false</item>
  </style>
  ```

  * 局部修改

  在Button上添加`android:textAllCaps="false"`，如下：

  ```xml
  <Button
     android:textAllCaps="false"
     android:layout_marginTop="match_parent"
     android:layout_marginBottom="match_parent"
     android:text="@string/text" />
  ```

  * Dialog按钮

    * 使用上面的全局修改
    * 使用代码修改，代码如下

    ```java
    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setAllCaps(false);
    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setAllCaps(false);
    ```
