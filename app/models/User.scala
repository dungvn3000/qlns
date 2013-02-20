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

  val idC = column("id")
  val usernameC = column("username")
  val passwordC = column("password")
  val emailC = column("email")
  val firstNameC = column("firstName")
  val lastNameC = column("lastName")
  val phoneC = column("phone")
  val addressC = column("address")
  val roleC = column("role")
  val dobC = column("dob")

  def login(user: User) = {
    DB.withConnection(implicit c => {
      SQL(s"select * from $table where $usernameC = {username} and $passwordC = {password}")
        .on('username -> user.username, 'password -> user.password).singleOpt().isDefined
    })
  }

  def findByUserName(userName: String) = DB.withConnection(implicit c => {
    SQL(s"select * from $table where $usernameC = {username}").on('username -> userName).singleOpt(user)
  })

  def all(): List[User] = DB.withConnection {
    implicit c =>
      SQL(s"select * from $table").as(user *)
  }

  def findJobNamesByUserName(username: String) = DB.withConnection(implicit c => {
    SQL(s"select ${Job.titleC} from $table " +
      s"inner join ${UserJob.table} on ${UserJob.userIdC} = $idC " +
      s"inner join ${Job.table} on ${Job.idC} = ${UserJob.jobIdC} " +
      s"where $usernameC = {username}").on('username -> username).as(scalar[String] *)
  })

  def update(user: User) {
    DB.withTransaction {
      implicit c => {
        SQL(s"update $table set " +
          s"$usernameC = {username} " +
          s"$passwordC = {password} " +
          s"$emailC = {email} " +
          s"$firstNameC = {firstName} " +
          s"$lastNameC = {lastName} " +
          s"$phoneC = {phone} " +
          s"$addressC = {address} " +
          s"$roleC = {role} " +
          s"$dobC = {dob} " +
          s"where $idC = {id}"
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
        SQL(s"insert into $table (" +
          s"$usernameC, " +
          s"$passwordC," +
          s"$emailC," +
          s"$firstNameC," +
          s"$lastNameC," +
          s"$phoneC," +
          s"$addressC," +
          s"$roleC," +
          s"$dobC" +
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
    get[Long](idC) ~
      get[String](usernameC) ~
      get[String](passwordC) ~
      get[Option[String]](emailC) ~
      get[String](firstNameC) ~
      get[String](lastNameC) ~
      get[String](phoneC) ~
      get[String](addressC) ~
      get[String](roleC) ~
      get[Date](dobC) map {
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