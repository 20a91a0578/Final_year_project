package com.example.woolfabric.Admin.Info

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.woolfabric.AllFunction
import com.example.woolfabric.Responses.CommonResponse
import com.example.woolfabric.Responses.RetroFit
import com.example.woolfabric.databinding.ActivityWoolGrowthBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.*
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class Addinformation : AppCompatActivity() {
    var encoded=""
    private lateinit var bind:ActivityWoolGrowthBinding
    var bitmap:Bitmap?=null
    var uri:Uri?=null
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind=ActivityWoolGrowthBinding.inflate(layoutInflater)
        setContentView(bind.root)
        val function=AllFunction(this)
        val result=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it!=null){
                val data=it.data
                if(data!=null){

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        val soucrce=ImageDecoder.createSource(contentResolver,Uri.parse(data.data.toString()))
                            bitmap=ImageDecoder.decodeBitmap(soucrce)
                    } else {
                    bitmap=MediaStore.Images.Media.getBitmap(contentResolver,Uri.parse(data.data.toString()))
                    }
                    uri=data.data
                    bind.selectfor.setImageBitmap(bitmap)
                    bind.selectfor.scaleType=ImageView.ScaleType.CENTER_CROP
                }
            }
        }
        bind.selectfor.setOnClickListener {
        result.launch(Intent(Intent.ACTION_GET_CONTENT).apply { type="image/*" })
        }
        val type=intent.getStringExtra("type")

        supportActionBar!!.title = type

        bind.upload.setOnClickListener {
            val title=bind.title2.text.toString().trim()
            val description=bind.decri.text.toString().trim()
            val youtube=bind.youtubelink.text.toString().trim()
            if(title.isEmpty()){
                function.toast("Please enter your head line title")
            }else
            if(description.isEmpty()){
                function.toast("please enter your Description")
            }else
            if(youtube.isEmpty()){
                function.toast("Please enter you tube link")
            }else if(uri!=null){

val url=getereal(uri!!)
if(url!=null) {

    function.p.show()


    val dat = SimpleDateFormat("dd/MM/yyyy aaa HH:mm ")
    val file = File(url)
    val requestname=RequestBody.create(MediaType.parse("*/*"),file)
    val serverfile=MultipartBody.Part.createFormData("filename",url,requestname)
    val typ=MultipartBody.create(MediaType.parse("text/plain"),type!!)
    val title2=MultipartBody.create(MediaType.parse("tet/plain"),title)
    val potray=MultipartBody.create(MediaType.parse("text/plain"),description)
    val youtubelink=MultipartBody.create(MediaType.parse("text/plain"),youtube)
    val uploaddate=MultipartBody.create(MediaType.parse("text/plain"),dat.format(Date()))
    CoroutineScope(IO).launch {
        RetroFit.instance.addinfo(file=serverfile,
                type=typ,
                title=title2,
                potray=potray,
                youtubelink=youtubelink,
                uploaddate=uploaddate
        ).enqueue(object :retrofit2.Callback<CommonResponse>{
            override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {
                function.apply {
                    p.dismiss()
                    response.body()?.let {
                        if(it.message=="uploaded"){
                            finish()
                        }
                        toast(it.message)
                    }
                }
            }

            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                function.apply {
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

    private fun getereal(uri: Uri): String? {
var path:String?=null
if(uri.scheme=="content"){
val input=contentResolver.openInputStream(uri)
    if(input!=null){
val file=File(cacheDir,uri.lastPathSegment!!)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            FileUtils.copy(input,file.outputStream())
        }else{
            Toast.makeText(this, "Android Lowest version", Toast.LENGTH_SHORT).show()
        }
        path=file.path
    }
}else{
path=uri.path
}
        return path
    }
}