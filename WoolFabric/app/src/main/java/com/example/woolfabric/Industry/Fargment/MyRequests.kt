package com.example.woolfabric.Industry.Fargment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.woolfabric.Adapter.RequestsView
import com.example.woolfabric.Industry.ViewFarmers
import com.example.woolfabric.Responses.RequestResponse
import com.example.woolfabric.Responses.RetroFit
import com.example.woolfabric.databinding.ActivityMyRequestsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MyRequests : Fragment() {

private lateinit var bind:ActivityMyRequestsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind= ActivityMyRequestsBinding.inflate(layoutInflater)

        bind.sendre.setOnClickListener { startActivity(
            Intent(requireContext(),
                ViewFarmers::class.java)
        ) }
        getview()
        return bind.root
    }

    private fun getview() {

        val id=requireActivity().getSharedPreferences("user",AppCompatActivity.MODE_PRIVATE).getString("id","")
        CoroutineScope(Dispatchers.IO).launch {
            RetroFit.instance.getrequest("getindustryrequests","$id").enqueue(object :retrofit2.Callback<RequestResponse>{
                override fun onResponse(
                    call: Call<RequestResponse>,
                    response: Response<RequestResponse>
                ) {
try {
    response.body()?.apply {
        bind.cycle11.apply {

            layoutManager=LinearLayoutManager(requireActivity())
            setItemViewCacheSize(data.size)
            adapter = RequestsView(requireContext(), data,"not change")
        }
    }
}catch (e: Exception){
    Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_SHORT).show()
}

                }
                override fun onFailure(call: Call<RequestResponse>, t: Throwable) =
                    Toast.makeText(bind.root.context, "${t.message}", Toast.LENGTH_SHORT).show()
            })
        }
    }
}