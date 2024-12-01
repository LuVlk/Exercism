#!/usr/bin/env bash

if (( $# != 2 )); then
    >&2 echo "Usage: hamming.sh <string1> <string2>"
    exit 1
fi

if [[ ${#1} != ${#2} ]]; then 
    echo "strands must be of equal length" 
    exit 1
fi

left_dna=$1
right_dna=$2

(( hamming=0 ))

for (( i=0; i<${#left_dna}; i++ )); do
  [[ "${left_dna:$i:1}" == "${right_dna:$i:1}" ]] || (( hamming++ ))
done

echo "$hamming"