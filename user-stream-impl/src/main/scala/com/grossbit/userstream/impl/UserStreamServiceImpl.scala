package com.grossbit.userstream.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.grossbit.userstream.api.UserStreamService
import com.grossbit.user.api.UserService

import scala.concurrent.Future

/**
  * Implementation of the UserStreamService.
  */
class UserStreamServiceImpl(userService: UserService) extends UserStreamService {
  def stream = ServiceCall { hellos =>
    Future.successful(hellos.mapAsync(8)(userService.hello(_).invoke()))
  }
}
