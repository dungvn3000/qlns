package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import org.joda.time.DateTime
import java.util.Date

/**
 * The Class UserJob.
 *
 * @author Nguyen Duc Dung
 * @since 2/19/13, 12:07 PM
 *
 */
case class UserJob(userId: Long, jobId: Long, start: DateTime, end: Option[DateTime] = None)

object UserJob extends TableSchema("user_job"){

  val userId = column("userId")
  val jobId = column("jobId")
  val start = column("start")
  val end = column("end")

  def findByUserId(userId: Long) = {
    DB.withConnection(implicit c => {
      SQL(s"select * from $tableName where $userId = {userId}").on('userId -> userId).as(userJob *)
    })
  }

  def insert(userJob: UserJob) = {
    DB.withTransaction(implicit c => {
      SQL(s"insert into $tableName ($userId, $jobId, $start, $end) values ({userId}, {jobId}, {start}, {end})").on(
        'userId -> userJob.userId,
        'jobId -> userJob.jobId,
        'start -> userJob.start.toDate,
        'end -> userJob.end.map(_.toDate)
      ).executeInsert[Option[Long]]()
    })
  }

  val userJob = get[Long](userId) ~
    get[Long](jobId) ~
    get[Date](start) ~
    get[Option[Date]](end) map {
    case userId ~ jobId ~ start ~ end => UserJob(userId, jobId, new DateTime(start), end = end.map(new DateTime(_)))
  }

}