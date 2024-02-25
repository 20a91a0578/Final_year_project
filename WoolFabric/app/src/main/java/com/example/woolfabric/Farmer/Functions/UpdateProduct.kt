package com.example.woolfabric.Farmer.Functions

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import com.example.woolfabric.AllFunction
import com.example.woolfabric.Responses.CommonResponse
import com.example.woolfabric.Responses.Models.Product
import com.example.woolfabric.Responses.RetroFit
import com.example.woolfabric.databinding.ActivityUpdateProductBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date

class UpdateProduct : AppCompatActivity() {
    lateinit var allFunction: AllFunction
    private lateinit var bind:ActivityUpdateProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind=ActivityUpdateProductBinding.inflate(layoutInflater)
        setContentView(bind.root)
        allFunction= AllFunction(this)
        intent.getParcelableExtra<Product>("data")?.let {
            with(it){
                bind.detailimage2.load(image)
                bind.pname.setText(pname)





            bind.cost.setText(cost)
                bind.leftqty.setText(leftqty)




                bind.updatebtn.setOnClickListener {

                    val cost=bind.cost.text.toString().trim()
                    val leftqty=bind.leftqty.text.toString().trim()
                    allFunction.apply {

                        if(cost.isEmpty()){
toast("Please Enter the cost ")
                        }else if(leftqty.isEmpty()){
toast("Please Enter left quantity")
                        }   else{
                            udpdate(cost = cost, lefqty = leftqty,id=id)
                        p.show()
                        }
                    }


                    }

            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun udpdate(cost:String,lefqty:String,id:String) {
        val date=SimpleDateFormat("dd/mm/yyyy")
        val farmerid=getSharedPreferences("user", MODE_PRIVATE).getString("id","")!!
        CoroutineScope(IO).launch {
            RetroFit.instance.updateproduct(
                condition = "update",
                farmerid=farmerid,
                date.format(Date()),
                cost=cost,
                leftqty =   lefqty,
                id=id
            ).enqueue(object :Callback<CommonResponse>{
                override fun onResponse(
                    call: Call<CommonResponse>,
                    response: Response<CommonResponse>
                ) {
                    allFunction.apply {
                        p.dismiss()
                        response.body()?.let {
                            toast(it.message)
                            if(it.message=="updated") {
                                finish()
                            }
                        }
                        if(response.body()==null){
                            toast("something Went Wrong")
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