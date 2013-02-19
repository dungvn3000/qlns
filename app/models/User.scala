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
                 id: Long = -1,
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

object User extends TableSchema("user") {

  val id = column("id")
  val username = column("username")
  val password = column("password")
  val email = column("email")
  val firstName = column("firstName")
  val lastName = column("lastName")
  val phone = column("phone")
  val address = column("address")
  val role = column("role")
  val dob = column("dob")

  def findByUserName(userName: String) = DB.withConnection(implicit c => {
    SQL(s"select * from $tableName where $username = {username}").on('username -> userName).singleOpt(user)
  })

  def all(): List[User] = DB.withConnection {
    implicit c =>
      SQL(s"select * from $tableName").as(user *)
  }

  def findJobNamesByUserName(uName: String) = DB.withConnection(implicit c => {
    SQL(s"select ${Job.title} from $tableName " +
      s"inner join ${UserJob.tableName} on ${UserJob.userId} = $id " +
      s"inner join ${Job.tableName} on ${Job.id} = ${UserJob.jobId} " +
      s"where $username = {username}").on('username -> uName).as(scalar[String] *)
  })

  def update(user: User) {
    DB.withTransaction {
      implicit c => {
        SQL(s"update $tableName set " +
          s"$username = {username} " +
          s"$password = {password} " +
          s"$email = {email} " +
          s"$firstName = {firstName} " +
          s"$lastName = {lastName} " +
          s"$phone = {phone} " +
          s"$address = {address} " +
          s"$role = {role} " +
          s"$dob = {dob} " +
          s"where $id = {id}"
        ).on(
          'id -> user.id,
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

  def insert(user: User) = {
    DB.withTransaction {
      implicit c =>
        SQL(s"insert into $tableName (" +
          s"$username, " +
          s"$password," +
          s"$email," +
          s"$firstName," +
          s"$lastName," +
          s"$phone," +
          s"$address," +
          s"$role," +
          s"$dob" +
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
        ).executeInsert[Option[Long]]()
    }
  }

  val user = {
    get[Long](id) ~
      get[String](username) ~
      get[String](password) ~
      get[Option[String]](email) ~
      get[String](firstName) ~
      get[String](lastName) ~
      get[String](phone) ~
      get[String](address) ~
      get[String](role) ~
      get[Date](dob) map {
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