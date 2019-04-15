package in.srain.cube.views.ptr;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * 自定义header
 */
public class PtrEventHeader extends FrameLayout implements PtrUIHandler {

    private TextView mPtrHeaderText;
    private ImageView mLoading;

    public PtrEventHeader(Context context) {
        super(context);
        initViews();
    }

    public PtrEventHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public PtrEventHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    protected void initViews() {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.ptr_event_header, this);
        mPtrHeaderText = header.findViewById(R.id.ptr_header_text);
        mLoading = header.findViewById(R.id.iv_loading);
        resetView();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }

    private void resetView() {
        mPtrHeaderText.setText("下拉刷新");
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        resetView();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        if (frame.isPullToRefresh()) {
            mPtrHeaderText.setText("下拉刷新");
        } else {
            mPtrHeaderText.setText("下拉刷新");
        }
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        mPtrHeaderText.setText("加载中...");

        mLoading.setImageResource(R.drawable.event_progress_loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) mLoading.getDrawable();
        animationDrawable.start();

    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        mPtrHeaderText.setText("加载结束");
    }


    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();

        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromBottomUnderTouch(frame);
            }
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromTopUnderTouch(frame);
            }
        }
    }

    private void crossRotateLineFromTopUnderTouch(PtrFrameLayout frame) {
        if (!frame.isPullToRefresh()) {
            mPtrHeaderText.setText("松手立即刷新");
        }
    }

    private void crossRotateLineFromBottomUnderTouch(PtrFrameLayout frame) {

        if (frame.isPullToRefresh()) {
            mPtrHeaderText.setText("下拉刷新");
        }
    }

}
