package com.example.woolfabric.Industry

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.woolfabric.Adapter.ChechkofItem
import com.example.woolfabric.R
import com.example.woolfabric.Responses.CommonResponse
import com.example.woolfabric.Responses.Models.Customdata
import com.example.woolfabric.Responses.Models.StartMycheck
import com.example.woolfabric.Responses.ReportResponse
import com.example.woolfabric.Responses.RequestResponse
import com.example.woolfabric.Responses.RetroFit
import com.example.woolfabric.databinding.ActivityCheckoutMystockBinding
import com.google.android.gms.common.internal.service.Common
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date

class CheckoutMystock : AppCompatActivity() {
    val bind by lazy {
        ActivityCheckoutMystockBinding.inflate(layoutInflater)
    }
    @SuppressLint("SimpleDateFormat")
    val simple=SimpleDateFormat("dd/MM/yyyy HH.mm(ss)")
    var  customdata:Customdata? = null
    var data=ArrayList<StartMycheck>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        customdata=intent.getParcelableExtra("data")


        bind.cycle16.let{
            it.layoutManager=LinearLayoutManager(this)
            it.setItemViewCacheSize(data.size)
            it.adapter=ChechkofItem(this, data)
        }

        getdata()
        bind.appCompatButton.setOnClickListener {
            if(customdata!=null) {
                myfunction()
            }
        }


    }

    private fun getdata() {
        bind.second.isVisible=true
        bind.first.isVisible=false
        RetroFit.instance.getreport(condition = "getreport", id = "${customdata?.id}",dateof= simple.format(Date())).enqueue(object :Callback<ReportResponse>{
            override fun onResponse(
                call: Call<ReportResponse>,
                response: Response<ReportResponse>
            ) {
                try {
                    response.body()?.let {
                        if(it.data.isNotEmpty()){
                            bind.first.isVisible=true
                            val k=it.data[0]
                            data.add(StartMycheck(item = "Woolen processing", condition = k.woolenprocessing.toBoolean()))
                            data.add(StartMycheck(item = "scouring", condition = k.scouring.toBoolean()))
                            data.add(StartMycheck(item = "carding", condition = k.carding.toBoolean()))
                            data.add(StartMycheck(item = "spinning", condition = k.spinning.toBoolean()))
                        }else{
                            bind.second.isVisible=false
                            bind.first.isVisible=true
                            data.add(StartMycheck(item = "Woolen processing", condition = false))
                            data.add(StartMycheck(item = "scouring", condition = false))
                            data.add(StartMycheck(item = "carding", condition = false))
                            data.add(StartMycheck(item = "spinning", condition = false))
                        }

                    }
                }catch (e: Exception){
                    finish()
                }


                bind.second.isVisible=false
            }

            override fun onFailure(call: Call<ReportResponse>, t: Throwable) {
                finish()
                Toast.makeText(this@CheckoutMystock, "Sorry", Toast.LENGTH_SHORT).show()
bind.second.isVisible=false
            }
        })
    }

    @SuppressLint("SimpleDateFormat")
    private fun myfunction() {
        bind.second.isVisible=true
        var first:Boolean?=null
        var second:Boolean?=null
        var third:Boolean?=null
        var fourth:Boolean?=null
        var num=0
        data.forEach {
            when(num){
                0->{
                    first=it.condition
                }
                1->{
                    second=it.condition
                }
                2->{
                    third=it.condition
                }
                3->{
                    fourth=it.condition
                }
            }
            num++
        }



        RetroFit.instance.processing(
            requestid = "${customdata?.id}", woolenprocessing = "$first", scouring = "$second", carding = "$third",
            spinning = "$fourth",
            dateof= simple.format(Date())
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
                Toast.makeText(this@CheckoutMystock, "${response.body()?.message}", Toast.LENGTH_SHORT).show()
                      bind.second.isVisible=false
            }

            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
              bind.second.isVisible=false
            }
        })
    }
}