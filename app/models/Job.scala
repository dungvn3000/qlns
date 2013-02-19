package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

/**
 * The Class Job.
 *
 * @author Nguyen Duc Dung
 * @since 2/19/13, 9:21 AM
 *
 */
case class Job(id: Long = -1, title: String)

object Job extends TableSchema("job") {

  val id = column("id")
  val title = column("title")

  def delete(jobId: Long) = {
    DB.withTransaction {
      implicit c => {
        SQL(s"delete $tableName where $id = {id}").on('id -> jobId).executeUpdate()
      }
    }
  }

  def update(job: Job) = {
    DB.withTransaction {
      implicit c => {
        SQL(s"update $tableName set $title = {title} where $id = {id}").on('title -> job.title, 'id -> job.id).executeUpdate()
      }
    }
  }

  def insert(job: Job) = {
    DB.withTransaction {
      implicit c => {
        SQL(s"insert into $tableName ($title) value ({title})").on('title -> job.title).executeInsert[Option[Long]]()
      }
    }
  }

  def job = get[Long](id) ~
    get[String](title) map {
    case id ~ title => Job(id, title)
  }

}

