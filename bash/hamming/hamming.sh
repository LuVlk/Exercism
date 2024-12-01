#!/usr/bin/env bash

error () {
  printf '%s\n' "$*"
  exit 1
}

main () {
  (( $# == 2 )) || error 'Usage: hamming.sh <string1> <string2>'
  
  dna1=$1 dna2=$2 

  (( ${#dna1} == ${#dna2} )) || error 'strands must be of equal length'

  declare -i hamming
  for (( i = 0; i < ${#dna1}; i++ )); do
    [[ ${dna1:i:1} == "${dna2:i:1}" ]] || hamming+=1
  done

  printf '%d\n' "$hamming"
}

main "$@"