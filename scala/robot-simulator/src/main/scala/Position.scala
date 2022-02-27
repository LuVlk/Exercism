import scala.language.implicitConversions

case class Position(x: Int, y: Int)

object Position {

  implicit def toPosition(tuple: (Int, Int)): Position = {
    Position(tuple._1, tuple._2)
  }

  implicit def toTuple2(position: Position): (Int, Int) = {
    (position.x, position.y)
  }

}
