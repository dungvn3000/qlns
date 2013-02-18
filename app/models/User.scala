package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import org.joda.time.DateTime
import java.util.Date

/**
 * The Class User.
 *
 * @author Nguyen Duc Dung
 * @since 2/18/13, 4:32 PM
 *
 */
case class User(
                 id: Int = 0,
                 username: String,
                 password: String,
                 email: Option[String] = None,
                 firstName: String,
                 lastName: String,
                 phone: String,
                 address: String,
                 role: String,
                 dob: DateTime
                 )

object User {

  def all(): List[User] = DB.withConnection {
    implicit c =>
      SQL("select * from user").as(user *)
  }

  def insert(user: User) {
    DB.withTransaction {
      implicit c =>
        SQL("insert into user (" +
          "username, " +
          "password," +
          "email," +
          "firstName," +
          "lastName," +
          "phone," +
          "address," +
          "role," +
          "dob" +
          ") values (" +
          "{username}, " +
          "{password}," +
          "{email}," +
          "{firstName}," +
          "{lastName}," +
          "{phone}," +
          "{address}," +
          "{role}," +
          "{dob}" +
          ")").on(
          'username -> user.username,
          'password -> user.password,
          'email -> user.email.getOrElse(""),
          'firstName -> user.firstName,
          'lastName -> user.lastName,
          'phone -> user.phone,
          'address -> user.address,
          'role -> user.role,
          'dob -> user.dob.toDate
        ).executeUpdate()
    }
  }

  val user = {
    get[Int]("id") ~
      get[String]("username") ~
      get[String]("password") ~
      get[Option[String]]("email") ~
      get[String]("firstName") ~
      get[String]("lastName") ~
      get[String]("phone") ~
      get[String]("address") ~
      get[String]("role") ~
      get[Date]("dob") map {
      case id ~
        username ~
        password ~
        email ~
        firstName ~
        lastName ~
        phone ~
        address ~
        role ~
        dob => User(
        id = id,
        username = username,
        password = password,
        email = email,
        firstName = firstName,
        lastName = lastName,
        phone = phone,
        address = address,
        role = role,
        dob = new DateTime(dob)
      )
    }
  }
}