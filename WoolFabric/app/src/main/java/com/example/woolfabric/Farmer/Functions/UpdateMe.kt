package com.example.woolfabric.Farmer.Functions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import com.example.woolfabric.AllFunction
import com.example.woolfabric.R
import com.example.woolfabric.Responses.CommonResponse
import com.example.woolfabric.Responses.RetroFit
import com.example.woolfabric.databinding.ActivityUpdateMeBinding
import com.google.android.gms.common.internal.service.Common
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateMe : AppCompatActivity() {
    lateinit var allFunction: AllFunction
    private lateinit var bind:ActivityUpdateMeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivityUpdateMeBinding.inflate(layoutInflater)
        setContentView(bind.root)
        allFunction=AllFunction(this)
        val shared=getSharedPreferences("user", MODE_PRIVATE)?.apply {
            bind.uname.setText(getString("name",""))
            bind.umail.setText(getString("mail",""))
            bind.umobile.setText(getString("mobile",""))
            bind.upassword.setText(getString("password",""))
            bind.howmany.setText(getString("count",""))
            bind.imageView7.load(getString("image",""))
        }

        bind.updatebtn2.setOnClickListener {
            val uname=bind.uname.text.toString().trim()
            val umail=bind.umail.text.toString().trim()
            val umobile=bind.umobile.text.toString().trim()
            val upassword=bind.upassword.text.toString().trim()
            val howmany=bind.howmany.text.toString().trim()
            if(uname.isEmpty()){
                allFunction.toast("Please enter your Name")
            }else
            if(umail.isEmpty()){
                allFunction.toast("Please enter your Mail")
            }else
            if(umobile.isEmpty()){
                allFunction.toast("Please enter your Mobile number")
            }else if(umobile.length!=10){
                allFunction.toast("Please enter valid mobile number")
            }else if(upassword.isEmpty()){
                allFunction.toast("Please enter your password")
            }else if(howmany.isEmpty()){
                allFunction.toast("Please enter your Sheep count")
            }else{
                allFunction.p.show()
                CoroutineScope(IO).launch {
                    RetroFit.instance.updateprofile(
                        name=uname,
                                mobile=umobile,
                                mail=umail,
                                password=upassword,
                                count=howmany,
                                id="${shared?.getString("id","")}",
                    ).enqueue(object :Callback<CommonResponse>{
                        override fun onResponse(
                            call: Call<CommonResponse>,
                            response: Response<CommonResponse>
                        ) {
                          response.body()?.apply {
                              allFunction.toast(message)
                          if(message=="success"){
                              shared?.edit()?.apply {
                                  putString("name",uname)
                                  putString("mail",umail)
                                  putString("mobile",umobile)
                                  putString("password",upassword)
                                  putString("count",howmany)
                              apply()
                              }
                          }
                              allFunction.p.dismiss()

                          }
                        }

                        override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                           allFunction.apply {
                               p.dismiss()
                               toast(t.message!!)
                           }
                        }
                    })
                }
            }
        }
    }
}