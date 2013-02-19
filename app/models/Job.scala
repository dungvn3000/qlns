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

object Job {

  val tableName = "job"
  val idCol = "id"
  val titleCol = "title"

  def delete(id: Long) = {
    DB.withTransaction {
      implicit c => {
        SQL(s"delete $tableName where $id = {id}").on('id -> id).executeUpdate()
      }
    }
  }

  def update(job: Job) = {
    DB.withTransaction {
      implicit c => {
        SQL(s"update $tableName set $titleCol = {title} where $idCol = {id}").on('title -> job.title, 'id -> job.id).executeUpdate()
      }
    }
  }

  def insert(job: Job) = {
    DB.withTransaction {
      implicit c => {
        SQL(s"insert into $tableName ($titleCol) value ({title})").on('title -> job.title).executeInsert[Option[Long]]()
      }
    }
  }

  def job = get[Long](idCol) ~
    get[String](titleCol) map {
    case id ~ title => Job(id, title)
  }

}

