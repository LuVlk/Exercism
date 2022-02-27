object SpiralMatrix {

  def spiralMatrix(size: Int): List[List[Int]] = {

    val sm = Array.fill(size, size)(0)
    val positions = smElementPositions(size)

    1 to size * size foreach { value =>
      sm(positions(value - 1)._1)(positions(value - 1)._2) = value
    }

    sm.map(_.toList).toList
  }

  private def smElementPositions(size: Int): Array[(Int, Int)] = {
    // create map of matrix element positions from 1 to size ^ size

    // start at position (0,0)
    var stepsTaken = Array[(Int, Int)]((0, 0))

    val dir = Iterator.continually(Seq(
      StepDirection.pos,
      StepDirection.pos,
      StepDirection.neg,
      StepDirection.neg)).flatten

    val dim = Iterator.continually(Seq(
      StepDimension.Y,
      StepDimension.X)).flatten

    // do size - 1 steps in + y direction
    // do size - 1 steps in + x direction
    // do size - 1 steps in - y direction
    1 to 3 foreach {_ =>
      stepsTaken = stepsTaken ++ takeSteps(size - 1, stepsTaken.last, (dim.next, dir.next))
    }

    var stepDecrement = 2
    while (stepsTaken.length < size * size) {
      // do size - 2 steps in - x
      // do size - 2 steps in + y
      // do size - 3 steps in + x
      // do size - 3 steps in - y
      // ...
      0 until 2 foreach { _ =>
        stepsTaken = stepsTaken ++ takeSteps(size - stepDecrement, stepsTaken.last, (dim.next, dir.next))
      }
      stepDecrement = stepDecrement + 1
    }

    stepsTaken
  }

  import StepDimension.StepDimension
  import StepDirection.StepDirection

  private def takeSteps(n: Int, pos: (Int, Int), step: (StepDimension, StepDirection)): Array[(Int, Int)] = {
    var stepsTaken = Array[(Int, Int)]()
    var curPos = pos

    0 until n foreach { _ =>
      curPos = step match {
        case (StepDimension.Y, StepDirection.pos) => (curPos._1, curPos._2 + 1)
        case (StepDimension.Y, StepDirection.neg) => (curPos._1, curPos._2 - 1)
        case (StepDimension.X, StepDirection.pos) => (curPos._1 + 1, curPos._2)
        case (StepDimension.X, StepDirection.neg) => (curPos._1 - 1, curPos._2)
      }
      stepsTaken = stepsTaken :+ curPos
    }

    stepsTaken
  }
}

private object StepDimension extends Enumeration {
  type StepDimension = Value
  val X, Y = Value
}

private object StepDirection extends Enumeration {
  type StepDirection = Value
  val pos, neg = Value
}