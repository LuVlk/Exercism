import java.time.LocalDate.ofEpochDay

case class Lens[A /* object to act on */, B /* value to act for */](get: A => B, set: (B, A) => A) {
  def compose[C](that: Lens[B, C]): Lens[A, C] = Lens(
    get = (a: A) => that.get(this.get(a)),
    set = (c: C, a: A) => this.set(that.set(c, this.get(a)), a)
  )
}

object LensPerson {
  case class Person(_name: Name, _born: Born, _address: Address)
  val personAdressLens = Lens[Person, Address](
    get = p => p._address,
    set = (a, p) => p.copy(_address = a)
  )
  val personBornLens = Lens[Person, Born](
    get = p => p._born,
    set = (b, p) => p.copy(_born = b)
  )

  case class Name(_foreNames: String /*Space separated*/ , _surName: String)

  // Value of java.time.LocalDate.toEpochDay
  type EpochDay = Long

  case class Born(_bornAt: Address, _bornOn: EpochDay)
  val bornAddressLens = Lens[Born, Address](
    get = b => b._bornAt,
    set = (a, b) => b.copy(_bornAt = a)
  )
  val bornStreetLens = bornAddressLens compose addressStreetLens

  val bornOnLens = Lens[Born, EpochDay](
    get = b => b._bornOn,
    set = (bo, b) => b.copy(_bornOn = bo)
  )
  val epochDayMonthLens = Lens[EpochDay, Int](
    get = ed => ofEpochDay(ed).getMonthValue,
    set = (m, ed) => ofEpochDay(ed).withMonth(m).toEpochDay
  )

  case class Address(_street: String, _houseNumber: Int, _place: String /*Village / city*/ , _country: String)
  val addressStreetLens = Lens[Address, String](a => a._street, (s, a) => a.copy(_street = s))

  // Valid values of Gregorian are those for which 'java.time.LocalDate.of'
  // returns a valid LocalDate.
  case class Gregorian(_year: Int, _month: Int, _dayOfMonth: Int)

  // Implement these.

  val bornStreet: Born => String = bornStreetLens.get

  val setCurrentStreet: String => Person => Person =
    street => (p: Person) => (personAdressLens compose addressStreetLens).set(street, p)

  val setBirthMonth: Int => Person => Person =
    month => (p: Person) => (personBornLens compose bornOnLens compose epochDayMonthLens).set(month, p)

  // Transform both birth and current street names.
  val renameStreets: (String => String) => Person => Person =
    renameFunc => (p: Person) => {
      val personAdressStreetLens = personAdressLens compose addressStreetLens
      val personBornStreetLens = personBornLens compose bornStreetLens

      val pAdressStreetRenamed = personAdressStreetLens.set(renameFunc(personAdressStreetLens.get(p)), p)
      personBornStreetLens.set(renameFunc(personBornStreetLens.get(pAdressStreetRenamed)), pAdressStreetRenamed)
    }
}
