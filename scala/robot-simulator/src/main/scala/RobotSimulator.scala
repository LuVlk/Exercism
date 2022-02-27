object RobotSimulator {

  implicit class Simulator(robot: Robot) {
    private val _robot = robot

    def simulate(instructions: String): Robot = {
      instructions.foldLeft(_robot) { (robot, char) => char match {
        case 'R' => robot.turnRight
        case 'L' => robot.turnLeft
        case 'A' => robot.advance
      }}
    }
  }
}