package com.grossbit.user.impl.persistance.Command

import java.util.UUID

import com.grossbit.user.api.{ResponseDone, User}
import com.grossbit.user.impl.persistance.state.UserState
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import play.api.libs.json.{Format, Json}
import com.grossbit.user.impl.util.JsonFormats

/**
  * Created by sarathraj on 23/05/17.
  */
sealed trait UserCommand



case class CreateUser(name: String, email: String, password: String) extends UserCommand with ReplyType[ResponseDone]
case class UpdateUser(uuid:String,name: String, email: String, password: String) extends UserCommand with ReplyType[ResponseDone]
case class DeleteUser(uuid:String,name: String) extends UserCommand with ReplyType[ResponseDone]

object UpdateUser {
  implicit val format: Format[UpdateUser] = Json.format
}
object CreateUser {
  implicit val format: Format[CreateUser] = Json.format
}
object DeleteUser {
  implicit val format: Format[UpdateUser] = Json.format
}

case object GetUser extends UserCommand with ReplyType[Option[UserState]] {
  implicit val format: Format[GetUser.type] = JsonFormats.singletonFormat(GetUser)
}
