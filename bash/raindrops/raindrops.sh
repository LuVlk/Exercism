#!/usr/bin/env bash

(( $1 % 3 )) || out+=Pling
(( $1 % 5 )) || out+=Plang
(( $1 % 7 )) || out+=Plong

echo ${out:-$1}