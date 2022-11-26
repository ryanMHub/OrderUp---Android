package com.practice.practiceorderup01;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import org.jetbrains.annotations.NotNull;

//This class creates a custom spinner
public class CustomSpinner extends AppCompatSpinner {

    //interface is for the actions to occur when the window is open and closed
    public interface OnSpinnerEventsListener{
        void onPopupWindowOpened(Spinner spinner);
        void onPopupWindowClosed(Spinner spinner);
    }

    private OnSpinnerEventsListener mListener;
    private boolean mOpenInitiated = false;


    public CustomSpinner(@NonNull @NotNull Context context) {
        super(context);
    }

    public CustomSpinner(@NonNull @NotNull Context context, int mode) {
        super(context, mode);
    }

    public CustomSpinner(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSpinner(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomSpinner(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    public CustomSpinner(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);
    }

    //on click set window to open
    @Override
    public boolean performClick(){
        mOpenInitiated = true;
        if(mListener != null){
            mListener.onPopupWindowOpened(this);
        }
        return super.performClick();
    }

    //execute when change occurs in spinner
    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        if(hasBeenOpened() && hasFocus){
            performClosedEvent();
        }
    }

    //Register the listener for the spinner
    public void setSpinnerEventsListener(OnSpinnerEventsListener onSpinnerEventsListener){
        mListener = onSpinnerEventsListener;
    }

    //perform event when the window is closed
    public void performClosedEvent(){
        mOpenInitiated = false;
        if(mListener != null){
            mListener.onPopupWindowClosed(this);
        }
    }

    //boolean for opened spinner
    public boolean hasBeenOpened(){
        return mOpenInitiated;
    }

}
