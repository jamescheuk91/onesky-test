import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}

object Program03 {
  def main(args: Array[String]): Unit = {
    println("Run Program03!")

    // I use Future becasue of it represents the result of an asynchronous computation.
    val result: Future[Int] = Future {
      1+1
    }
    // use onComplete to handle whether the Future returned
    result.onComplete {
        case Success(value) => println(value)
        case Failure(e) => e.printStackTrace
    }
  }
}


/**
  The java code use Thread from thread model,
  and the synchronization is not implemented.
  The variable result and hasResult was defined.
  Inside the thread run method, the outter scope variable was mutated.
  Setting hasResult as true to make program run is not a good practice.
*/