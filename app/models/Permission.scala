package models

/**
 * The Class Permission.
 *
 * @author Nguyen Duc Dung
 * @since 11/6/12 10:13 AM
 *
 */
sealed trait Permission

case object Administrator extends Permission

case object NormalUser extends Permission