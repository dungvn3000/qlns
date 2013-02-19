package models

import org.joda.time.DateTime

/**
 * The Class User.
 *
 * @author Nguyen Duc Dung
 * @since 2/18/13, 4:32 PM
 *
 */
case class User(
                 username: String,
                 password: String,
                 email: Option[String] = None,
                 firstName: String,
                 lastName: String,
                 phone: String,
                 address: String,
                 role: String,
                 dob: DateTime,
                 userJobs: List[UserJob] = Nil
                 )