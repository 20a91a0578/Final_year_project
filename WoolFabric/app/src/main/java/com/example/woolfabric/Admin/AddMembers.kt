package com.example.woolfabric.Admin

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputFilter
import android.text.InputType
import androidx.activity.result.contract.ActivityResultContracts
import com.example.woolfabric.AllFunction
import com.example.woolfabric.Responses.CommonResponse
import com.example.woolfabric.Responses.RetroFit
import com.example.woolfabric.databinding.ActivityAddMemberBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.ByteArrayOutputStream

class AddMembers : AppCompatActivity() {
    private var bitmap:Bitmap?=null
    var encode=""
    private lateinit var bind:ActivityAddMemberBinding
    lateinit var allfuntion:AllFunction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivityAddMemberBinding.inflate(layoutInflater)
        setContentView(bind.root)
        val types=intent.getStringExtra("type")
        allfuntion= AllFunction(this)
        val launch=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val data=it.data
            if(data!=null){
             bitmap=data.extras!!.get("data")as Bitmap
            bind.capture.setImageBitmap(bitmap)
            }
        }

        bind.capture.setOnClickListener {
            launch.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }
if(types!="Farmer"){
    val count=bind.count1
    bind.layoutname.hint="Company Address"
    count.maxLines=2
    count.inputType=InputType.TYPE_CLASS_TEXT
    count.filters= arrayOf(InputFilter.LengthFilter(100))
}
        bind.adding.setOnClickListener {
            val name1=bind.name1.text.toString().trim()
            val mobile1=bind.mobile1.text.toString().trim()
            val email1=bind.email1.text.toString().trim()
            val password1=bind.password1.text.toString().trim()
            val count=bind.count1.text.toString()

            if(name1.isEmpty()){
                allfuntion.toast("Please Enter name")
            }else
            if(mobile1.isEmpty()){
                allfuntion.toast("Please Enter mobile")
            }else
            if(email1.isEmpty()){
                allfuntion.toast("Please Enter email")
            }else if(password1.isEmpty()){
                allfuntion.toast("Please Enter password")
            }else if(!email1.contains("@gmail.com")){
                allfuntion.toast("Please enter a valid email id address")
            }else if(mobile1.length!=10){
                allfuntion.toast("Please enter a valid mobile number")
            }else if(count.isEmpty()){
                allfuntion.toast("Please enter  a number of count of sheeps")
            }else if(bitmap==null){
                allfuntion.toast("Please select Capture Image")
            }else{
                allfuntion.p.show()
                val out=ByteArrayOutputStream()
                bitmap?.compress(Bitmap.CompressFormat.PNG,100,out)
                val encode=android.util.Base64.encodeToString(out.toByteArray(),android.util.Base64.NO_WRAP)
                CoroutineScope(IO).launch {
                    RetroFit.instance.addfarmers(
                                name=name1,
                                mobile=mobile1,
                                email=email1,
                                password=password1,
                                count=count,
                                image=encode,
                                type="$types",
                    ).enqueue(object  :retrofit2.Callback<CommonResponse>{
                        override fun onResponse(
                            call: retrofit2.Call<CommonResponse>,
                            response: Response<CommonResponse>
                        ) {
                            allfuntion.apply {
                                p.dismiss()
                                response.body()?.let {

                                if(it.message=="success"){
                                    finish()
                                }
                                    toast(it.message)
                                }
                            }
                        }

                        override fun onFailure(call: retrofit2.Call<CommonResponse>, t: Throwable) {
                allfuntion.apply{
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