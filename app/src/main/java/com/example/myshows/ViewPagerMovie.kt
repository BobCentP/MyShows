package com.example.myshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

class ViewPagerMovie: Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)





    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view:View= inflater.inflate(R.layout.view_pager_mvoie,container,false)
        val fragments= listOf(MovieListFragment(0),MovieListFragment(1), MovieListFragment(2), MovieListFragment(3), MovieListFragment(4))
        val viewPager:ViewPager2= view.findViewById(R.id.pager)
        val adapter= MyCustomAdapter(this, fragments)
        viewPager.adapter= adapter
        return view
    }
}