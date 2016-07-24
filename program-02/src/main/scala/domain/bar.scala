package domain.bar

import domain.baz.Baz

class Bar(baz: Option[Baz]) {

  def getBaz(): Option[Baz] = {
    baz
  }
}
