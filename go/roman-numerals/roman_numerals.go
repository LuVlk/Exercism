package romannumerals

import (
	"fmt"
)

type ToRomanNumeralErr struct {
	value int
}

func (e ToRomanNumeralErr) Error() string {
	return fmt.Sprintf("%v is out of range", e.value)
}

func ToRomanNumeral(input int) (roman string, err error) {
	if input < 1 || input > 3999 {
		return "", ToRomanNumeralErr{input}
	}

	var r string

	switch {

	case input == 1000:
		roman = "M"

	case input > 1000:
		r, err = ToRomanNumeral(input - 1000)
		roman = "M" + r

	case input == 900:
		roman = "CM"

	case input > 900:
		r, err = ToRomanNumeral(input - 900)
		roman = "CM" + r

	case input == 500:
		roman = "D"

	case input > 500:
		r, err = ToRomanNumeral(input - 500)
		roman = "D" + r

	case input == 400:
		roman = "CD"

	case input > 400:
		r, err = ToRomanNumeral(input - 400)
		roman = "CD" + r

	case input == 100:
		roman = "C"

	case input > 100:
		r, err = ToRomanNumeral(input - 100)
		roman = "C" + r

	case input == 90:
		roman = "XC"

	case input > 90:
		r, err = ToRomanNumeral(input - 90)
		roman = "XC" + r

	case input == 50:
		roman = "L"

	case input > 50:
		r, err = ToRomanNumeral(input - 50)
		roman = "L" + r

	case input == 40:
		roman = "XL"

	case input > 40:
		r, err = ToRomanNumeral(input - 40)
		roman = "XL" + r

	case input == 10:
		roman = "X"

	case input > 10:
		r, err = ToRomanNumeral(input - 10)
		roman = "X" + r

	case input == 9:
		roman = "IX"

	case input == 5:
		roman = "V"

	case input > 5:
		r, err = ToRomanNumeral(input - 5)
		roman = "V" + r

	case input == 4:
		roman = "IV"

	case input == 1:
		roman = "I"

	default:
		r, err = ToRomanNumeral(input - 1)
		roman = "I" + r
	}

	return
}
