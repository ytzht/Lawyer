package com.onekeyask.lawyer.utils;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.flyco.dialog.widget.NormalDialog;
import com.onekeyask.lawyer.R;

public class EditDialog extends NormalDialog {

	private EditText editText;

	public EditDialog(Context context) {
		super(context);
		widthScale(0.88f);
		setEditText(new EditText(context));
		getEditText().setBackgroundResource(R.drawable.editcss);
		mTvContent = getEditText();
	}

	@Override
	public void setUiBeforShow() {
		super.setUiBeforShow();
		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)getEditText().getLayoutParams();
		layoutParams.leftMargin = layoutParams.rightMargin = layoutParams.bottomMargin = layoutParams.topMargin = dip2px(mContext, 10);
		getEditText().setPadding(dp2px(6),dp2px(2), dp2px(6),dp2px(2));
		mTvContent.setMinHeight(dp2px(36));
		getEditText().setLayoutParams(layoutParams);
	}
	public EditText getEditText() {
		editText.setSingleLine();
		editText.setMinHeight(100);
		return editText;
	}

	public void setEditText(EditText editText) {
		this.editText = editText;
	}


	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	private int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
}
