package com.example.woolfabric.Admin.Fargments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.woolfabric.Adapter.AdminHome
import com.example.woolfabric.AllFunction
import com.example.woolfabric.R
import com.example.woolfabric.Responses.CountResponse
import com.example.woolfabric.Responses.Models.Count
import com.example.woolfabric.Responses.RetroFit


import com.example.woolfabric.databinding.ActivityInfoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Info : Fragment() {
    private lateinit var allFunction: AllFunction
    private lateinit var bind:ActivityInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        allFunction= AllFunction(requireContext())
        bind= ActivityInfoBinding.inflate(layoutInflater)
        getdata()



        return bind.root
    }

    override fun onResume() {
        getdata()
        super.onResume()
    }

    private fun getdata() {
        allFunction.p.show()
        CoroutineScope(IO).launch {
            RetroFit.instance.getcount("count").enqueue(object :Callback<CountResponse>{
                override fun onResponse(
                    call: Call<CountResponse>,
                    response: Response<CountResponse>
                ) {
                  val ll=  response.body()?.apply{

                        val array=ArrayList<String>()
                        array.add("News")
                        array.add("Trade News")
                        array.add("Wool Growth")
                        array.add("Price")
                        val drawable=ArrayList<Int>()
                        drawable.add(R.drawable.woofar)
                        drawable.add(R.drawable.stock)
                        drawable.add(R.drawable.newspaper)
                        drawable.add(R.drawable.ruppes)

                        bind.cycle.layoutManager=GridLayoutManager(requireContext(),2)
                        bind.cycle.adapter=AdminHome(requireContext(),array,drawable,data)
                  }
                    allFunction.p.dismiss()
                    if(ll==null){
nothing()
                    }
                }

                override fun onFailure(call: Call<CountResponse>, t: Throwable) {
            allFunction.p.show()
                    nothing()
                }
            })
        }
    }

    private fun nothing() {
        val data=ArrayList<Count>()
            data.add(Count(type = "News","0"))
            data.add(Count(type =    "Trade News","0"))
            data.add(Count(type =    "Wool Growth","0"))
            data.add(Count(type = "Price","0"))

        val array=ArrayList<String>()
        array.add("News")
        array.add("Trade News")
        array.add("Wool Growth")
        array.add("Price")
        val drawable=ArrayList<Int>()
        drawable.add(R.drawable.woofar)
        drawable.add(R.drawable.stock)
        drawable.add(R.drawable.newspaper)
        drawable.add(R.drawable.ruppes)

        bind.cycle.layoutManager=GridLayoutManager(requireContext(),2)
        bind.cycle.adapter=AdminHome(requireContext(),array,drawable,data)

    }
}