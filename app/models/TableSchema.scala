package models

/**
 * The Class Schema.
 *
 * @author Nguyen Duc Dung
 * @since 2/19/13, 6:29 PM
 *
 */
abstract class TableSchema(val table: String) {
  def column(name: String) = s"$table.$name"
}