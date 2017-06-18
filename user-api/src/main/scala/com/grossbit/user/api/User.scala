package com.grossbit.user.api

import java.util.UUID

import play.api.libs.json.{Format, Json}

/**
  * Created by sarathraj on 04/05/17.
  */

case class User(uuid:Option[String],name:String,email:String,password:String)
//case class User(uuid:Option[UUID],name:String,email:String,password:String,userId:Option[String],fistName:Option[String],lastName:Option[String],
//                dob:Option[Long],doj:Option[Long],language:Option[String],organisation: Option[List[Organisation]],roles:Option[List[UserRole]],isAdmin:Option[Boolean]
//                ,isEnabled:Option[Boolean],isSuperAdmin:Option[Boolean])
case class Organisation(name:String,location:String,address:String,street:String,pinCode:Int)
case class UserRole(name:String,title:String,level:Double)
case class ResponseDone(message:String,status:Boolean,code:Int)
case class LoginUser(email:String,userId:String,password:String)
case class ResetPassword(email:String,userId:String,password:String)

object User{
  implicit val format: Format[User] = Json.format[User]
}
object Organisation{
  implicit val format: Format[Organisation] = Json.format[Organisation]
}
object UserRole{
  implicit val format: Format[UserRole] = Json.format[UserRole]
}
object ResponseDone{
  implicit val format: Format[ResponseDone] = Json.format[ResponseDone]
}
object LoginUser{
  implicit val format: Format[LoginUser] = Json.format[LoginUser]
}
object ResetPassword{
  implicit val format: Format[ResetPassword] = Json.format[ResetPassword]
}
