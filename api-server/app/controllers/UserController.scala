package controllers

import play.api._
import play.api.mvc._
import play.api.i18n._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
import scala.util.{Success, Failure}
import models._
import dal._

import scala.concurrent.{ ExecutionContext, Future }

import javax.inject._

class UserController @Inject() (repo: UserRepository, val messagesApi: MessagesApi)
                                 (implicit ec: ExecutionContext) extends Controller with I18nSupport{

  /**
   * A REST endpoint that gets all the users as JSON.
   */
  def getUsers = Action.async {
  	repo.list().map { users =>
      Ok(Json.toJson(users))
    }
  }

  /**
   * A REST endpoint that gets the user as JSON.
   */
  def getUser(id: Long) = Action.async {
  	repo.findById(id).map { user =>
      user match {
        case None => NotFound("user not found")
        case _: Option[User] => Ok(Json.toJson(user))
      }
    }
  }

  /**
   * A REST endpoint that create a user with firstName, lastName, and age.
   */
  def createUser = Action.async(parse.json) { implicit request =>
    parseUserResource(request, (params: UserResource) => {
      val firstName = params.firstName
      val lastName = params.lastName
      val age = params.age
      repo.create(firstName, lastName, age).map { newUser => 
        Created(Json.toJson(newUser))
      }
    })
  }


  def parseUserResource(
    request: Request[JsValue],
    block: (UserResource) => Future[Result]
  ): Future[Result] = {
    request.body.validate[UserResource].fold(
      valid = block,
      invalid = (e => {
        val error = JsError.toJson(e)
        Future.successful(BadRequest(error))
      })
    )
  }



}


case class UserResource(firstName: String, lastName: String, age: Int)

object UserResource {

  implicit val reads: Reads[UserResource] = (
    (JsPath \ "firstName").read[String](minLength[String](2)) and
    (JsPath \ "lastName").read[String](minLength[String](2)) and
    (JsPath \ "age").read[Int](min(0) keepAnd max(150)) 
  )(UserResource.apply _)
}