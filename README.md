# CustomProgressBar
自定义进度条<br>
#####改项目是自定义view实现进度条
在项目中CustomProgressBar即为改自定义view<br>
###下面是演示效果
 <img src="https://github.com/jiangML/CustomProgressBar/blob/master/video/custom.gif" width="400" height="700"/>



*  该自定义view可以设置进度条为水平或者环型进度条
*  可以设置进度条的进度值颜色和未到达进度的颜色
*  可以设置显示百分比字体的大小和颜色
*  可以设置默认进度值
*  可以设置最大进度值
*  进度条的宽度


···

           <com.jiang.customprogressbar.CustomProgressBar
                android:id="@+id/pb"
                android:layout_marginTop="10dp"
                android:layout_width="200dp"
                android:layout_height="200dp"
                app:progress_color="#5D478B"
                app:paint_color="#ff0066"
                app:paint_width="20dp"
                app:progress_text_size="50sp"
                app:progress_text_color="#0000EE"
                app:progress_default_value="0"/>
 
···

####view的自定义属性如下<br>

```

	  <resources>
	    <declare-styleable name="CustomProgressBar">
	        <attr name="progress_mode" format="enum">
	            <enum name="horziontal" value="0"/>
	            <enum name="ring" value="1"/>
	        </attr>
	        <attr name="paint_width" format="dimension"/><!--画笔的宽度 当是圆型进度条时 指定的是圆环的宽度-->
	        <attr name="paint_color" format="color"/><!--进度值中未到达进度的颜色-->
	        <attr name="progress_text_size" format="dimension"/><!--显示进度值的字体大小-->
	        <attr name="progress_max" format="integer"/><!--进度值的最大值-->
	        <attr name="progress_color" format="color"/><!--进度值的颜色-->
	        <attr name="progress_text_color" format="color"/><!--进度值字体的颜色-->
	        <attr name="progress_default_value" format="integer"/><!--进度默认值-->
	    </declare-styleable>
	 </resources>

```