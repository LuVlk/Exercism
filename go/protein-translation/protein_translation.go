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
	case "UUU":
		protein = "Phenylalanine"
		return
	case "UUC":
		protein = "Phenylalanine"
		return
	case "UUA":
		protein = "Leucine"
		return
	case "UUG":
		protein = "Leucine"
		return
	case "UCU":
		protein = "Serine"
		return
	case "UCC":
		protein = "Serine"
		return
	case "UCA":
		protein = "Serine"
		return
	case "UCG":
		protein = "Serine"
		return
	case "UAU":
		protein = "Tyrosine"
		return
	case "UAC":
		protein = "Tyrosine"
		return
	case "UGU":
		protein = "Cysteine"
		return
	case "UGC":
		protein = "Cysteine"
		return
	case "UGG":
		protein = "Tryptophan"
		return
	case "UAA":
		err = ErrStop
		return
	case "UAG":
		err = ErrStop
		return
	case "UGA":
		err = ErrStop
		return
	default:
		err = ErrInvalidBase
		return
	}
}
