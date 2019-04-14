package in.srain.cube.views.ptr;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义header
 */
public class PtrEventHeader extends FrameLayout implements PtrUIHandler {

    private View mProgressBar;
    private TextView mPtrHeaderText;

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
        mProgressBar = header.findViewById(R.id.ptr_header_rotate_view_progressbar);
        mPtrHeaderText = header.findViewById(R.id.ptr_header_text);
        resetView();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }

    private void resetView() {
        mProgressBar.setVisibility(VISIBLE);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        resetView();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        mProgressBar.setVisibility(INVISIBLE);
        mPtrHeaderText.setText("Prepare");
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        mProgressBar.setVisibility(VISIBLE);
        mPtrHeaderText.setText("开始");
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        mProgressBar.setVisibility(INVISIBLE);
        mPtrHeaderText.setText("结束");
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

        }
    }

    private void crossRotateLineFromBottomUnderTouch(PtrFrameLayout frame) {

        if (frame.isPullToRefresh()) {

        } else {

        }
    }

}
