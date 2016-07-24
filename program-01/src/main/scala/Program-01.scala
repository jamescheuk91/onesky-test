package program01

import domain.user.User
import domain.role.Role
import repository.user.UserRepository

object Program01 {
  val userRepository = new UserRepository()
  def main(args: Array[String]): Unit = {
    println("Run Program01")
    // setup ------------------------------------------
    val roles = List(new Role("User"), new Role("Tester"))
    userRepository.createUser(roles)

    val user = userRepository.findUserById(1)
    // -----------------------------------------------
    /**
     * use function composition to replace nested loop statement.
     * it is clear
     * It avoid unnecessary variables, 
     * for example: 
     * roleNames, roles
     */
    user match {
      case Some(value) =>
        value.getRoles()
          .map(formatRoleName)
          .foreach(println)
      case None => println("user not found")
    }
    
  }

  def formatRoleName(role: Role): String = {
    s"Role: ${role.getName()}"
  }

}
