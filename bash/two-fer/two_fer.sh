#!/usr/bin/env bash

main () {
    person="you"
    [[ -n "$1" ]] && person="$1"
    echo "One for $person, one for me."    
}

main "$@"