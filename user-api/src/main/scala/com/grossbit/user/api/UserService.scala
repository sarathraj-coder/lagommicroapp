package com.grossbit.user.api

import java.util.UUID

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}
import play.api.libs.json.{Format, Json}

import scala.concurrent.Future

/**
  * The User service interface.
  * <p>
  * This describes everything that Lagom needs to know about how to serve and
  * consume the UserService.
  */
trait UserService extends Service {

  /**
    * Example: curl http://localhost:9000/api/hello/Alice
    */
  def hello(id: String): ServiceCall[NotUsed, String]

  /**
    * Example: curl -H "Content-Type: application/json" -X POST -d '{"message":
    * "Hi"}' http://localhost:9000/api/hello/Alice
    */
  def useGreeting(id: String): ServiceCall[GreetingMessage, Done]


  def registration:ServiceCall[User,ResponseDone]
  def login:ServiceCall[LoginUser,User]
  def resetPassword:ServiceCall[ResetPassword,ResponseDone]


  def user(id: UUID):ServiceCall[NotUsed,User]
  def users:ServiceCall[NotUsed,List[User]]
  def currentState(id: UUID):ServiceCall[NotUsed,User]
  def newUser:ServiceCall[User,ResponseDone]
  def updateUser:ServiceCall[User,ResponseDone]
  def delete(id: UUID):ServiceCall[NotUsed,ResponseDone]

  override final def descriptor = {
    import Service._
    import com.lightbend.lagom.scaladsl.api.transport.Method
    // @formatter:off
    named("user").withCalls(
//      pathCall("/api/hello/:id", hello _),
//      pathCall("/api/hello/:id", useGreeting _),
//      pathCall("/api/register",registration _),
//      pathCall("/api/login",login _),
//      pathCall("/api/resetpassword", resetPassword _),

      restCall(Method.GET, "/api/users", users _),
      restCall(Method.GET, "/api/user/:id", user _),
      restCall(Method.POST, "/api/user", newUser _),
      restCall(Method.PUT, "/api/user", updateUser _),
      restCall(Method.DELETE, "/api/user/:id", delete _),
      restCall(Method.GET, "/api/user/current-state/:id", currentState _)


    ).withAutoAcl(true)
    // @formatter:on
  }
}

/**
  * The greeting message class.
  */
case class GreetingMessage(message: String)
object GreetingMessage {
  /**
    * Format for converting greeting messages to and from JSON.
    *
    * This will be picked up by a Lagom implicit conversion from Play's JSON format to Lagom's message serializer.
    */
  implicit val format: Format[GreetingMessage] = Json.format[GreetingMessage]
}




