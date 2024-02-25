package com.example.woolfabric.Responses
import com.example.woolfabric.Responses.Models.Customdata

data class CustomeResponse2 (
    var error:Boolean,
    var message:String,
    var data:ArrayList<Customdata>
)