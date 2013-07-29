(ns util.core-test
  (:use [midje.sweet]
        [util.core]))


(facts
 (keep-last 3 (range 10))
 => [7 8 9]

 (keep-last 3 [0 1])
 => [0 1])
