package allyourbase

import "errors"

var ErrInvalidOutputBase = errors.New("output base must be >= 2")
var ErrInvalidInputBase = errors.New("input base must be >= 2")
var ErrInvalidDigit = errors.New("all digits must satisfy 0 <= d < input base")

func ConvertToBase(inputBase int, inputDigits []int, outputBase int) ([]int, error) {
	n, err := fromDigits(inputDigits, inputBase)
	if err != nil {
		return nil, err
	}
	return toDigits(n, outputBase)
}

func fromDigits(digits []int, base int) (int, error) {
	if base < 2 {
		return 0, ErrInvalidInputBase
	}

	n := 0
	for _, d := range digits {
		if d >= base || d < 0 {
			return 0, ErrInvalidDigit
		}
		n = n*base + d
	}
	return n, nil
}

func toDigits(number int, base int) ([]int, error) {
	if base < 2 {
		return nil, ErrInvalidOutputBase
	}

	digits := make([]int, 0)
	for {
		digits = append(digits, number%base)
		number /= base
		if number == 0 {
			break
		}
	}

	for i, j := 0, len(digits)-1; i < j; i, j = i+1, j-1 {
		digits[i], digits[j] = digits[j], digits[i]
	}
	return digits, nil
}
