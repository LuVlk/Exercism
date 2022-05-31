trait SimpleLinkedList[T] {
  def isEmpty: Boolean
  def value: T
  def add(item: T): SimpleLinkedList[T]
  def next: SimpleLinkedList[T]
  def reverse: SimpleLinkedList[T]
  def toSeq: Seq[T]
}

object SimpleLinkedList {

  private case class Node[T](var _value: Option[T] = None) extends SimpleLinkedList[T] {
    var _next: Option[SimpleLinkedList[T]] = None

    override def isEmpty: Boolean = _value.isEmpty

    override def value: T = _value.get

    override def add(item: T): SimpleLinkedList[T] = {
      if (isEmpty) _value = Some(item)
      else if (next.isEmpty) {
        val newList = apply(item)
        _next = Some(newList)
      }
      else next.add(item)
      this
    }

    override def next: SimpleLinkedList[T] = _next.getOrElse(Node())

    override def reverse: SimpleLinkedList[T] = {
      if (isEmpty) this
      else next.reverse.add(value)
    }

    override def toSeq: Seq[T] = {
      if (isEmpty) Seq()
      else Seq(_value.get) ++ next.toSeq
    }
  }

  def apply[T](items: T*): SimpleLinkedList[T] =
    items.foldLeft[SimpleLinkedList[T]](Node())((list, item) => list.add(item))

  def fromSeq[T](items: Seq[T]): SimpleLinkedList[T] = apply(items: _*)
}