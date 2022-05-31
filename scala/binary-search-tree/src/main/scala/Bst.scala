import scala.math.Ordered.orderingToOrdered

case class Bst[T](
                  value: T,
                  var left: Option[Bst[T]] = None,
                  var right: Option[Bst[T]] = None)
                 (implicit ordering: Ordering[T]) {

  private def insertOrInit(it: T)(bstOpt: Option[Bst[T]]): Bst[T] = {
    bstOpt.fold(Bst(it))(bst => bst.insert(it))
  }

  def insert(it: T): Bst[T] = {
    val actOn = insertOrInit(it)(_)
    if (it > value) right = Some(actOn(right))
    else left = Some(actOn(left))
    this
  }
}

object Bst {

  def fromList[T](list: List[T])(implicit ordering: Ordering[T]): Bst[T] = list match {
      case head :: tail =>
        tail.foldLeft(Bst(head))((bst, item) => bst.insert(item))
  }

  def toList[T](bst: Bst[T])(implicit ordering: Ordering[T]): List[T] = bst match {
    case Bst(value, left, right) =>
      left.fold(List[T]())(Bst.toList) ::: List(value) ::: right.fold(List[T]())(Bst.toList)
  }

}