package com.example.woolfabric.Responses

import com.example.woolfabric.Responses.Models.Count

data class CountResponse (var error:Boolean,var message:String,var data:ArrayList<Count>)