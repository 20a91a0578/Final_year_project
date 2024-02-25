package com.example.woolfabric.Admin.Info

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS
import com.example.woolfabric.AllFunction
import com.example.woolfabric.Responses.CommonResponse
import com.example.woolfabric.Responses.PriceResponse
import com.example.woolfabric.Responses.RetroFit
import com.example.woolfabric.databinding.ActivityTradeinfoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class Price : AppCompatActivity() {
    private lateinit var bind:ActivityTradeinfoBinding
    lateinit var allFunction: AllFunction
    var old=0
var percentage=""
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind=ActivityTradeinfoBinding.inflate(layoutInflater)
        setContentView(bind.root)
  bind.price.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) { settingtext(p0.toString()) }
        })
        allFunction=AllFunction(this).apply {
            p.show()
        }
        val myday=Calendar.getInstance()
        val simple=SimpleDateFormat("dd-MM-yyyy")
        myday.add(Calendar.DATE,-1)

        CoroutineScope(IO).launch {

    RetroFit.instance.gettradeprice(condition = "todayprice").enqueue(object:
        Callback<PriceResponse> {
        @SuppressLint("SetTextI18n")
        override fun onResponse(call: Call<PriceResponse>, response: Response<PriceResponse>) {
            allFunction.p.dismiss()
            response.body()?.let {
                val data=it.data

                old = if(data.isNotEmpty()){
                    (data[0].price).toInt()
                }else{
                    0
                }
                bind.yesterday.text = HtmlCompat.fromHtml("<b>Last Price</b><br></br>₹$old/-",FROM_HTML_OPTION_USE_CSS_COLORS)
            }
        if(response.body()==null){
            finish()
            old=0
            allFunction.toast("Something went wrong")
        }
        }

        override fun onFailure(call: Call<PriceResponse>, t: Throwable) {
        allFunction.p.dismiss()
            finish()
            allFunction.toast(t.message!!)
        }
    })
}
        bind.upload.setOnClickListener {
            allFunction.p.show()
            val price=bind.price.text.toString().trim()
            if(price.isEmpty()) {
                allFunction.toast("Please enter today price")
            }else{
                CoroutineScope(IO).launch {
                    RetroFit.instance.addprice(
                        today = simple.format(Date()),
                        price = price,
                        percentage = percentage
                    ).enqueue(object : Callback<CommonResponse> {
                        override fun onResponse(
                            call: Call<CommonResponse>,
                            response: Response<CommonResponse>
                        ) {
                            response.body()?.let {
                                allFunction.toast(it.message)
                                if(it.message=="success") {
                                    finish()
                                }
                                  }

                            allFunction.p.dismiss()
                        }

                        override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
allFunction.apply {
    toast(t.message!!)
    p.dismiss()
}
                        }
                    })
                }
            }
        }



    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    private fun settingtext(today:String) {
        val text = bind.percentage
        try {
           val decimal = DecimalFormat("##.##")

            val k =if(0!=old){
                decimal.format((today.toInt() - old) / old.toFloat() * 100)
            }else{
                today
            }

            if (0.00F== k.toFloat()) {
                text.setTextColor(Color.GRAY)
            } else if (0.00 >= k.toFloat()) {
                text.setTextColor(Color.RED)
            } else if (0.00 <= k.toFloat()) {
                text.setTextColor(Color.GREEN)
            } else {
                text.setTextColor(Color.BLACK)
            }
            percentage=k

            text.text ="$k%"
        } catch (e: Exception) {
            text.setTextColor(Color.GRAY)
            text.text = "0.00%"
        }
    }
}