(ns swissknife.core-test
  (:use [midje.sweet]
        [swissknife.core]))


(facts
 "keep-last"

 (keep-last 3 (range 10))
 => [7 8 9]

 (keep-last 3 [0 1])
 => [0 1])


(facts
 "group"

 (group even? #(* % %) (partial apply +) (range 10))
 => {false 165                          ; [1 9 25 49 81]
     true 120})                         ; [0 4 16 36 64]


(facts
 "bucket"

 (bucket identity 3 (range 10))
 => [[0 1 2 3 4 5] [6 7 8] [9]]         ; [15 21 9]

 (bucket #(* % %) 3 (range 10))
 => [[0 1 2 3 4 5 6 7] [8 9] []])       ; [140 145 0]
