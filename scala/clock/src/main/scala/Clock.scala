case class Clock(hours: Int, minutes: Int) {
  def +(other: Clock): Clock = Clock(hours + other.hours, minutes + other.minutes)
  def -(other: Clock): Clock = Clock(hours - other.hours, minutes - other.minutes)
}

object Clock {
  def apply(minutes: Int): Clock = Clock(0, minutes)

  def apply(hours: Int, minutes: Int): Clock = {
    var h = (hours + minutes / 60) % 24
    var m = minutes % 60

    if (m < 0) {
      h = h - 1
      m = m + 60
    }
    if (h < 0) h = h + 24

    new Clock(h, m)
  }
}