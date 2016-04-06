package com.coding.tag_layouter;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gunter on 2016/4/5.
 */
public class AutoLayoutContainer extends ViewGroup implements View.OnClickListener {

    //自定义属性
    private float mHorizontalDis;//标签横向边距，从自定义属性中获取
    private float mVerticalDis;//标签纵向边距，从自定义属性中获取
    private int mOrientation;//方向

    //横向特有的变量
    private int mRows;//标签的行数，从自定义属性中获取
    private int[] rowsLength;

    //纵向特有的变量
    private List<Integer> mCountPerRow;//该list集合存放每一行的便签数量，所以该集合的size等于行数

    //共有属性
    private boolean isFirst;
    private int mChildCount;//子View的个数，标签个数
    private LayoutInflater mInflater;
    private OnSelectListener selectListener;
    private List<View> mChild;

    public AutoLayoutContainer(Context context) {
        this(context, null);
    }

    public AutoLayoutContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取自定义属性值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoLayoutContainer);
        mHorizontalDis = typedArray.getDimension(R.styleable.AutoLayoutContainer_dist_horizontal, 20);
        mVerticalDis = typedArray.getDimension(R.styleable.AutoLayoutContainer_dist_vertical, 20);
        mRows = typedArray.getInt(R.styleable.AutoLayoutContainer_row, 1);
        mOrientation = typedArray.getInt(R.styleable.AutoLayoutContainer_orientation, 0);

        typedArray.recycle();

        mInflater = LayoutInflater.from(context);
        mChild = new ArrayList<>();
        isFirst = true;

        //纵向特有变量的初始化
        if (mOrientation == 0) {
            mCountPerRow = new ArrayList<>();
        }
        //横向特有变量的初始化
        else {
            initRowsLength();
        }
    }

    //初始化每一行的长度，均设为0
    private void initRowsLength() {
        rowsLength = new int[mRows];
        for (int i = 0; i < rowsLength.length; i++) {
            rowsLength[i] = 0;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mChildCount = getChildCount();
        if (mChildCount != 0) {
            //如果是纵向
            if (mOrientation == 0) {
                int width = getMeasuredWidth();
                if (width != 0) {
                    if (isFirst) {
                        mCountPerRow.clear();
                        int rowWidth = 0;
                        int rowCount = 0;
                        for (int i = 0; i < mChildCount; i++) {
                            View child = getChildAt(i);
                            measureChild(child, widthMeasureSpec, heightMeasureSpec);
                            int childWidth = child.getMeasuredWidth();
                            if ((rowWidth += childWidth) > width) {
                                if (rowWidth == childWidth) {
                                    break;
                                }
                                i--;
                                rowWidth = 0;
                                mCountPerRow.add(rowCount);
                                rowCount = 0;
                                continue;
                            }
                            rowWidth += mHorizontalDis;
                            rowCount++;
                            if (i == mChildCount - 1) {
                                mCountPerRow.add(rowCount);
                            }
                        }
                        isFirst = false;
                    }
                    setMeasuredDimension(getMeasuredWidth(), mCountPerRow.size() * (getChildAt(0).getMeasuredHeight() + (int) mVerticalDis) - (int) mVerticalDis);
                }
            }
            //如果是横向
            else {
                if (isFirst) {
                    for (int i = 0; i < mChildCount; i++) {
                        View child = getChildAt(i);
                        mChild.add(child);
                        measureChild(child, child.getMeasuredWidth(), child.getMeasuredHeight());
                        int childWidth = child.getMeasuredWidth();
                        int currentRow = getMinRow(rowsLength);
                        if (rowsLength[currentRow] == 0) {
                            rowsLength[currentRow] += childWidth;
                        } else {
                            rowsLength[currentRow] += childWidth + (int) mHorizontalDis;
                        }
                    }
                    isFirst = false;
                }
                setMeasuredDimension(getMaxLength(rowsLength), mRows * (getChildAt(0).getMeasuredHeight() + (int) mVerticalDis) - (int) mVerticalDis);
            }
        }
    }

    //关键点：有了mRows标签行数，就意味着高度已确定，现只需要在onLayout对布局的宽度进行动态分配
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            //如果是纵向
            if (mOrientation == 0) {
                int index = 0;
                int currentTop = 0;
                for (int rowCount : mCountPerRow) {
                    int currentLeft = 0;
                    for (int i = 0; i < rowCount; i++) {
                        View child = getChildAt(index++);
                        child.layout(currentLeft, currentTop, currentLeft + child.getMeasuredWidth(), currentTop + child.getMeasuredHeight());
                        currentLeft += (child.getMeasuredWidth() + mHorizontalDis);
                    }
                    currentTop += (getChildAt(index - 1).getMeasuredHeight() + mVerticalDis);
                }
            }
            //如果是横向
            else {
                initRowsLength();
                int left;
                int top;
                for (int i = 0; i < mChildCount; i++) {
                    View child = getChildAt(i);
                    //获取子View长和宽
                    int childWidth = child.getMeasuredWidth();
                    int childHeight = child.getMeasuredHeight();
                    //获取最短行作为当前行
                    int currentRow = getMinRow(rowsLength);
                    if (rowsLength[currentRow] == 0) {
                        left = rowsLength[currentRow];
                        rowsLength[currentRow] += childWidth;
                    } else {
                        left = rowsLength[currentRow] + (int) mHorizontalDis;
                        rowsLength[currentRow] += childWidth + (int) mHorizontalDis;
                    }
                    if (currentRow == 0) {
                        top = 0;
                    } else {
                        top = currentRow * (childHeight + (int) mVerticalDis);
                    }
                    child.layout(left, top, left + childWidth, top + childHeight);
                }
            }
        }
    }

    //获取最小长度的那一行
    private int getMinRow(int... lengths) {
        int index = 0;
        if (lengths.length > 0) {
            int minLength = lengths[0];
            if (lengths.length > 1) {
                for (int i = 1; i < lengths.length; i++) {
                    if (lengths[i] < minLength) {
                        minLength = lengths[i];
                        index = i;
                    }
                }
            }
        }
        return index;
    }

    //获取长度数组中的最大长度
    private int getMaxLength(int... lengths) {
        int maxLength = 0;
        if (lengths.length > 0) {
            maxLength = lengths[0];
            if (lengths.length > 1) {
                for (int i = 1; i < lengths.length; i++) {
                    maxLength = lengths[i] > maxLength ? lengths[i] : maxLength;
                }
            }
        }
        return maxLength;
    }

    /**
     * add child view
     * @param tagLayoutId the layout id of your tag view
     * @param names the text of your tag view
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public <T extends TextView> void addTag(int tagLayoutId, String... names) {
        for (String name : names) {
            T tag = (T) mInflater.inflate(tagLayoutId, null);
            tag.setText(name);
            tag.setOnClickListener(this);
            addView(tag);
        }
        invalidateView();
    }

    //重绘
    public void invalidateView() {
        isFirst = true;
        mChild.clear();
        if (mOrientation == 1)
            initRowsLength();
        if (Looper.myLooper() == Looper.getMainLooper()) {
            invalidate();
        } else postInvalidate();
    }

    public void addChildView(View v) {
        addView(v);
        v.setOnClickListener(this);
        invalidateView();
    }

    public void removeChildView(View v) {
        if (mChild.contains(v)) {
            removeView(v);
            invalidateView();
        }
    }

    public void setOnSelectListener(OnSelectListener listener) {
        this.selectListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (selectListener != null)
            selectListener.onSelect(v, mChild.indexOf(v));
    }

    public List<View> getTags() {
        return mChild;
    }

    public interface OnSelectListener {
        void onSelect(View tag, int position);
    }
}
