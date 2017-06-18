package com.grossbit.user.impl

import com.grossbit.user.impl.persistance.event.UserEvent
import com.lightbend.lagom.scaladsl.persistence.ReadSideProcessor.ReadSideHandler
import com.lightbend.lagom.scaladsl.persistence.{AggregateEventTag, ReadSideProcessor}

/**
  * Created by sarathraj on 11/06/17.
  */
class UserEventProcessor extends  ReadSideProcessor[UserEvent]{

  override def buildHandler(): ReadSideHandler[UserEvent] = ???

  override def aggregateTags: Set[AggregateEventTag[UserEvent]] = UserEvent.UserEventTag.allTags
}
