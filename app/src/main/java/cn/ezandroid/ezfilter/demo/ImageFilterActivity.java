package cn.ezandroid.ezfilter.demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import cn.ezandroid.ezfilter.EZFilter;
import cn.ezandroid.ezfilter.core.RenderPipeline;
import cn.ezandroid.ezfilter.demo.render.LookupRender;
import cn.ezandroid.ezfilter.environment.TextureRenderView;
import cn.ezandroid.ezfilter.io.output.BitmapOutput;

/**
 * ImageFilterActivity
 *
 * @author like
 * @date 2017-09-16
 */
public class ImageFilterActivity extends BaseActivity {

    private TextureRenderView mRenderView;
    private ImageView mPreviewImage;

    private Bitmap mBitmap1;
    private Bitmap mBitmap2;

    private Bitmap mCurrentBitmap;

    private RenderPipeline mRenderPipeline;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_filter);
        mRenderView = $(R.id.render_view);
        mPreviewImage = $(R.id.preview_image);

        mBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.preview);
        mBitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        mCurrentBitmap = mBitmap1;

        mRenderPipeline = EZFilter.setBitmap(mCurrentBitmap)
                .addFilter(new LookupRender(ImageFilterActivity.this, R.drawable.langman))
                .into(mRenderView);

        $(R.id.change_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBitmap();
            }
        });

        $(R.id.capture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRenderPipeline.capture(new BitmapOutput.BitmapOutputCallback() {
                    @Override
                    public void bitmapOutput(final Bitmap bitmap) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mPreviewImage.setImageBitmap(bitmap);
                            }
                        });
                    }
                }, true);
                mRenderView.requestRender();
            }
        });
    }

    private void changeBitmap() {
        if (mCurrentBitmap == mBitmap1) {
            mCurrentBitmap = mBitmap2;
        } else {
            mCurrentBitmap = mBitmap1;
        }

        mRenderPipeline = EZFilter.setBitmap(mCurrentBitmap)
                .addFilter(new LookupRender(ImageFilterActivity.this, R.drawable.langman))
                .into(mRenderView);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
