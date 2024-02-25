package com.example.woolfabric.Farmer.Fragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.woolfabric.Adapter.ProductsView
import com.example.woolfabric.Adapter.interfere
import com.example.woolfabric.Farmer.Functions.Additems
import com.example.woolfabric.Responses.CustomResponse
import com.example.woolfabric.Responses.RetroFit
import com.example.woolfabric.databinding.ActivityProductsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Products : Fragment() {
    private lateinit var bind:ActivityProductsBinding
    lateinit var interfere: interfere
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        interfere=activity as interfere
        bind=ActivityProductsBinding.inflate(layoutInflater)
        interfere.view(0)
        bind.cycle3.layoutManager=LinearLayoutManager(requireContext())
        bind.additem.setOnClickListener{startActivity(Intent(requireContext(), Additems::class.java))}

    viewdata()
        return bind.root
    }


    private fun viewdata() {
        val id=context?.getSharedPreferences("user",AppCompatActivity.MODE_PRIVATE)?.getString("id","")
        CoroutineScope(IO).launch {
            RetroFit.instance.getProducts(
                condition = "getmyproducts",
                id="$id"
            ).enqueue(object :Callback<CustomResponse>{
                override fun onResponse(
                    call: Call<CustomResponse>,
                    response: Response<CustomResponse>
                ) {
                    response.body()?.let {

                        bind.cycle3.apply {
                         setItemViewCacheSize(it.data.size)
                            adapter = ProductsView(requireContext(), it.data)
                        }
                        }
                }

                override fun onFailure(call: Call<CustomResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        viewdata()
    }
}