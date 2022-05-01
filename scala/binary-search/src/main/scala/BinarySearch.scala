object BinarySearch {
  def find[A](list: List[A], element: A)(implicit ordering: Ordering[A]): Option[Int] = {
    var l = list
    var offset = 0
    while (l.nonEmpty) {
      val i = l.length / 2
      val el = l(i)
      if (el == element) return Some(i + offset)

      if (l.length == 1) return None
      val (l1, l2) = l.splitAt(i)
      if (ordering.lt(element, el)) l = l1
      else { l = l2; offset += i }
    }
    None
  }
}