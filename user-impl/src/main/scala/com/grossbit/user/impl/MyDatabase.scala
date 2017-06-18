package com.grossbit.user.impl

import scala.concurrent.Future
import akka.Done
import akka.persistence.query.Offset
import com.grossbit.user.impl.persistance.event.UserEvent
import com.lightbend.lagom.scaladsl.persistence.AggregateEventTag




/**
  * Created by sarathraj on 11/06/17.
  */
trait MyDatabase {
  /**
    * Create the tables needed for this read side if not already created.
    */
  def createTables(): Future[Done]

  /**
    * Load the offset of the last event processed.
    */
  def loadOffset(tag: AggregateEventTag[UserEvent]): Future[Offset]

  /**
    * Handle the post added event.
    */
  def handleEvent(event: UserEvent, offset: Offset): Future[Done]
}