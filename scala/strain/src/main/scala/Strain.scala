object Strain {
  def keep[A](list: Iterable[A], pred: A => Boolean): Iterable[A] = {
    list.foldLeft(Iterable[A]())(
      (acc, a) => if (pred(a)) acc ++ Iterable(a) else acc
    )
  }

  def discard[A](list: Iterable[A], pred: A => Boolean): Iterable[A] =
    keep[A](list, a => !pred(a))
}