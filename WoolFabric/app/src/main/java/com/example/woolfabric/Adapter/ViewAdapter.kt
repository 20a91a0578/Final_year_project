package com.example.woolfabric.Adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.woolfabric.Admin.Fargments.Info
import com.example.woolfabric.Farmer.Fragment.Dailyinfo
import com.example.woolfabric.Farmer.Fragment.Others
import com.example.woolfabric.Farmer.Fragment.Products

class ViewAdapter(val cout :Int, faragment:FragmentManager) :FragmentPagerAdapter(faragment) {
    override fun getCount(): Int = cout

    override fun getItem(position: Int): Fragment {
Log.i("askfhjkashfjkad","$position")
return    when(position){
        0->{
            Dailyinfo()
        }
            1->{
    Products()
        }
    2->{
        Others()
    }
    else->{
    Dailyinfo()
    }

    }
    }

}
