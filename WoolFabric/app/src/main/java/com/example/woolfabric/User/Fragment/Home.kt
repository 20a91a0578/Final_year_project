package com.example.woolfabric.User.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.woolfabric.Adapter.AdapterForUser
import com.example.woolfabric.Responses.LoginResponse
import com.example.woolfabric.Responses.Models.User
import com.example.woolfabric.Responses.RetroFit
import com.example.woolfabric.databinding.ActivityHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Home : Fragment() {
    lateinit var bind:ActivityHomeBinding
lateinit var data:ArrayList<User>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind= ActivityHomeBinding.inflate(layoutInflater)
        data= ArrayList()
        bind.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                ready(query!!)
            return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                ready(newText!!)
             return false
            }
        })
        getfarmers()

        return bind.root
    }

    private fun ready(query: String) {
    val newdata=ArrayList<User>()
        for (datum in data) {
            if(datum.name.lowercase().contains(query.lowercase())){
                newdata.add(datum)
            }else if(datum.mobile.lowercase().contains(query.lowercase())){
                newdata.add(datum)
            }
        }

        bind.cycle4.apply {
            adapter=AdapterForUser(requireContext(), newdata, "user", "nothing")
        }
   }


    private fun getfarmers() {
        CoroutineScope(Dispatchers.IO).launch {
            RetroFit.instance.getboth("getboth").enqueue(object :
                Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    response.body()?.let {

                        bind.cycle4.apply {
                            layoutManager=LinearLayoutManager(requireContext())
                            adapter= AdapterForUser(
                                requireContext(),
                                it.data,
                                "farmer",
                                "no control"
                            )
                        data=it.data
                        }
                    }

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}