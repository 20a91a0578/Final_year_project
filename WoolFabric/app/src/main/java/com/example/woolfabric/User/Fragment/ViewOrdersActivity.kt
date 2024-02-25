package com.example.woolfabric.User.Fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.view.Display.Mode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.woolfabric.Adapter.ViewOrders
import com.example.woolfabric.R
import com.example.woolfabric.Responses.Models.ForUser
import com.example.woolfabric.Responses.RetroFit
import com.example.woolfabric.Responses.UsersOrders
import com.example.woolfabric.databinding.ActivityViewOrdersBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.security.auth.callback.Callback

class ViewOrdersActivity : Fragment() {
        private lateinit var bind:ActivityViewOrdersBinding
        var viewdata=ArrayList<ForUser>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind=ActivityViewOrdersBinding.inflate(layoutInflater)
        bind.cycle6.layoutManager=LinearLayoutManager(requireContext())
        bind.searchView2.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                query?.let { mytext(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
            newText?.let {  mytext(it)}
                return true
            }
        })
        getdata()


        return bind.root
    }

    private fun mytext(newText: String) {
    val new=ArrayList<ForUser>()

        viewdata.forEach {
            if(it.name.lowercase().contains(newText.lowercase())){
                new.add(it)
            }else if(it.orderid.lowercase().contains(newText.lowercase())){
                new.add(it)
            }else if(it.ordereddate.lowercase().contains(newText.lowercase())){
                new.add(it)
            }
        }
        bind.cycle6.adapter=ViewOrders(requireActivity(),new)


    }

    private fun getdata() {
        val id=requireActivity().getSharedPreferences("user",AppCompatActivity.MODE_PRIVATE).getString("id","")!!
        CoroutineScope(IO).launch {
            RetroFit.instance.viewData(condition = "getmyorders",id=id).enqueue(object :retrofit2.Callback<UsersOrders>{
                override fun onResponse(
                    call: retrofit2.Call<UsersOrders>,
                    response: Response<UsersOrders>
                ) {
                response.body()?.apply {
                    bind.cycle6.setItemViewCacheSize(data.size)
                    viewdata=data
                    bind.cycle6.adapter=ViewOrders(requireActivity(),data)
                }
                }

                override fun onFailure(call: retrofit2.Call<UsersOrders>, t: Throwable) {
                    Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

}