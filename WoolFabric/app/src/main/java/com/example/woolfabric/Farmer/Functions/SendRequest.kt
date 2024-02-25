package com.example.woolfabric.Farmer.Functions

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.text.HtmlCompat
import coil.load
import com.example.woolfabric.AllFunction
import com.example.woolfabric.Responses.CommonResponse
import com.example.woolfabric.Responses.Models.User
import com.example.woolfabric.Responses.RetroFit
import com.example.woolfabric.databinding.ActivitySendRequestBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date

class SendRequest : AppCompatActivity() {
    lateinit var bind:ActivitySendRequestBinding
    private lateinit var allFunction: AllFunction
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind=ActivitySendRequestBinding.inflate(layoutInflater)
        setContentView(bind.root)
        allFunction= AllFunction(this)

        intent.getParcelableExtra<User>("data")?.apply {
            val text="<b>Industry Name :</b>$name<br>" +
                    "<b>Industry Mobile  number :</b>$mobile<br>" +
                    "<b>Industry Mail-Id :</b>$mail<br>"
        bind.details8.text=HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS)
        bind.imageview7.load(image)
       bind.send.setOnClickListener {
           val kg=bind.kg.text.toString().trim()
           val perkg=bind.perkg.text.toString().trim()
           val description=bind.desc.text.toString().trim()
           if(kg.isEmpty()){
               allFunction.toast("Please enter your kg")
           }else
           if(perkg.isEmpty()){
               allFunction.toast("Please enter your per kg how much")
           }else
           if(description.isEmpty()){
               allFunction.toast("Please enter your description")
           }else{
               val id1=getSharedPreferences("user", MODE_PRIVATE).getString("id","")!!
               val simple=SimpleDateFormat("dd/mm/yyy (hh:mm a)")
               CoroutineScope(IO).launch {
                   RetroFit.instance.sendrequets(
                       farmerid = id1,
                       indutryid = id,
                       requestsdate = simple.format(Date()),
                       kg,
                       perkg,
                       "pending",
                       description
                   ).enqueue(object :retrofit2.Callback<CommonResponse>{
                       override fun onResponse(
                           call: Call<CommonResponse>,
                           response: Response<CommonResponse>
                       ) {
                           allFunction.apply {

                               response.body()?.apply {
                                   if(message=="success"){
                                       finish()
                                   }
                                toast(message)
                               p.dismiss()
                               }

                               if(response.body()==null){
                                 allFunction.p.dismiss()
                                   toast("something went wrong")
                               }
                           }
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





    }
}