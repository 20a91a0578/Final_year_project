package com.example.woolfabric.User.Functions

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.woolfabric.Adapter.SelectorAdapter
import com.example.woolfabric.Adapter.reponsive
import com.example.woolfabric.AllFunction
import com.example.woolfabric.Responses.CommonResponse
import com.example.woolfabric.Responses.Models.Selection
import com.example.woolfabric.Responses.Models.User
import com.example.woolfabric.Responses.CustomResponse
import com.example.woolfabric.Responses.RetroFit
import com.example.woolfabric.databinding.ActivityViewProductsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date

class ViewProducts : AppCompatActivity() ,reponsive{
    lateinit var allFunction: AllFunction
    val data=ArrayList<Selection>()
    private lateinit var bind:ActivityViewProductsBinding
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
bind= ActivityViewProductsBinding.inflate(layoutInflater)
        setContentView(bind.root)
        allFunction= AllFunction(this)
        var farmerid=""
    intent.getParcelableExtra<User>("data")?.let {
            with(it){
                val text="<b>Farmer name : </b>$name<br>" +
                        "<b>Mobile number : </b>$mobile<br>" +
                        "<b>Farmer mail : </b>$mail<br>" +
                        "<b>Sheep count : </b>$count<br>"
                bind.details5.text=HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS)
                bind.imageView5.load(image)
                allFunction.p.show()
                getdata(id)
                farmerid=id
            }
        }

    bind.payment.setOnClickListener {
      if(data.isEmpty()){
          Toast.makeText(this, "Sorry no items", Toast.LENGTH_SHORT).show()
      }else{
          val id=getSharedPreferences("user", MODE_PRIVATE).getString("id","")
          val simple=SimpleDateFormat("dd/mm/yyyy (hh:ss a)")
          allFunction.p.show()
          val orderid=System.currentTimeMillis()
          CoroutineScope(IO).launch {

          data.forEach { it ->
              if(it.qty!=0) {
                  CoroutineScope(IO).launch {
                      RetroFit.instance.addorders(
                          farmerid,
                          "$id",
                          "${it.pid}",
                          "${it.cost}",
                          "${it.qty}",
                          simple.format(Date()),
                          orderid = "$orderid"
                      ).enqueue(object :Callback<CommonResponse>{
                          override fun onResponse(
                              call: Call<CommonResponse>,
                              response: Response<CommonResponse>
                          ) {
                              response.body()?.let {
                                  if(it.message=="success"){
                                      finish()
                                  }

                              }
                              allFunction.p.dismiss()
                          }

                          override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                              allFunction.apply {
                                toast(t.message!! )
                                  p.dismiss()
                              }
                          }
                      })
                  }
              }else{
                  allFunction.p.dismiss()
              }

          }
              }

      }
    }
    }

    private fun getdata(id: String) {
        bind.cycle5.layoutManager=LinearLayoutManager(this)
        CoroutineScope(IO).launch {
            RetroFit.instance.getProducts("getmyproducts",id).enqueue(object :Callback<CustomResponse>{
                override fun onResponse(
                    call: retrofit2.Call<CustomResponse>,
                    response: Response<CustomResponse>
                ) {
                    response.body()?.let {
                        bind.cycle5.setItemViewCacheSize(data.size)
                        bind.cycle5.adapter=SelectorAdapter(it.data,this@ViewProducts,data,this@ViewProducts)
                    }
                    allFunction.p.dismiss()
                }

                override fun onFailure(call: retrofit2.Call<CustomResponse>, t: Throwable) {
               allFunction.p.dismiss()
                    Toast.makeText(this@ViewProducts, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    @SuppressLint("SetTextI18n")
    override fun position(position: Int){
        var num=0
        data.forEach {
            if(it.qty!=0) {
                num += it.cost * it.qty
            }
            }

        bind.textView5.text="Total cost ₹ $num/-"

    }
}