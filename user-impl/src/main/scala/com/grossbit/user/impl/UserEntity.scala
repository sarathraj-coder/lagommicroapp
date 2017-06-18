package com.grossbit.user.impl

import java.time.LocalDateTime
import java.util.UUID

import akka.Done
import com.grossbit.user.api._
import com.grossbit.user.impl.persistance.Command.{CreateUser, GetUser, UpdateUser, UserCommand}
import com.grossbit.user.impl.persistance.event.{UserCreated, UserEvent, UserUpdated}
import com.grossbit.user.impl.persistance.state.UserState
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity
import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import play.api.libs.json.{Format, Json}

import scala.collection.immutable.Seq

/**
  * This is an event sourced entity. It has a state, [[UserState]], which
  * stores what the greeting should be (eg, "Hello").
  *
  * Event sourced entities are interacted with by sending them commands. This
  * entity supports two commands, a [[UseGreetingMessage]] command, which is
  * used to change the greeting, and a [[Hello]] command, which is a read
  * only command which returns a greeting to the name specified by the command.
  *
  * Commands get translated to events, and it's the events that get persisted by
  * the entity. Each event will have an event handler registered for it, and an
  * event handler simply applies an event to the current state. This will be done
  * when the event is first created, and it will also be done when the entity is
  * loaded from the database - each event will be replayed to recreate the state
  * of the entity.
  *
  * This entity defines one event, the [[GreetingMessageChanged]] event,
  * which is emitted when a [[UseGreetingMessage]] command is received.
  */
class UserEntity extends PersistentEntity {

  override type Command = UserCommand
  override type Event = UserEvent
  override type State = Option[UserState]

  /**
    * The initial state. This is used if there is no snapshotted state to be found.
    */

  override def initialState = None

  /**
    * An entity can define different behaviours for different states, so the behaviour
    * is a function of the current state to a set of actions.
    */
  override def behavior: Behavior = {
    case None => initial
    case Some(user) => personAdded
  }

  // this the case where the entitiy is empty
  private val initial: Actions = {
    Actions()
      .onCommand[CreateUser, ResponseDone] {
      case (CreateUser(name, email, password), ctx, state) =>
        ctx.thenPersist(UserCreated(entityId,name, email, password)) (
          _  => ctx.reply(ResponseDone("success" + entityId, true, 200)))
    }
      .onEvent {
        case (UserCreated(uuid,name, email, password), state) =>
          Some(UserState(uuid,name, email, password))
      }

      .onReadOnlyCommand[GetUser.type , Option[UserState]] {
      case (GetUser, ctx, state) => ctx.reply(state)
    }
  }


  private val personAdded: Actions = {
    Actions()
      .onReadOnlyCommand[GetUser.type , Option[UserState]] {
      case (GetUser, ctx, state) => ctx.reply(state)
    }
//      .onReadOnlyCommand[CreateUser, ResponseDone] {
//      case (CreateUser(name, email, password), ctx, state) =>
//        ctx.invalidCommand("User already exists")
//    }

      .onCommand[UpdateUser, ResponseDone] {
      case (UpdateUser(uuid,name, email, password), ctx, state) =>
        ctx.thenPersist(UserUpdated(uuid,name, email, password))(_ =>
          ctx.reply(ResponseDone("success", true, 200)))
    }

      .onEvent {
        case (UserUpdated(uuid,name, email, password), state) =>
          Some(UserState(uuid,name, email, password))
      }

  }

}
