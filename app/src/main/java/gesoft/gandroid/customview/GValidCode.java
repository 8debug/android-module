package gesoft.gandroid.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import gesoft.gandroid.R;

/**
 * Created by yhr on 2016/9/18.
 * 自定义随机验证码控件
 */
public class GValidCode extends View {

    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;
    private int mTitleBackColor;
    private Paint mPaint;
    private Rect mBound;

    public GValidCode(Context context) {
        this(context,null);
    }

    public GValidCode(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public GValidCode(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GValidCode, defStyleAttr, 0);

        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.GValidCode_gTitleText:
                    mTitleText = a.getString(attr);
                    break;
                case R.styleable.GValidCode_gTitleTextColor:
                    mTitleTextColor = a.getInt(attr, Color.BLACK);
                    break;
                case R.styleable.GValidCode_gTitleTextSize:
                    mTitleTextSize = a.getDimensionPixelSize(attr, (int)TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics() ));
                    break;
                case R.styleable.GValidCode_gTitleBackGroudColor:
                    mTitleBackColor = a.getColor(attr,Color.YELLOW);
            }
        }
        a.recycle();


        mPaint = new Paint();
        mPaint.setTextSize( mTitleTextSize );
        mBound = new Rect();
        mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitleText = "sdf";

            }
        });

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=0, height = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                width = getPaddingLeft()+getPaddingRight()+specSize;
                break;
            case MeasureSpec.AT_MOST:
                width = getPaddingLeft()+getPaddingRight()+mBound.width();
                break;
        }

        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                height = getPaddingTop() + getPaddingBottom() + specSize;
                break;
            case MeasureSpec.AT_MOST:
                height = getPaddingTop() + getPaddingBottom() + mBound.height();
                break;
        }

        setMeasuredDimension(width, height);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0,0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(mTitleTextColor);
        canvas.drawText(mTitleText, getWidth()/2f - mBound.width()/2f, getHeight()/2f + mBound.height()/2f, mPaint );
    }
}
