(ns util.core-test
  (:use [midje.sweet]
        [util.core]))


(facts
 (keep-last 3 (range 10))
 => [7 8 9]

 (keep-last 3 [0 1])
 => [0 1])


(facts
 (queue) => []

 (queue 1 2 3) => [1 2 3]

 (peek (queue 1 2 3)) => 1

 (pop (queue 1 2 3)) => [2 3]

 (conj (queue 1 2 3) 4) => [1 2 3 4])
