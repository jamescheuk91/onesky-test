package domain.first

import domain.bar.Bar

class First(second: Option[Bar]) {

  def getSecond(): Option[Bar] = {
    second
  }

}