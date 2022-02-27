import Bearing._

case class Robot(brng: Bearing, pos: Position) {
  private val _pos: Position = pos
  private val _bearing: Bearing = brng

  def coordinates: (Int, Int) = _pos

  def bearing: Bearing = _bearing

  def turnRight: Robot = {
    Robot(Bearing.nextOf(_bearing), _pos)
  }

  def turnLeft: Robot = {
    Robot(Bearing.prevOf(_bearing), _pos)
  }

  def advance: Robot = {
    Robot(_bearing, _bearing match {
      case North => Position(_pos.x, _pos.y + 1)
      case East  => Position(_pos.x + 1, _pos.y)
      case South => Position(_pos.x, _pos.y - 1)
      case West  => Position(_pos.x - 1, _pos.y)
    })
  }
}