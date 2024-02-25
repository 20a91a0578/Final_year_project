package com.example.woolfabric.Responses

import com.example.woolfabric.Responses.Models.Orders
import java.lang.Error

data class CustomReponse(var error:Boolean,var message:String,var data:ArrayList<Orders>)