package repository.user

import scala.collection.mutable.HashMap
import java.util.concurrent.atomic.AtomicLong

import domain.user.User
import domain.role.Role

class UserRepository {
          
  val users = HashMap.empty[Long, User]
  val idSequence = new AtomicLong(0)

  def createUser(roles: List[Role]): User = {
    val newId = idSequence.incrementAndGet()
    val newUser = new User(newId, roles)
    users.put(newId, newUser)
    newUser
  }

  def findUserById(id: Long): Option[User] = {
    users.get(id)
  }
}