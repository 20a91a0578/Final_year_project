package com.example.woolfabric.Industry

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS
import androidx.core.view.isVisible
import com.example.woolfabric.AllFunction
import com.example.woolfabric.R
import com.example.woolfabric.Responses.CommonResponse
import com.example.woolfabric.Responses.LoginResponse
import com.example.woolfabric.Responses.Models.Requests
import com.example.woolfabric.Responses.RetroFit
import com.example.woolfabric.databinding.ActivityViewDetails2Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewDetails : AppCompatActivity() {
    private lateinit var allFunction: AllFunction
    lateinit var bind:ActivityViewDetails2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivityViewDetails2Binding.inflate(layoutInflater)
        setContentView(bind.root)
        allFunction= AllFunction(this)
        val ff=findViewById<Button>(R.id.update)

    intent.getParcelableExtra<Requests>("data")?.apply {
        ff.setOnClickListener {
            function(id)
        }
val change=intent.getStringExtra("type")
        val type=getSharedPreferences("user", MODE_PRIVATE).getString("type","")
        var typo=""
        typo = if(type=="Farmer"){
            fromid
        }else{
            toid
        }
        ff.isVisible = change=="change"

        allFunction.p.show()
        CoroutineScope(IO).launch {
            RetroFit.instance.getuserid(
                "getuser",
               id= typo
            ).enqueue(object :Callback<LoginResponse>{
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    allFunction.apply {
                        p.dismiss()
                        response.body()?.apply {
                            if(data.isNotEmpty()) {
                                val k=data[0]
                                val text = if (type == "Farmer") {
                                    "<b>Industry name : ${k.name}</b><br>" +
                                            "<b>Industry mobile : ${k.mobile}</b><br>" +
                                            "<b>Industry Mail : ${k.mail}</b><br>" +
                                            "<b>Industry Address : ${k.count}</b>"
                                } else {
                                        "<b>Farmer name : ${k.name}</b><br>" +
                                            "<b>Farmer mobile : ${k.mobile}</b><br>" +
                                            "<b>Farmer Mail : ${k.mail}</b><br>" +
                                            "<b>Farmer Sheep : ${k.count}</b>"
                                }
                                bind.deatils23.text=HtmlCompat.fromHtml(text,FROM_HTML_OPTION_USE_CSS_COLORS)
                                bind.callr.setOnClickListener {
                                    startActivity(Intent(Intent.ACTION_DIAL,Uri.parse("tel:${k.mobile}")))
                                }

                            }
                        }
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                allFunction.apply {
                p.dismiss()
                    toast(t.message!!)
                }
                }
            })
        }
    }
    }

    private fun function(id: String) {

AlertDialog.Builder(this).apply {
    setTitle("Do you want to Change the Status of the requests")
    setMessage("Press 'complete' to Completed the requests or Press 'close' to complete")
    setPositiveButton("complete"){d,_->
        d.dismiss()
       realfiunction(id)
    }
    setNegativeButton("No"){d,_->
        d.dismiss()
    }
    show()
}
    }

    private fun realfiunction(id: String) {
        allFunction.p.show()
        CoroutineScope(IO).launch {
            RetroFit.instance.updatefun(condition = "updaterequest",id=id).enqueue(object :Callback<CommonResponse>{
                override fun onResponse(
                    call: Call<CommonResponse>,
                    response: Response<CommonResponse>
                ) {
                    allFunction.apply {
                        response.body()?.apply {
                            if(message=="success"){
                                finish()
                            }
                        }
                        if(response.body()==null){
                            toast(response.body()!!)
                        }
                        p.show()
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