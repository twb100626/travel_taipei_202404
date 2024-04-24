package com.example.traveltaipeiapplication.adapter

import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.traveltaipeiapplication.databinding.LayoutListAttractionBinding
import com.example.traveltaipeiapplication.model.Attraction
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.assist.ImageScaleType
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener


class AttractionFragmentAdapter(val listener : View.OnClickListener?, val bottomReachedListener: OnBottomReachedListener?) : RecyclerView.Adapter<AttractionFragmentAdapter.ViewHolder>() {
    companion object {
        private val TAG = "[TravelTaipei]" + AttractionFragmentAdapter::class.java.simpleName
    }

    val attractionList = mutableListOf<Attraction>()

    class ViewHolder : RecyclerView.ViewHolder {

        var mBinding : LayoutListAttractionBinding? = null
        constructor(binding : LayoutListAttractionBinding) : super(binding.root) {
            mBinding = binding
        }

        fun bind(item : Attraction) {
            mBinding?.item = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding  = LayoutListAttractionBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        if (listener != null) binding.root.setOnClickListener(listener)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return attractionList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var attraction = attractionList.get(position)
        holder.itemView.setTag(attraction);
        holder.bind(attraction)

        var url = if (attraction.images != null && attraction.images.size > 0) attraction.images[0].src else null
        if (url != null) {
            val imageLoader = ImageLoader.getInstance()
            val mImageOptionAD = DisplayImageOptions.Builder()
                    .considerExifParams(true)
                    .cacheInMemory(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .imageScaleType(ImageScaleType.NONE)
                    .build()
            imageLoader.displayImage(url,
                    holder.mBinding?.iv1,
                    mImageOptionAD,
                    object : SimpleImageLoadingListener() {
                        override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                            super.onLoadingComplete(imageUri, view, loadedImage)
                            val imageView: android.widget.ImageView = view as android.widget.ImageView
                            imageView.setScaleType(android.widget.ImageView.ScaleType.CENTER_CROP)
                        }

                        override fun onLoadingFailed(imageUri: String?, view: View?, failReason: FailReason?) {
                            super.onLoadingFailed(imageUri, view, failReason)
                            Log.d(TAG, "Image loading failed: " + failReason?.cause.toString())
                        }
                    })
        } else {
            holder.mBinding?.iv1?.setImageResource(0)
        }

        bottomReachedListener?.let {
            if (position == attractionList.size - 1)
                it.onBottomReached(position)
        }
    }

    fun addItems(list : List<Attraction>) {
        attractionList.addAll(list)
        notifyDataSetChanged()
    }
}