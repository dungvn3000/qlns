package models

/**
 * The Class Schema.
 *
 * @author Nguyen Duc Dung
 * @since 2/19/13, 6:29 PM
 *
 */
abstract class TableSchema(val tableName: String) {
  def column(name: String) = s"$tableName.$name"
}