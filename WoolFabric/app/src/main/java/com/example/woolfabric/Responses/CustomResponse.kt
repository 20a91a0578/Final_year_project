package com.example.woolfabric.Responses

import com.example.woolfabric.Responses.Models.Product


data class CustomResponse (var error:Boolean, var message:String, var data:ArrayList<Product>)