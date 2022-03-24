#!/bin/bash

FILES="e_outputs/*.txt"
num=0
for f in $FILES
do
  {
    diff -u "$f" "outputs/transaction_$((num)).txt"

  } > "transaction_differences/$((num++))_difference.txt"

done
