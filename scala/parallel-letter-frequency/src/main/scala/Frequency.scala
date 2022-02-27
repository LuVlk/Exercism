import java.util.concurrent.Executors
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContext, Future}

object Frequency {
  private val ignored = "[ ,\\d]".r

  def frequency(numWorkers: Int, texts: Seq[String]): Map[Char, Int] = {
    implicit val ctx = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(numWorkers))
    Await.result(futureCombineMaps(texts.map(futureCountNotIgnoredChars).toList), 1 second)
  }

  private def futureCombineMaps(maps: List[Future[Map[Char, Int]]])(implicit ctx: ExecutionContext): Future[Map[Char, Int]] =
    Future.foldLeft(maps)(Map[Char, Int]())(sumElements)

  private def sumElements(m1: Map[Char, Int], m2: Map[Char, Int]): Map[Char, Int] =
    (m1.keySet ++ m2.keySet) map { i => i -> (m1.getOrElse(i, 0) + m2.getOrElse(i, 0))} toMap

  private def futureCountNotIgnoredChars(str: String)(implicit ctx: ExecutionContext): Future[Map[Char, Int]] = Future {
    str.toLowerCase.foldLeft(Map[Char, Int]())((map, char) => char match {
      case ignored() => map
      case _ => map.updated(char, map.getOrElse(char, 0) + 1)
    })
  }
}
