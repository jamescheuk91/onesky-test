package domain.baz

import domain.result.Result

class Baz(result: Option[Result]) {

  def getResult(): Option[Result] = {
    result
  }

}