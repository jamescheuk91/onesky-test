package models

import play.api.libs.json._

case class User(id: Long, firstName: String, lastName: String, age: Int)

object User {
  implicit val personFormat = Json.format[User]
}
