package com.example.traveltaipeiapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.traveltaipeiapplication.R
import com.example.traveltaipeiapplication.adapter.AttractionInfoImagesAdapter
import com.example.traveltaipeiapplication.databinding.FragmentAttractionInfoBinding
import com.example.traveltaipeiapplication.model.Attraction
import com.example.traveltaipeiapplication.model.AttractionInfoItem
import com.example.traveltaipeiapplication.util.Utils


class AttractionInfoFragment : Fragment() {
    companion object {
        private val TAG = "[TravelTaipei]" + AttractionInfoFragment::class.java.simpleName

        fun newInstance(attraction : Attraction, lang : Int, listener : View.OnClickListener?) : AttractionInfoFragment {
            var fragment = AttractionInfoFragment();
            fragment.setAttraction(attraction)
            fragment.setListener(listener)
            fragment.setLang(lang)
            return fragment;
        }

        @BindingAdapter("attractioninfoimagesadapter")
        @JvmStatic
        fun setArticleParagraphAdapter(view: ViewPager, adapter: AttractionInfoImagesAdapter) {
            if (view.adapter !== adapter) {
                view.adapter = adapter
            }
        }
    }

    private var attraction : Attraction? = null
    private var listener : View.OnClickListener? = null
    private lateinit var mBinding: FragmentAttractionInfoBinding
    private var lang = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_attraction_info, container, false);
        var adapter = AttractionInfoImagesAdapter(getChildFragmentManager())
        attraction?.images?.let {
            if (it.size > 0) adapter.setImages(attraction?.images)
            else {
                mBinding.flImages.visibility = View.GONE
            }
        }
        var item = AttractionInfoItem(adapter)
        context?.let {
            item.opentimeText = Utils.getLocaleStringResource(it, Utils.getLocaleByIntLang(lang), R.string.txt_attraction_opentime) + ": " + (attraction?.open_time?.trim() ?: "")
            item.addressText = Utils.getLocaleStringResource(it, Utils.getLocaleByIntLang(lang), R.string.txt_attraction_address) + ": " + (attraction?.address ?: "")
            item.telText = Utils.getLocaleStringResource(it, Utils.getLocaleByIntLang(lang), R.string.txt_attraction_tel) + ": " + (attraction?.tel ?: "")
            item.urlText = Utils.getLocaleStringResource(it, Utils.getLocaleByIntLang(lang), R.string.txt_attraction_url) + ":&nbsp;<u>" + (attraction?.url ?: "") + "</u>"
            item.introductionText = (attraction?.introduction ?: "")
            item.remindText = Utils.getLocaleStringResource(it, Utils.getLocaleByIntLang(lang), R.string.txt_attraction_remind) + ": " + (attraction?.remind?.trim() ?: "")
            item.listener = listener
        }
        mBinding.item = item
        mBinding.tabLayout.setupWithViewPager(mBinding.imageGallery)
        mBinding.tvUrl.setTag(attraction?.url)
        return mBinding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun setAttraction(attraction : Attraction?) {
        this.attraction = attraction
    }

    fun setListener(listener : View.OnClickListener?) {
        this.listener = listener
    }

    fun setLang(lang : Int) {
        this.lang = lang
    }
}