import scala.collection.mutable

object Grains {
  val chessboard: mutable.Map[Int, Option[BigInt]] = mutable.Map()

  def square(field: Int): Option[BigInt] = {
    if (field == 1) Some(1)
    else if (field < 1) None
    else if (field > 64) None
    else chessboard.getOrElseUpdate(field, square(field - 1).map(_ * 2))
  }

  def total: BigInt = (1 to 64)
    .map(square)
    .reduce((left, right) => for (v1 <- left; v2 <- right) yield v1 + v2)
    .get
}