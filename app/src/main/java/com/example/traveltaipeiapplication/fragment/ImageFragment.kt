package com.example.traveltaipeiapplication.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.traveltaipeiapplication.R
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener

class ImageFragment(var url : String?) : Fragment() {

    companion object {
        private val TAG = "[TravelTaipei]" + ImageFragment::class.java.simpleName
    }

    private val mImageLoader = ImageLoader.getInstance()
    private var mImageOption: DisplayImageOptions? = null

    init {
        mImageOption = DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.adapter_attraction_info_images,
                container,
                false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView: ImageView = view.findViewById(R.id.img1)
        imageView.setScaleType(ImageView.ScaleType.CENTER)
        imageView.setBackgroundResource(R.color.gray_ffefefef)

        if (!TextUtils.isEmpty(url)) {
            mImageLoader.displayImage(url, imageView, mImageOption , object : SimpleImageLoadingListener() {
                override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                    imageView.setImageBitmap(loadedImage)
                    imageView.setBackgroundResource(R.color.transparent)
                }

                override fun onLoadingFailed(imageUri: String?, view: View?, failReason: FailReason?) {
                    Log.d(TAG, "Image loading failed: " + failReason?.cause.toString())
                }
            })
        }
    }
}