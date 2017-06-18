package com.grossbit.user.impl

import java.util.UUID

import akka.actor.ActorSystem
import akka.persistence.cassandra.session.scaladsl.CassandraSession
import akka.stream.Materializer
import akka.{Done, NotUsed}
import com.datastax.driver.core.utils.UUIDs
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.{PersistentEntityRegistry, ReadSide}
import com.grossbit.user.api._
import com.grossbit.user.impl.persistance.Command.{CreateUser, GetUser, UpdateUser}

import scala.concurrent.{ExecutionContext, Future}

/**
  * Implementation of the UserService.
  */
class UserServiceImpl(registry: PersistentEntityRegistry, system: ActorSystem )(implicit ec: ExecutionContext, mat: Materializer)extends UserService {


  override def login: ServiceCall[LoginUser, User] = ???

  override def resetPassword: ServiceCall[ResetPassword, ResponseDone] = ???





  /**
    * Example: curl http://localhost:9000/api/hello/Alice
    */
  override def hello(id: String): ServiceCall[NotUsed, String] = ???

  /**
    * Example: curl -H "Content-Type: application/json" -X POST -d '{"message":
    * "Hi"}' http://localhost:9000/api/hello/Alice
    */
  override def useGreeting(id: String): ServiceCall[GreetingMessage, Done] = ???

  override def registration: ServiceCall[User, ResponseDone] = ???


//  override def user(id: UUID): ServiceCall[NotUsed, Future[User]] = ServiceCall {
//    entityRef(id).ask(GetUser).map( data =>
//      if(data.isEmpty){
//        Future.successful(User(None,None,None))
//      }else{
//
//      }
//      User(data.get.name,data.get.email,data.get.password))
//  }

  override def user(id: UUID): ServiceCall[NotUsed, User] = ServiceCall { x =>
    entityRef(id).ask(GetUser).map( user =>
      if(user.isEmpty) User(Some(""),"","","")
      else User(Some(user.get.uuid),user.get.name,user.get.email,user.get.password)
    )
  }

  override def currentState(id: UUID): ServiceCall[NotUsed, User] = ???

  override def newUser: ServiceCall[User, ResponseDone] = ServiceCall { user: User =>
    //TODO  check user with this emailId in the org
    val userId = UUIDs.timeBased()
    println(userId)
    //newEntityRef.ask(CreateUser(user.name, user.email, user.password))
    entityRef(userId).ask(CreateUser(user.name, user.email, user.password)).map { data =>
      data
    }

  }

  override def updateUser: ServiceCall[User, ResponseDone] = ServiceCall {user: User =>
     if(user.uuid.isEmpty)
     Future.successful(ResponseDone("success" , true, 200))
     else {
       //TODO check the user is there with the id
       entityRef(UUID.fromString(user.uuid.get)).ask(UpdateUser(user.uuid.get, user.name, user.email, user.password)).map { data =>
         data
       }
     }
  }


  // This function is not required
  override def delete(id: UUID): ServiceCall[NotUsed, ResponseDone] = ???

//  override def users: ServiceCall[NotUsed, List[User]] = ServiceCall { request =>
//    val userFinal: List[User] = List()
//    session.select("SELECT * FROM user").map {
//      row => {
//        userFinal :+ User(row.getString("uuid"), row.getString("name"), row.getString("email"), row.getString("password"))
//      }
//    }
//    Future.successful(userFinal)
//  }




  //private def refFor(userId: UUID) = registry.refFor[UserEntity](userId.toString)

  /**
    * Helper function to seed a new entity reference, uses a new UUID.
    */
  private def newEntityRef = registry.refFor[UserEntity](UUIDs.timeBased().toString)

  /**
    * Helper function to look up entity ref from UserId in UUID format.
    */
  private def entityRef(id: UUID) = registry.refFor[UserEntity](id.toString)

  override def users: ServiceCall[NotUsed, List[User]] = ???


}

