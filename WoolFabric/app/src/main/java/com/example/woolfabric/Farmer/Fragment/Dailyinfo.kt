package com.example.woolfabric.Farmer.Fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.ScrollingView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.woolfabric.Adapter.InfoAdapter
import com.example.woolfabric.Adapter.interfere
import com.example.woolfabric.R
import com.example.woolfabric.Responses.DoubleResponse
import com.example.woolfabric.Responses.RetroFit
import com.example.woolfabric.databinding.ActivityDailyinfoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Dailyinfo : Fragment(){
    lateinit var interfere: interfere
    lateinit var linear:LinearLayoutManager
    private lateinit var bind:ActivityDailyinfoBinding
    lateinit var adapter:InfoAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind= ActivityDailyinfoBinding.inflate(layoutInflater)
        getdetails()
        interfere=activity as interfere
         linear=LinearLayoutManager(requireContext())
        bind.cycle2.layoutManager=linear

bind.cycle2.addOnScrollListener(object :RecyclerView.OnScrollListener(){
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val gg=linear.findLastVisibleItemPosition()-1
        bind.cardView2.isVisible = gg <= 1
        interfere.view(gg)
    }
})
        return bind.root
    }
    fun getdetails(){
        bind.cardView2.isVisible=false
        bind.cycle2.isVisible=false
        bind.lottie.isVisible=true
        CoroutineScope(IO).launch {
            RetroFit.instance.getdata("getdouble").enqueue(object :Callback<DoubleResponse>{
                @SuppressLint("SetTextI18n")
                override fun onResponse(
                    call: Call<DoubleResponse>,
                    response: Response<DoubleResponse>
                ) {
response.body()?.let {it.apply {
    bind.cycle2.setItemViewCacheSize(info.size)

    bind.cycle2.setHasFixedSize(true)
    bind.cycle2.isNestedScrollingEnabled=false

        adapter=InfoAdapter(requireContext(),info)
    bind.cycle2.adapter=adapter
    if(price.isNotEmpty()){
        val d=price[0]
        bind.price2.text="₹ ${d.price}/-"
        if(d.percentage.contains("-")){
            function(true)
        }else{
            function(false)
        }
        bind.percentage2.text="${d.percentage}%"

        bind.cardView2.isVisible=true

    }

}
}
                    if(response.body()==null){
                        Toast.makeText(requireContext(), "${response.body()}", Toast.LENGTH_SHORT).show()
                    }

                    bind.cycle2.isVisible=true
                    bind.lottie.isVisible=false
                }

                override fun onFailure(call: Call<DoubleResponse>, t: Throwable) {
                    bind.cycle2.isVisible=true
                    Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
                    bind.lottie.isVisible=false
                }
            })
        }

    }



    @SuppressLint("UseCompatLoadingForDrawables", "ResourceType")
    private fun function(b: Boolean) {
        if(b){
            bind.imageView4.setImageResource(R.drawable.decrease)
            bind.imageView4.setColorFilter(ContextCompat.getColor(requireActivity(),R.color.red))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context?.let { bind.percentage2.setTextColor(it.getColor(R.color.red)) }
            }else{
            bind.percentage2.setTextColor(Color.RED)
            }
        }else{
            bind.imageView4.setColorFilter(ContextCompat.getColor(requireContext(),R.color.greeen))

            bind.imageView4.setImageResource(R.drawable.trend)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context?.let { bind.percentage2.setTextColor(it.getColor(R.color.greeen)) }
            }else{
                bind.percentage2.setTextColor(Color.GREEN)
            }
        }
    }



}