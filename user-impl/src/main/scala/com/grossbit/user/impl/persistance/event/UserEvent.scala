package com.grossbit.user.impl.persistance.event

import java.util.UUID

import com.grossbit.user.api.ResponseDone
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag, AggregateEventTagger}
import play.api.libs.json.{Format, Json}

/**
  * Created by sarathraj on 24/05/17.
  */
sealed trait UserEvent extends  AggregateEvent[UserEvent]{
  override def aggregateTag: AggregateEventTagger[UserEvent] = UserEvent.UserEventTag

}
object UserEvent{
  val NumShards=4
  val UserEventTag = AggregateEventTag.sharded[UserEvent](NumShards)
}

case class UserCreated(uuid:String,name: String, email: String, password: String) extends UserEvent
case class UserUpdated(uuid:String,name: String, email: String, password: String) extends UserEvent
case class UserDeleted(name: String) extends UserEvent

object UserCreated {
  implicit val format: Format[UserCreated] = Json.format
}
object UserUpdated {
  implicit val format: Format[UserUpdated] = Json.format
}
object UserDeleted {
  implicit val format: Format[UserDeleted] = Json.format
}
