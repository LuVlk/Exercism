import Schedule.{First, Fourth, Last, Schedule, Second, Teenth, Third}

import java.time.{DayOfWeek, LocalDate}

object Schedule extends Enumeration {
  type Schedule = Value
  val Teenth, First, Second, Third, Fourth, Last = Value
}

object Meetup {
  val Mon = DayOfWeek.MONDAY.getValue
  val Tue = DayOfWeek.TUESDAY.getValue
  val Wed = DayOfWeek.WEDNESDAY.getValue
  val Thu = DayOfWeek.THURSDAY.getValue
  val Fri = DayOfWeek.FRIDAY.getValue
  val Sat = DayOfWeek.SATURDAY.getValue
  val Sun = DayOfWeek.SUNDAY.getValue
}

case class Meetup(month: Int, year: Int) {

  def day(dayOfWeek: Int, schedule: Schedule): LocalDate = {
    val start = LocalDate.of(year, month, 1)
    val datesOfInterest = start.datesUntil(
      start.plusMonths(1)).filter(_.getDayOfWeek == DayOfWeek.of(dayOfWeek))

    schedule match {
      case First => datesOfInterest.findFirst().get()
      case Second => datesOfInterest.skip(1).findFirst().get()
      case Third => datesOfInterest.skip(2).findFirst().get()
      case Fourth => datesOfInterest.skip(3).findFirst().get()
      case Last => datesOfInterest.reduce((first, second) => second).get()
      case Teenth => datesOfInterest.filter(date => date.getDayOfMonth > 12 && date.getDayOfMonth < 20).findFirst().get()
    }
  }
}
