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

  val idC = column("id")
  val titleC = column("title")

  def delete(jobId: Long) = {
    DB.withTransaction {
      implicit c => {
        SQL(s"delete $table where $idC = {id}").on('id -> jobId).executeUpdate()
      }
    }
  }

  def update(job: Job) = {
    DB.withTransaction {
      implicit c => {
        SQL(s"update $table set $titleC = {title} where $idC = {id}").on('title -> job.title, 'id -> job.id).executeUpdate()
      }
    }
  }

  def insert(job: Job) = {
    DB.withTransaction {
      implicit c => {
        SQL(s"insert into $table ($titleC) value ({title})").on('title -> job.title).executeInsert[Option[Long]]()
      }
    }
  }

  def job = get[Long](idC) ~
    get[String](titleC) map {
    case id ~ title => Job(id, title)
  }

}

