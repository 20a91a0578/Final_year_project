package com.example.woolfabric.Responses

import com.example.woolfabric.Responses.Models.User
import java.lang.Error

data class LoginResponse (var error: Boolean,var message:String,var data:ArrayList<User>)