package com.example.woolfabric.Responses

import com.example.woolfabric.Responses.Models.ForUser

data class UsersOrders (var error:Boolean,var message:String,var data:ArrayList<ForUser>)