package com.example.woolfabric.Farmer.Functions

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.woolfabric.AllFunction
import com.example.woolfabric.Responses.CommonResponse
import com.example.woolfabric.Responses.RetroFit
import com.example.woolfabric.databinding.ActivityAdditemsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date

class Additems : AppCompatActivity() {
    var imagemap:Bitmap?=null
    lateinit var allFunction:AllFunction
    private lateinit var bind:ActivityAdditemsBinding
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
bind=ActivityAdditemsBinding.inflate(layoutInflater)
        setContentView(bind.root)
        allFunction= AllFunction(this)
        val laun=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val data=it.data

            if(data!=null){
                imagemap=data.extras?.get("data")as Bitmap
                    val image=bind.shapeableImageView
        image.setImageBitmap(imagemap)
     image.scaleType=ImageView.ScaleType.CENTER_CROP
            }
        }
        bind.capture2.setOnClickListener{
            laun.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }
        val productname= bind.productname
        val basicqty= bind.basicqty
        val rupees= bind.rupees

        productname.setOnFocusChangeListener { _,b->
            refreshed(productname,b)
        }
        basicqty.setOnFocusChangeListener { _,b->
            refreshed(basicqty,b)
        }
        rupees.setOnFocusChangeListener { _, b ->
            refreshed(rupees,b)
        }

        bind.addit.setOnClickListener {
            val pname=productname.text.toString().trim()
            val qty=basicqty.text.toString().trim()
            val rupee=rupees.text.toString().trim()
            if(pname.isEmpty()){
               allFunction.toast("Please enter Product name")
            }else
            if(qty.isEmpty()){
                allFunction.toast("Please enter basic Quantity")
            }else
            if(rupee.isEmpty()){
                allFunction.toast("Please enter cost of the product")
            }else if(imagemap==null){
                allFunction.toast("Please Pick a picture from your camera")
            } else{
                val id=getSharedPreferences("user", MODE_PRIVATE).getString("id","")
                val simple=SimpleDateFormat("dd/mm/yyy")
                val out=ByteArrayOutputStream()
                imagemap?.compress(Bitmap.CompressFormat.PNG,100,out)
                val encoded=android.util.Base64.encodeToString(out.toByteArray(),android.util.Base64.NO_WRAP)
                allFunction.p.show()
                CoroutineScope(IO).launch {
                    RetroFit.instance.addproduct(
                    pname=pname,
                    farmerid="$id",
                    updatedate= simple.format(Date()),
                    cost=rupee,
                    leftqty=qty,
                    image=encoded).enqueue(object :Callback<CommonResponse>{
                        override fun onResponse(
                            call: Call<CommonResponse>,
                            response: Response<CommonResponse>
                        ) {
                            response.body()?.let {
                                allFunction.apply {
                                if(it.message=="success"){
                                    finish()
                                }
                                    p.dismiss()
                                    toast(it.message)
                                }
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



    private fun refreshed(editText: EditText, b: Boolean) {

if(b){
    editText.compoundDrawables[0].setTint(Color.WHITE)
}else{
    editText.compoundDrawables[0].setTint(Color.BLACK)
}


    }







}