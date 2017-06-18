package com.grossbit.user.impl.persistance.state

import java.util.UUID

import com.grossbit.user.api.User
import play.api.libs.json.{Format, Json}

/**
  * Created by sarathraj on 24/05/17.
  */

case class UserState(uuid:String,name:String,email:String,password:String)

object UserState {
  /**
    * Format for the user state.
    *
    * Persisted entities get snapshotted every configured number of events. This
    * means the state gets stored to the database, so that when the entity gets
    * loaded, you don't need to replay all the events, just the ones since the
    * snapshot. Hence, a JSON format needs to be declared so that it can be
    * serialized and deserialized when storing to and from the database.
    */

  implicit val format: Format[UserState] = Json.format
}