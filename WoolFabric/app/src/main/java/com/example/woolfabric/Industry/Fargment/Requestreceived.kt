package com.example.woolfabric.Industry.Fargment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.woolfabric.Adapter.RequestsView
import com.example.woolfabric.Responses.RequestResponse
import com.example.woolfabric.Responses.RetroFit
import com.example.woolfabric.databinding.ActivityRequestreceivedBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class Requestreceived : Fragment() {
    private lateinit var bind:ActivityRequestreceivedBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind=ActivityRequestreceivedBinding.inflate(layoutInflater)

        getdata()

        return bind.root
    }
    fun getdata(){
        bind.cycle10.layoutManager=LinearLayoutManager(requireContext())
val id=requireActivity().getSharedPreferences("user",AppCompatActivity.MODE_PRIVATE).getString("id","")
        CoroutineScope(Dispatchers.IO).launch {
            RetroFit.instance.getrequest("getfarmerrequest","$id").enqueue(object :retrofit2.Callback<RequestResponse>{
                override fun onResponse(
                    call: Call<RequestResponse>,
                    response: Response<RequestResponse>
                ) {
                    response.body()?.apply {
                        bind.cycle10.setItemViewCacheSize(data.size)
                        bind.cycle10.adapter= RequestsView(requireContext(), data, "change")
                    }
               }
                override fun onFailure(call: Call<RequestResponse>, t: Throwable) {
                    Toast.makeText(bind.root.context, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}