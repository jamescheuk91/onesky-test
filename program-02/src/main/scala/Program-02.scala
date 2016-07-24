
import domain.foo.Foo
import domain.first.First
import domain.bar.Bar
import domain.baz.Baz
import domain.result.Result

object Program02 {
  /// Setup
  val _result = if (math.random > 0.5)
    Some(new Result())
  else 
    None

  val _baz = new Baz(_result)
  val _bar = new Bar(Some(_baz))
  val _foo = new Foo()
  ///
  def main(args: Array[String]): Unit = {
    println("Run Program02!")
    val first = new First(Some(_bar))
    // use for-comprehension to replace nested if statement
    val result = for {
      foo <- getFoo()
      bar <- first.getSecond()
      baz <- bar.getBaz()
      result <- baz.getResult()
    } yield {
      result
    }

    // use pattern matching to replace if statement
    result match {
      case Some(result) => println(result.toString)
      case None => println("no result")
    }
  }

  def getFoo(): Option[Foo] = {
    Some(_foo)
  }
}
