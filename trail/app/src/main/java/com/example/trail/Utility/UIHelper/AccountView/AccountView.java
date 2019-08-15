package com.example.trail.Utility.UIHelper.AccountView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.palette.graphics.Palette;

import com.example.trail.R;

import static com.example.trail.Utility.UIHelper.AccountView.Utils.DEFAULT_ALPHA;
import static com.example.trail.Utility.UIHelper.AccountView.Utils.DEFAULT_ANGLE;
import static com.example.trail.Utility.UIHelper.AccountView.Utils.DEFAULT_TINT;
import static com.example.trail.Utility.UIHelper.AccountView.Utils.END_COLOR;
import static com.example.trail.Utility.UIHelper.AccountView.Utils.START_COLOR;
import static com.example.trail.Utility.UIHelper.AccountView.Utils.TAG_IMAGE;
import static com.example.trail.Utility.UIHelper.AccountView.Utils.TIDE_COUNT;
import static com.example.trail.Utility.UIHelper.AccountView.Utils.TIDE_HEIGHT_DP;

public class AccountView extends View {

    private ScaleType scaleType;
    private float width;
    private float height;
    private float requiredWidth;
    private float requiredHeight;
    private int tintColor;
    private Path path;
    private boolean autoTint;
    private RectF viewBounds, scaleRect;
    private int imageSource;
    private int x;
    private int y;
    private Context context;
    private Paint paint;
    private Bitmap bitmap;
    private int tideCount;
    private int tideHeight;
    private int alphaValue;
    private GradientType gradientType;
    private int gradientStartColor;
    private int gradientEndColor;
    private int gradientAngle;
    private boolean showGradient;
    private Shader gradientShader;

    public enum ScaleType {
        CENTRE_CROP(0),
        FIT_XY(1);
        final int value;

        ScaleType(int value) {
            this.value = value;
        }
    }

    public enum GradientType {
        LINEAR(0),
        RADIAL(1);
        final int value;

        GradientType(int value) {
            this.value = value;
        }
    }

    private static final ScaleType[] scaleTypeArray = {ScaleType.CENTRE_CROP, ScaleType.FIT_XY};

    private static final GradientType[] gradientTypeArray = {GradientType.LINEAR, GradientType.RADIAL};

    public AccountView(Context context) {
        super(context);
        init(context, null);
    }

    public AccountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AccountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        autoTint = true;
        tideCount = TIDE_COUNT;
        tideHeight = Utils.getPixelForDp(context, TIDE_HEIGHT_DP);
        alphaValue = DEFAULT_ALPHA;
        tintColor = DEFAULT_TINT;
        scaleType = ScaleType.CENTRE_CROP;
        gradientAngle = DEFAULT_ANGLE;
        gradientStartColor = START_COLOR;
        gradientEndColor = END_COLOR;
        gradientType = GradientType.LINEAR;
        showGradient = false;
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AccountView);
            if (array.hasValue(R.styleable.AccountView_src))
                imageSource = array.getResourceId(R.styleable.AccountView_src, -1);
            if (array.hasValue(R.styleable.AccountView_autoTint))
                autoTint = array.getBoolean(R.styleable.AccountView_autoTint, true);
            if (array.hasValue(R.styleable.AccountView_tintColor)) {
                autoTint = false;
                tintColor = array.getColor(R.styleable.AccountView_tintColor, DEFAULT_TINT);
            }
            if (array.hasValue(R.styleable.AccountView_tideCount)) {
                tideCount = array.getInt(R.styleable.AccountView_tideCount, TIDE_COUNT);
                if (tideCount < 3 || tideCount > 10)
                    tideCount = TIDE_COUNT;
            }
            if (array.hasValue(R.styleable.AccountView_tideHeight))
                tideHeight = array.getDimensionPixelSize(R.styleable.AccountView_tideHeight, Utils.getPixelForDp(context, TIDE_HEIGHT_DP));
            if (array.hasValue(R.styleable.AccountView_alphaValue)) {
                alphaValue = array.getInt(R.styleable.AccountView_alphaValue, DEFAULT_ALPHA);
                if (alphaValue > 255 || alphaValue < 0)
                    alphaValue = DEFAULT_ALPHA;
            }
            if (array.hasValue(R.styleable.AccountView_scaleType))
                scaleType = scaleTypeArray[array.getInt(R.styleable.AccountView_scaleType, 0)];
            if (array.hasValue(R.styleable.AccountView_gradient_startColor)) {
                gradientStartColor = array.getColor(R.styleable.AccountView_gradient_startColor, START_COLOR);
                showGradient = true;
            }
            if (array.hasValue(R.styleable.AccountView_gradient_endColor)) {
                gradientEndColor = array.getColor(R.styleable.AccountView_gradient_endColor, END_COLOR);
                showGradient = true;
            }
            if (array.hasValue(R.styleable.AccountView_gradientType))
                gradientType = gradientTypeArray[array.getInt(R.styleable.AccountView_gradientType, 0)];
            if (array.hasValue(R.styleable.AccountView_gradientAngle))
                if (Math.abs(array.getInt(R.styleable.AccountView_gradientAngle, DEFAULT_ANGLE)) <= 360)
                    gradientAngle = Math.abs(array.getInt(R.styleable.AccountView_gradientAngle, DEFAULT_ANGLE));
            array.recycle();
        }
        tideHeight /= 2;
        path = new Path();
        viewBounds = new RectF();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(tintColor);
        scaleRect = new RectF();
        this.context = context;
        if (imageSource != -1)
            try {
                bitmap = BitmapFactory.decodeResource(context.getResources(), imageSource);
                pickColorFromBitmap(bitmap);
            } catch (OutOfMemoryError error) {
                bitmap = null;
                Log.e(TAG_IMAGE, "Image is too large to process. " + error.getMessage());
            } catch (Exception e) {
                Log.e(TAG_IMAGE, e.getMessage());
            }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        Log.d("T", width + "");
        if (bitmap != null && scaleType == ScaleType.CENTRE_CROP) {
            float ratioChange = 1;
            if (width != bitmap.getWidth()) {
                ratioChange = width / bitmap.getWidth();
            }
            if (ratioChange * bitmap.getHeight() < height) {
                ratioChange = height / bitmap.getHeight();
            }
            requiredHeight = bitmap.getHeight() * ratioChange;
            requiredWidth = bitmap.getWidth() * ratioChange;
            y = (int) ((requiredHeight / 2) - (height / 2));
            x = (int) ((requiredWidth / 2) - (width / 2));
            if (x > 0) x = -x;
            if (y > 0) y = -y;
        }
        if (showGradient)
            if (gradientType == GradientType.LINEAR)
                gradientShader = GradientGenerator.getLinearGradient(gradientAngle, width, height, gradientStartColor, gradientEndColor);
            else
                gradientShader = GradientGenerator.getRadialGradient(width, height, gradientStartColor, gradientEndColor);

    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        int l = tideCount / 2;
        path = Utils.getWavePath(width, height, tideHeight, l * 20, 4);
        viewBounds.set(0, 0, width, height);
        canvas.clipPath(path);
        canvas.drawColor(Color.WHITE);
        paint.setAlpha(255);
        if (bitmap != null) {
            if (scaleType == ScaleType.CENTRE_CROP) {
                scaleRect.set(x, y, x + requiredWidth, y + requiredHeight);
                canvas.clipRect(scaleRect);
                canvas.drawBitmap(bitmap, null, scaleRect, paint);
            } else {
                canvas.drawBitmap(bitmap, null, viewBounds, paint);
            }
        }
        paint.setAlpha(alphaValue);
        canvas.drawPath(path, paint);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            canvas.clipRect(viewBounds, Region.Op.UNION);
        } else {
            canvas.clipRect(viewBounds);
        }
        for (int i = 1; i <= tideCount; i++) {
            path = Utils.getWavePath(width, height, tideHeight, i * i * 20, 3);
            canvas.drawPath(path, paint);
        }
        if (showGradient) {
            Paint p = new Paint();
            p.setShader(gradientShader);
            path = Utils.getWavePath(width, height, tideHeight, l * 20, 4);
            canvas.drawPath(path, p);
        }
    }

    /**
     * @param scaleType scaleType of the image on {@link AccountView}
     */
    public void setScaleType(@NonNull ScaleType scaleType) {
        this.scaleType = scaleType;
        invalidate();
    }


    /**
     * @param bitmap is object of Scaled Bitmap
     */
    public void setBitmap(@NonNull Bitmap bitmap) {
        this.bitmap = bitmap;
        pickColorFromBitmap(bitmap);
        invalidate();
    }

    /**
     * @param resId is drawable resource Id of image
     */
    public void setImageSource(@DrawableRes int resId) {
        this.imageSource = resId;
        try {
            bitmap = BitmapFactory.decodeResource(context.getResources(), imageSource);
            pickColorFromBitmap(bitmap);
        } catch (OutOfMemoryError error) {
            bitmap = null;
            Log.e(TAG_IMAGE, "Image is too large to process. " + error.getMessage());
        } catch (Exception e) {
            Log.e(TAG_IMAGE, e.getMessage());
        }
        invalidate();
    }

    /**
     * @param color is image tint to provide theme effect. This is optional.
     */
    public void setTintColor(@ColorInt int color) {
        if (color != 0) {
            this.tintColor = color;
            paint.setColor(tintColor);
            setAutoTint(false);
            invalidate();
        }
    }

    /**
     * Pick Color from bitmap
     *
     * @param bitmap
     */
    private void pickColorFromBitmap(Bitmap bitmap) {
        if (bitmap != null)
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                    int defaultColor = 0x000000;
                    if (autoTint) {
                        if (palette.getDarkVibrantColor(defaultColor) != 0) {
                            paint.setColor(Math.abs(palette.getDarkVibrantColor(defaultColor)));
                            Log.d(TAG_IMAGE, "#" + Math.abs(palette.getDarkVibrantColor(defaultColor)));
                        } else if (palette.getDarkMutedColor(defaultColor) != 0) {
                            Log.d(TAG_IMAGE, palette.getMutedColor(defaultColor) + "");
                            paint.setColor(Math.abs(palette.getDarkMutedColor(defaultColor)));
                        } else {
                            paint.setColor(DEFAULT_TINT);
                        }
                    } else
                        paint.setColor(tintColor);
                }
            });
    }

    /**
     * {@link AccountView} will automatically pick tintColor from image
     *
     * @param autoTint
     */
    public void setAutoTint(boolean autoTint) {
        this.autoTint = autoTint;
        pickColorFromBitmap(bitmap);
        invalidate();
    }

    /**
     * Draw {@link android.graphics.LinearGradient} on top of the image with gradientAngle
     *
     * @param gradientAngle
     * @param gradientStartColor
     * @param gradientEndColor
     */
    public void setLinearGradient(int gradientAngle, int gradientStartColor, int gradientEndColor) {
        showGradient = true;
        gradientShader = GradientGenerator.getLinearGradient(gradientAngle, width, height, gradientStartColor, gradientEndColor);
        invalidate();
    }

    /**
     * Draw {@link android.graphics.RadialGradient} on top of the image
     * @param gradientStartColor
     * @param gradientEndColor
     */
    public void setRadialGradient(int gradientStartColor, int gradientEndColor) {
        showGradient = true;
        gradientShader = GradientGenerator.getRadialGradient(width, height, gradientStartColor, gradientEndColor);
        invalidate();
    }

}

