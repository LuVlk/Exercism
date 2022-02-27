package stack

import scala.collection.immutable

trait ForthStack {
  var stack: immutable.Seq[Int] = List[Int]()

  def push(value: Int): Unit = {
    stack = value +: stack
  }

  def pop(): Option[Int] = {
    stack.headOption match {
      case None => None
      case Some(item) =>
        stack = stack.tail
        Some(item)
    }
  }

  override def toString: String = {
    stack.reverse.mkString(" ")
  }
}
