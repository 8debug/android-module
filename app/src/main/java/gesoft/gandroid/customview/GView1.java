package gesoft.gandroid.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yhr on 2016/9/28.
 *
 */
public class GView1 extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public GView1(Context context) {
        super(context);
    }

    public GView1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 确定要绘制的view尺寸
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension( measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec) );
    }

    /**
     * 在画布中绘制view
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);

        int left = getLeft();   //相对于父元素的元素左边缘距离
        int right = getRight(); //相对于父元素的元素右边缘距离
        int top = getTop();     //相对于父元素的元素上边缘距离
        int bottom = getBottom();//相对于父元素的元素下边缘距离

        int cx = ( right - left )/2;    //圆心x坐标
        int cy = ( bottom - top )/2;    //圆心y坐标
        canvas.drawCircle(cx, cy, Math.min( getWidth(), getHeight() )/2, mPaint);
    }

    private int measureWidth(int widthMeasureSpec ){
        int result = 200;
        int specMode = MeasureSpec.getMode( widthMeasureSpec );
        int specSize = MeasureSpec.getSize( widthMeasureSpec );
        switch( specMode ){
            case MeasureSpec.EXACTLY :
                result = specSize;
                break;
            case MeasureSpec.AT_MOST :
                result = Math.min(result, specSize);
                break;
        }
        return result;
    }

    private int measureHeight( int heightMeasureSpec ){
        int result = 200;
        int specMode = MeasureSpec.getMode( heightMeasureSpec );
        int specSize = MeasureSpec.getSize( heightMeasureSpec );
        switch( specMode ){
            case MeasureSpec.EXACTLY :
                result = specSize;
                break;
            case MeasureSpec.AT_MOST :
                result = Math.min(result, specSize);
                break;
        }
        return result;
    }
}
