package com.example.traveltaipeiapplication.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.traveltaipeiapplication.R
import com.example.traveltaipeiapplication.adapter.NewsFragmentAdapter
import com.example.traveltaipeiapplication.adapter.OnBottomReachedListener
import com.example.traveltaipeiapplication.model.News

class NewsFragment : Fragment() {
    companion object {
        private val TAG = "[TravelTaipei]" + NewsFragment::class.java.simpleName

        fun newInstance(newsList : List<News>, listener : View.OnClickListener?, bottomReachedListener: OnBottomReachedListener) : NewsFragment {
            var fragment = NewsFragment()
            fragment.setNewList(newsList)
            fragment.setListener(listener)
            fragment.setBottomReachedListener(bottomReachedListener)
            return fragment;
        }
    }

    private var newsList : List<News>? = null
    private var listener : View.OnClickListener? = null
    private lateinit var adapter : NewsFragmentAdapter
    private var bottomReachedListener : OnBottomReachedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home,
                container,
                false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view);
        adapter = NewsFragmentAdapter(listener, bottomReachedListener)
        adapter.addItems(newsList as List<News>)
        recyclerView.adapter = adapter
    }

    fun setNewList(list : List<News>) {
        newsList = list
    }

    fun setListener(listener : View.OnClickListener?) {
        this.listener = listener
    }

    fun addNews(list : List<News>) {
        adapter.addItems(list)
    }

    fun setBottomReachedListener(bottomReachedListener: OnBottomReachedListener) {
        this.bottomReachedListener = bottomReachedListener
    }
}