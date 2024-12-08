#!/usr/bin/env bash

if (( $# != 2 )); then
    >&2 echo "Usage: $0 <planet> <age in seconds>"
    exit 1
fi

planet=$1
seconds=$2

declare -A orbital_periods=(
    ["Mercury"]=0.2408467
    ["Venus"]=0.61519726
    ["Earth"]=1.0
    ["Mars"]=1.8808158
    ["Jupiter"]=11.862615
    ["Saturn"]=29.447498
    ["Uranus"]=84.016846
    ["Neptune"]=164.79132
)

period=( ${orbital_periods["$planet"]} )

if [ -z $period ]; then 
    >&2 echo "$planet is not a planet" 
    exit 1
fi

age=$( bc <<< "scale=3; $seconds/31557600/$period" )

printf "%.2f" "$age"