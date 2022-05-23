case class Deque[A]() {
  private case class Node(value: A, var next: Option[Node] = None, var prev: Option[Node] = None)
  private var headOption: Option[Node] = None
  private var tailOption: Option[Node] = None

  private def init(item: A): Unit = {
    val head = Node(item)
    val tail = head

    headOption = Some(head)
    tailOption = Some(tail)
  }

  def push(item: A): Unit = tailOption match {
    case Some(tail) =>
      val newTail = Node(item)
      newTail.prev = Some(tail)
      tailOption = Some(newTail)
      tail.next = tailOption
    case None => init(item)
  }

  def unshift(item: A): Unit = headOption match {
    case Some(head) =>
      val newHead = Node(item)
      newHead.next = Some(head)
      headOption = Some(newHead)
      head.prev = headOption
    case None => init(item)
  }

  def pop(): Option[A] = tailOption match {
    case Some(tail) => tailOption = tail.prev; Some(tail.value)
    case None => None
  }

  def shift(): Option[A] = headOption match {
    case Some(head) => headOption = head.next; Some(head.value)
    case None => None
  }
}