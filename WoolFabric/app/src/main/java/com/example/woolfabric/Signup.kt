package com.example.woolfabric

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.example.woolfabric.Responses.CommonResponse
import com.example.woolfabric.Responses.RetroFit
import com.example.woolfabric.databinding.ActivitySignupBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Signup : AppCompatActivity() {
    private lateinit var bind:ActivitySignupBinding
    private lateinit var allFunction: AllFunction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind=ActivitySignupBinding.inflate(layoutInflater)
        setContentView(bind.root)
        allFunction= AllFunction(this)

        bind.pass.setOnFocusChangeListener { _, b ->
            bind.name.isVisible = !b
            bind.mobile.isVisible=!b

        }
        bind.loginbtn.setOnClickListener {
            finish()
        }
        bind.signupbtn.setOnClickListener {
            val name=bind.name.text.toString().trim()
            val mobile=bind.mobile.text.toString().trim()
            val mail=bind.mail.text.toString().trim()
            val pass=bind.pass.text.toString().trim()
            if(name.isEmpty()){
                allFunction.toast("Please enter name")
            }else if(mobile.isEmpty()){
                allFunction.toast("Please enter mobile")
            }else if(mobile.length!=10){
                allFunction.toast("Please enter valid mobile number")
            }else if(mail.isEmpty()){
                allFunction.toast("Please enter mail")
            }else if(!mail.contains("@gmail.com"))
            allFunction.toast("Please enter a valid mail-id")
            else if(pass.isEmpty()){
                allFunction.toast("Please enter Password")
            }else{
                bind.pass.clearFocus()
                allFunction.p.show()
                CoroutineScope(IO).launch {
                    RetroFit.instance.signup(name=name,mail=mail,password=pass,mobile=mobile).enqueue(object :Callback<CommonResponse>{
                        override fun onResponse(
                            call: Call<CommonResponse>,
                            response: Response<CommonResponse>
                        ) {
                            allFunction.p.dismiss()
                            response.body()?.let{it.apply {
                                if(message=="Success"){
                                    finish()
                                }
                                allFunction.toast(message)
                            }
                            }

                        }

                        override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                            allFunction.p.dismiss()
                            allFunction.toast("${t.message}")
                        }
                    })
                }
            }
        }





    }

    override fun onBackPressed() {
    if(bind.pass.isFocusable){
        bind.pass.clearFocus()
    }else{
        super.onBackPressed()
    }
    }


}