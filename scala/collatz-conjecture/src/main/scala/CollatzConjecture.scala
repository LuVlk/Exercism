object CollatzConjecture {

  private def isEven(i: Int): Boolean = {
      i % 2 == 0
  }

  private def step(number: Int): Int = number match {
    case n if isEven(n) => number / 2
    case n if !isEven(n) => number * 3 + 1
  }

  def steps(number: Int): Option[Int] = {
    if (number <= 0) return None

    var num = number
    var stepsTaken = 0

    while (num != 1) {
      num = step(num)
      stepsTaken += 1
    }

    Some(stepsTaken)
  }
}