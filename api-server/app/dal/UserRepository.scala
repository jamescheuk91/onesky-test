package dal

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import models.User

import scala.concurrent.{ Future, ExecutionContext }

/**
 * A repository for users.
 *
 * @param dbConfigProvider The Play db config provider. Play will inject this for you.
 */
@Singleton
class UserRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import driver.api._

  /**
   * Here we define the table. It will have a name of users
   */
  private class UsersTable(tag: Tag) extends Table[User](tag, "users") {

    /** The ID column, which is the primary key, and auto incremented */
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    /** The firstName column */
    def firstName = column[String]("first_name")

    /** The lastName column */
    def lastName = column[String]("last_name")

    /** The age column */
    def age = column[Int]("age")

    /**
     * This is the tables default "projection".
     *
     * It defines how the columns are converted to and from the User object.
     *
     * In this case, we are simply passing the id, name and page parameters to the User case classes
     * apply and unapply methods.
     */
    def * = (id, firstName, lastName, age) <> ((User.apply _).tupled, User.unapply)
  }

  /**
   * The starting point for all queries on the users table.
   */
  private val users = TableQuery[UsersTable]

  /**
    * List all the users in the database.
    */
  def list(): Future[Seq[User]] = db.run {
    users.result
  }

  /**
   * Create a user with the given firstName, lastName and age.
   *
   * This is an asynchronous operation, it will return a future of the created user, which can be used to obtain the
   * id for that user.
   */
  def create(firstName: String, lastName: String, age: Int): Future[User] = db.run {
    // We create a projection of just the name and age columns, since we're not inserting a value for the id column
    (users.map(u => (u.firstName, u.lastName, u.age))
      // Now define it to return the id, because we want to know what id was generated for the user
      returning users.map(_.id)
      // And we define a transformation for the returned value, which combines our original parameters with the
      // returned id
      into ((params, id) => User(id, params._1, params._2, params._3))
    // And finally, insert the user into the database
    ) += (firstName, lastName, age)
  }

 

  def findById(id: Long): Future[Option[User]] = db.run {
    users.filter(_.id === id).result.headOption
  }
}
