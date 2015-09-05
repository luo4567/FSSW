package main.gis.money.commonviewlibrary.radiobutton;

import com.xiaoai.ImageRadioButton.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RadioButton;

public class ImageRadioButton extends RadioButton {

	private Drawable buttonDrawable;

	private int buttonDrawableWidth, buttonDrawableHeight;

	private int buttonDrawablePaddingLeft;

	private Drawable imageDrawable;

	private int imageDrawableWidth, imageDrawableHeight;

	private int imageDrawablePaddingLeft;

	private Context context;

	public ImageRadioButton(Context context) {
		this(context, null);
	}

	public ImageRadioButton(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ImageRadioButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;

		setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
		setFocusable(true);
		setClickable(true);

		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.ImageCheckableButton);

		buttonDrawablePaddingLeft = array.getDimensionPixelSize(
				R.styleable.ImageCheckableButton_buttonPaddingLeft, 0);

		Drawable drawable = array
				.getDrawable(R.styleable.ImageCheckableButton_buttonDrawable);
		if (drawable != null) {
			setRadioButtonDrawable(drawable);

			int defWidth = drawable.getIntrinsicWidth();
			int defHeight = drawable.getIntrinsicHeight();
			buttonDrawableWidth = array.getDimensionPixelSize(
					R.styleable.ImageCheckableButton_buttonDrawableWidth,
					defWidth);
			buttonDrawableHeight = array.getDimensionPixelSize(
					R.styleable.ImageCheckableButton_buttonDrawableHeight,
					defHeight);
		}

		boolean checked = array.getBoolean(
				R.styleable.ImageCheckableButton_buttonChecked, false);
		setChecked(checked);

		Drawable content = array
				.getDrawable(R.styleable.ImageCheckableButton_imageDrawable);

		if (content != null) {
			int defWidth = content.getIntrinsicWidth();
			int defHeight = content.getIntrinsicHeight();
			imageDrawableWidth = array.getDimensionPixelSize(
					R.styleable.ImageCheckableButton_imageDrawableWidth,
					defWidth);
			imageDrawableHeight = array.getDimensionPixelSize(
					R.styleable.ImageCheckableButton_imageDrawableHeight,
					defHeight);
		}

		imageDrawablePaddingLeft = array.getDimensionPixelSize(
				R.styleable.ImageCheckableButton_imagePaddingLeft, 0);

		setImageDrawable(content);
		setCompoundDrawablePadding(imageDrawablePaddingLeft);

		array.recycle();
	}

	public void setRadioButtonDrawable(Drawable d) {
		if (d != null) {
			if (buttonDrawable != null) {
				buttonDrawable.setCallback(null);
				unscheduleDrawable(buttonDrawable);
			}
			d.setCallback(this);
			d.setState(getDrawableState());
			d.setVisible(getVisibility() == VISIBLE, false);
			buttonDrawable = d;
			buttonDrawable.setState(null);
			setMinHeight(buttonDrawable.getIntrinsicHeight());
		}

		refreshDrawableState();
	}

	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();

		if (buttonDrawable != null) {
			int[] myDrawableState = getDrawableState();

			// Set the state of the Drawable
			buttonDrawable.setState(myDrawableState);

			invalidate();
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		final Drawable button = buttonDrawable;
		if (button != null) {
			final int verticalGravity = getGravity()
					& Gravity.VERTICAL_GRAVITY_MASK;
			final int height = buttonDrawableHeight;

			int y = 0;

			switch (verticalGravity) {
			case Gravity.BOTTOM:
				y = getHeight() - height;
				break;
			case Gravity.CENTER_VERTICAL:
				y = (getHeight() - height) / 2;
				break;
			}

			button.setBounds(0 + buttonDrawablePaddingLeft, y,
					buttonDrawableWidth + buttonDrawablePaddingLeft, y + height);
			button.draw(canvas);
		}
	}

	public void setRadioButton(int resId) {
		setRadioButton(context.getResources().getDrawable(resId));
	}

	public void setRadioButton(Drawable drawable) {
		setRadioButtonDrawable(drawable);

		invalidate();
		requestLayout();
	}

	public void setRadioButtonDimensions(int width, int height) {
		this.buttonDrawableWidth = width;
		this.buttonDrawableHeight = height;

		invalidate();
		requestLayout();
	}

	public void setRadioButtonPadddingLefe(int paddingLeft) {
		this.buttonDrawablePaddingLeft = paddingLeft;

		invalidate();
		requestLayout();
	}

	public void setImageDrawable(int resId) {
		setImageDrawable(context.getResources().getDrawable(resId));
	}

	public void setImageDrawable(Drawable drawable) {
		this.imageDrawable = drawable;
		if (drawable != null) {
			drawable.setBounds(imageDrawablePaddingLeft, 0,
					imageDrawablePaddingLeft + imageDrawableWidth,
					imageDrawableHeight);
		}
		setCompoundDrawables(drawable, null, null, null);
	}

	public void setImageDrawableDimensions(int width, int height) {
		this.imageDrawableWidth = width;
		this.imageDrawableHeight = height;
		setImageDrawable(imageDrawable);

		invalidate();
		requestLayout();
	}

	public void setImagePaddingLeft(int paddingLeft) {
		this.imageDrawablePaddingLeft = paddingLeft;
		setImageDrawable(imageDrawable);

		invalidate();
		requestLayout();
	}

}
