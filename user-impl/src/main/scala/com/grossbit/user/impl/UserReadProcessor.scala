package com.grossbit.user.impl

import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraSession

import scala.concurrent.ExecutionContext

/**
  * Created by sarathraj on 10/06/17.
  */
class UserReadProcessor(session: CassandraSession)(implicit ec: ExecutionContext) {

   // get user by emailId and password
      // if success ... generate a uuid and save to the db
         // response with the uuid

   // get user by Id

   // get all user in the company


  // get the user with the uuid .. security check

}
