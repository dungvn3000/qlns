package models

import org.joda.time.DateTime

/**
 * The Class UserJob.
 *
 * @author Nguyen Duc Dung
 * @since 2/19/13, 12:07 PM
 *
 */
case class UserJob(userId: Long, jobId: Long, start: DateTime, end: Option[DateTime] = None)