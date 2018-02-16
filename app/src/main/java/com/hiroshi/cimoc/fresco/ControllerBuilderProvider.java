package com.hiroshi.cimoc.fresco;

import android.content.Context;

import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilderSupplier;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.hiroshi.cimoc.manager.SourceManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hiroshi on 2016/9/5.
 */
public class ControllerBuilderProvider {

    private Context mContext;
    private Map<String, PipelineDraweeControllerBuilderSupplier> mSupplierHash;
    private Map<String, ImagePipeline> mPipelineHash;
    private boolean mCover;

    public ControllerBuilderProvider(Context context, boolean cover) {
        mSupplierHash = new HashMap<>();
        mPipelineHash = new HashMap<>();
        mContext = context;
        mCover = cover;
    }

    public PipelineDraweeControllerBuilder get(String sourceId) {
        PipelineDraweeControllerBuilderSupplier supplier = mSupplierHash.get(sourceId);
        if (supplier == null) {
            ImagePipelineFactory factory = ImagePipelineFactoryBuilder
                    .build(mContext, SourceManager.getInstance().getHeader(sourceId), mCover);
            supplier = ControllerBuilderSupplierFactory.get(mContext, factory);
            mSupplierHash.put(sourceId, supplier);
            mPipelineHash.put(sourceId, factory.getImagePipeline());
        }
        return supplier.get();
    }

    public void pause() {
        for (String key : mPipelineHash.keySet()) {
            mPipelineHash.get(key).pause();
        }
    }

    public void resume() {
        for (String key : mPipelineHash.keySet()) {
            mPipelineHash.get(key).resume();
        }
    }

    public void clear() {
        for (String key : mPipelineHash.keySet()) {
            mPipelineHash.get(key).clearMemoryCaches();
        }
    }

}
