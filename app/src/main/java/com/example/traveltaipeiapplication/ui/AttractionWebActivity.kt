package com.example.traveltaipeiapplication.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.traveltaipeiapplication.R
import com.example.traveltaipeiapplication.fragment.AttractionInfoFragment
import com.example.traveltaipeiapplication.fragment.WebFragment
import com.example.traveltaipeiapplication.model.Attraction
import com.example.traveltaipeiapplication.viewmodel.AttractionWebViewModel
import com.google.gson.Gson

class AttractionWebActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private val TAG = "[TravelTaipei]" + AttractionWebActivity::class.java.simpleName
        val EXTRA_TITLE : String = "extra_title"
        val EXTRA_URL : String = "extra_url"
        val EXTRA_ATTRACTION : String  = "extra_attraction"
        val EXTRA_LANG : String = "extra_lang"

        fun getActivityIntent(context: Context, title : String, url : String?, attraction : String?, lang : Int): Intent {
            var intent =  Intent(context, AttractionWebActivity::class.java)
            intent.putExtra(EXTRA_TITLE, title)
            url?.let {
                intent.putExtra(EXTRA_URL, it)
            }
            attraction?.let {
                intent.putExtra(EXTRA_ATTRACTION, it)
            }
            intent.putExtra(EXTRA_LANG, lang)
            return intent
        }
    }

    private lateinit var viewModel : AttractionWebViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attraction_web)
        viewModel = ViewModelProviders.of(this).get(AttractionWebViewModel::class.java)
        intent.let {
            viewModel.setTitle(it.getStringExtra(EXTRA_TITLE))
            if (it.hasExtra(EXTRA_URL)) {
                viewModel.setUrl(it.getStringExtra(EXTRA_URL))
            }
            if (it.hasExtra(EXTRA_ATTRACTION)) {
                viewModel.setAttraction(Gson().fromJson(it.getStringExtra(EXTRA_ATTRACTION), Attraction::class.java))
            }
            viewModel.setLang(it.getIntExtra(EXTRA_LANG, 0))
        }
        initView()
    }

    fun initView() {
        (findViewById(R.id.imgBack) as View).setOnClickListener(this)
        (findViewById(R.id.tvTitle) as AppCompatTextView).setText(viewModel.getTitle())
    }

    override fun onResume() {
        super.onResume()
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount == 0) {
            viewModel.getUrl()?.let {
                var fragment = WebFragment.newInstance(it)
                replaceRootFragment(fragment, "URL")
            }
            viewModel.getAttraction()?.let {
                var fragment = AttractionInfoFragment.newInstance(
                        viewModel.getAttraction() as Attraction,
                        viewModel.getLang(),
                        object : View.OnClickListener {
                            override fun onClick(view: View?) {
                                var fragment = WebFragment.newInstance(view?.getTag() as String)
                                replaceRootFragment(fragment, "Click")
                            }
                        }
                )
                replaceRootFragment(fragment, "Attraction")
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imgBack -> {
                onBackPressed()
            }
        }
    }

    fun replaceRootFragment(fragment: Fragment, stackName: String?) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        Log.d(TAG, "count: " + fragmentManager.backStackEntryCount)

        transaction.addToBackStack(stackName)
        transaction.replace(R.id.layoutFragment, fragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount <= 1) {
            finish()
        } else {
            fragmentManager.popBackStack()
        }
    }
}