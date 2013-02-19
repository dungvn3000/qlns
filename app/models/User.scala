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
                 id: Long,
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

  val tableName = "user"
  val idCol = "id"
  val usernameCol = "username"
  val passwordCol = "password"
  val emailCol = "email"
  val firstNameCol = "firstName"
  val lastNameCol = "lastName"
  val phoneCol = "phone"
  val addressCol = "address"
  val roleCol = "role"
  val dobCol = "dob"

  def findByUserName(userName: String) = DB.withConnection(implicit c => {
    SQL(s"select * from $tableName where $usernameCol = {username}").on('username -> userName).singleOpt(user)
  })

  def all(): List[User] = DB.withConnection {
    implicit c =>
      SQL(s"select * from $tableName").as(user *)
  }

  def update(user: User) {
    DB.withTransaction {
      implicit c => {
        SQL(s"update $tableName set " +
          s"$usernameCol = {username}" +
          s"$passwordCol = {password}" +
          s"$emailCol = {email}" +
          s"$firstNameCol = {firstName}" +
          s"$lastNameCol = {lastName}" +
          s"$phoneCol = {phone}" +
          s"$addressCol = {address}" +
          s"$roleCol = {role}" +
          s"$dobCol = {dob}"
        ).on(
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
  }

  def insert(user: User) {
    DB.withTransaction {
      implicit c =>
        SQL(s"insert into $tableName (" +
          s"$usernameCol, " +
          s"$passwordCol," +
          s"$emailCol," +
          s"$firstNameCol," +
          s"$lastNameCol," +
          s"$phoneCol," +
          s"$addressCol," +
          s"$roleCol," +
          s"$dobCol" +
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
        ).executeInsert()
    }
  }

  val user = {
    get[Long](idCol) ~
      get[String](usernameCol) ~
      get[String](passwordCol) ~
      get[Option[String]](emailCol) ~
      get[String](firstNameCol) ~
      get[String](lastNameCol) ~
      get[String](phoneCol) ~
      get[String](addressCol) ~
      get[String](roleCol) ~
      get[Date](dobCol) map {
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