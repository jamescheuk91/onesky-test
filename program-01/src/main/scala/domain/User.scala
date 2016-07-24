package domain.user

import domain.role.Role

class User(id: Long, roles: List[Role]) {

  def getRoles(): List[Role] = {
    roles
  }
}