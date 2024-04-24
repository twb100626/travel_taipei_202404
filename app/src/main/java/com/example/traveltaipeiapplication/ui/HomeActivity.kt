package com.example.traveltaipeiapplication.ui

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.example.traveltaipeiapplication.R
import com.example.traveltaipeiapplication.adapter.HomeFragmentPagerAdapter
import com.example.traveltaipeiapplication.adapter.OnBottomReachedListener
import com.example.traveltaipeiapplication.fragment.AttractionFragment
import com.example.traveltaipeiapplication.fragment.NewsFragment
import com.example.traveltaipeiapplication.model.Attraction
import com.example.traveltaipeiapplication.model.News
import com.example.traveltaipeiapplication.util.Utils
import com.example.traveltaipeiapplication.viewmodel.HomeViewModel
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.QueueProcessingType
import com.nostra13.universalimageloader.core.download.BaseImageDownloader
import java.util.*


class HomeActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private val TAG = "[TravelTaipei]" + HomeActivity::class.java.simpleName
    }

    private lateinit var viewModel : HomeViewModel
    private lateinit var langs : Array<String>
    private var oldPos = 0

    private lateinit var mPager : ViewPager
    private lateinit var mAdapter : HomeFragmentPagerAdapter

    lateinit var tvTitle: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initImageLoader()
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        initView()
    }

    fun initImageLoader() {
        val config = ImageLoaderConfiguration.Builder(this)
            .threadPriority(Thread.NORM_PRIORITY - 2)
            .denyCacheImageMultipleSizesInMemory()
            .diskCacheFileNameGenerator(Md5FileNameGenerator())
            .diskCacheSize(50 * 1024 * 1024) // 50
            .imageDownloader(BaseImageDownloader(getApplicationContext()))
            .tasksProcessingOrder(QueueProcessingType.LIFO)
            .build()

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config)
    }

    private fun initView() {
        langs = arrayOf(
                getString(R.string.language1),
                getString(R.string.language2),
                getString(R.string.language3),
                getString(R.string.language4),
                getString(R.string.language5),
                getString(R.string.language6),
                getString(R.string.language7),
                getString(R.string.language8),
                getString(R.string.language9)
        )
        tvTitle = findViewById(R.id.tvTitle)
        (findViewById(R.id.ivToolbarRightItem) as View).setOnClickListener(this)

        var mTabLayout = findViewById(R.id.tl_label) as TabLayout
        mPager = findViewById(R.id.viewpager) as ViewPager
        mPager.isHorizontalScrollBarEnabled = true
        mPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                Log.d(TAG, "page changed " + position)
                oldPos = position
            }

            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
        })

        mTabLayout.setupWithViewPager(mPager)
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.getAllNews().size == 0 && viewModel.getAllAttractions().size == 0) updateViews()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i(TAG, "orientation landscape")
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i(TAG, "orientation protrait")
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivToolbarRightItem -> {
                AlertDialog.Builder(this)
                        .setTitle(getString(R.string.choose_language))
                        .setItems(langs,
                                { dialog, which ->
                                    viewModel.setLang(which)
                                    updateViews()
                                })
                        .show()
            }
        }
    }

    fun updateViews() {
        tvTitle.let {
            it.setText(Utils.getLocaleStringResource(this, getLocale(), R.string.app_name))
        }
        val tabTitles =
                arrayOf(
                        getString(R.string.home_tab_1)
                        , getString(R.string.home_tab_2)
                )
        val contentFragments: Array<Fragment> = arrayOf(ListFragment(), ListFragment())
        mAdapter = HomeFragmentPagerAdapter(
                supportFragmentManager,
                contentFragments,
                tabTitles
        )
        mAdapter.let {
            var loc = Utils.getLocaleByIntLang(viewModel.getLang())
            val tabTitles =
                arrayOf(
                        Utils.getLocaleStringResource(this, loc, R.string.home_tab_1)
                        , Utils.getLocaleStringResource(this, loc, R.string.home_tab_2)
                )
            var pos = oldPos
            it.mPages = arrayOf(ListFragment(), ListFragment())
            it.mTitles = tabTitles
            mPager.adapter = it
            mPager.adapter = mAdapter
            mPager.setCurrentItem(pos)
        }
        viewModel.clear()
        getNews()
        getAttractions()
    }

    private fun getNews() {
        viewModel.getNews(object : HomeViewModel.NewsCallback {
            override fun onResponse(list: List<News>) {
                mAdapter.mPages[0].let {
                    when (viewModel.getAllNews().size) {
                        0 -> {
                            var pos = oldPos
                            mAdapter.mPages[0] = Fragment()
                            mPager.adapter = mAdapter
                            mPager.setCurrentItem(pos)
                        }
                        in 1.. viewModel.MaxItemCountPerPage -> {
                            var pos = oldPos
                            mAdapter.mPages[0] = NewsFragment.newInstance(list, newListener, newsBottomReachedListener)
                            mPager.adapter = mAdapter
                            mPager.setCurrentItem(pos)
                        }
                        else -> (it as NewsFragment).addNews(list)
                    }
                    if (viewModel.getAllNews().size <= viewModel.MaxItemCountPerPage) {

                    } else {
                        (it as NewsFragment).addNews(list)
                    }
                }
            }

            override fun onFailure(e: Exception?) {
                if (viewModel.getAllNews().size == 0) {
                    var pos = oldPos
                    mAdapter.mPages[0] = Fragment()
                    mPager.adapter = mAdapter
                    mPager.setCurrentItem(pos)
                }
                Toast.makeText(this@HomeActivity, Utils.getLocaleStringResource(this@HomeActivity, getLocale(), R.string.txt_load_fail_news), Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getAttractions() {
        viewModel.getAttractions(object : HomeViewModel.AttractionCallback {
            override fun onResponse(list: List<Attraction>) {
                mAdapter.mPages[1].let {
                    when (viewModel.getAllAttractions().size) {
                        0 -> {
                            var pos = oldPos
                            mAdapter.mPages[1] = Fragment()
                            mPager.adapter = mAdapter
                            mPager.setCurrentItem(pos)
                        }
                        in 1 .. viewModel.MaxItemCountPerPage -> {
                            var pos = oldPos
                            mAdapter.mPages[1] = AttractionFragment.newInstance(list, attractionListener, attractionBottomReachedListener)
                            mPager.adapter = mAdapter
                            mPager.setCurrentItem(pos)
                        }
                        else -> (it as AttractionFragment).addAttractions(list)
                    }
                }
            }

            override fun onFailure(e: Exception?) {
                if (viewModel.getAllAttractions().size == 0) {
                    var pos = oldPos
                    mAdapter.mPages[1] = Fragment()
                    mPager.adapter = mAdapter
                    mPager.setCurrentItem(pos)
                }
                Toast.makeText(this@HomeActivity, Utils.getLocaleStringResource(this@HomeActivity, getLocale(), R.string.txt_load_fail_attraction), Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getLocale() : Locale {
        return when (viewModel.getLang()) {
            0 -> Locale("zh-tw")
            1 -> Locale("zh")
            else -> Locale("en")
        }
    }

    private var newListener = View.OnClickListener {
        var news = it.getTag() as News
        startActivity(AttractionWebActivity.getActivityIntent(this,
                Utils.getLocaleStringResource(this, getLocale(), R.string.home_tab_1),
                news.url,
                null,
                0
        ))
    }

    private var attractionListener = View.OnClickListener {
        var attraction = it.getTag() as Attraction
        startActivity(AttractionWebActivity.getActivityIntent(this,
                attraction.name,
                null,
                Gson().toJson(attraction),
                viewModel.getLang()
        ))
    }

    private var newsBottomReachedListener = object : OnBottomReachedListener {
        override fun onBottomReached(position: Int) {
            getNews()
        }
    }

    private var attractionBottomReachedListener = object : OnBottomReachedListener {
        override fun onBottomReached(position: Int) {
            getAttractions()
        }
    }
}