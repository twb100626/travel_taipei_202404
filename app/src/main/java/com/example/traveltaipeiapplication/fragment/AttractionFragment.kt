package com.example.traveltaipeiapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.traveltaipeiapplication.R
import com.example.traveltaipeiapplication.adapter.AttractionFragmentAdapter
import com.example.traveltaipeiapplication.adapter.OnBottomReachedListener
import com.example.traveltaipeiapplication.model.Attraction

class AttractionFragment : Fragment() {
    companion object {
        private val TAG = "[TravelTaipei]" + AttractionFragment::class.java.simpleName

        fun newInstance(attractionList : List<Attraction>, listener : View.OnClickListener?, bottomReachedListener: OnBottomReachedListener) : AttractionFragment {
            var fragment = AttractionFragment();
            fragment.setAttractionList(attractionList)
            fragment.setListener(listener)
            fragment.setBottomReachedListener(bottomReachedListener)
            return fragment;
        }
    }

    private var attractionList : List<Attraction>? = null
    private var listener : View.OnClickListener? = null
    private lateinit var adapter : AttractionFragmentAdapter
    private var bottomReachedListener : OnBottomReachedListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home,
                container,
                false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        adapter = AttractionFragmentAdapter(listener, bottomReachedListener)
        adapter.addItems(attractionList as List<Attraction>)
        recyclerView.adapter = adapter
    }

    fun setAttractionList(list : List<Attraction>) {
        attractionList = list
    }

    fun setListener(listener : View.OnClickListener?) {
        this.listener = listener
    }

    fun addAttractions(list : List<Attraction>) {
        adapter.addItems(list)
    }

    fun setBottomReachedListener(bottomReachedListener: OnBottomReachedListener) {
        this.bottomReachedListener = bottomReachedListener
    }
}