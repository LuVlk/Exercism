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

var thousands = [4]string{"", "M", "MM", "MMM"}
var hundrets = [10]string{"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"}
var tens = [10]string{"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"}
var units = [10]string{"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"}

func ToRomanNumeral(input int) (roman string, err error) {
	if input < 0 || input == 0 || input > 3999 {
		return "", ToRomanNumeralErr{input}
	}

	roman = thousands[input/1000] +
		hundrets[input%1000/100] +
		tens[input%100/10] +
		units[input%10]
	return
}
