package protein

import (
	"errors"
	"unicode/utf8"
)

var ErrStop = errors.New("stop")
var ErrInvalidBase = errors.New("invalid base")

func FromRNA(rna string) ([]string, error) {
	end := utf8.RuneCountInString(rna)
	proteins := []string{}

	for current := 0; current < end; current += 3 {

		codon := rna[current : current+3]

		if protein, err := FromCodon(codon); err == nil {

			proteins = append(proteins, protein)

		} else if err == ErrStop {

			break

		} else {

			return nil, err

		}
	}

	return proteins, nil
}

func FromCodon(codon string) (protein string, err error) {
	switch codon {
	case "AUG":
		protein = "Methionine"
		return
	case "UUU", "UUC":
		protein = "Phenylalanine"
		return
	case "UUA", "UUG":
		protein = "Leucine"
		return
	case "UCU", "UCC", "UCA", "UCG":
		protein = "Serine"
		return
	case "UAU", "UAC":
		protein = "Tyrosine"
		return
	case "UGU", "UGC":
		protein = "Cysteine"
		return
	case "UGG":
		protein = "Tryptophan"
		return
	case "UAA", "UAG", "UGA":
		err = ErrStop
		return
	default:
		err = ErrInvalidBase
		return
	}
}
