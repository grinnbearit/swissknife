(ns swissknife.core-test
  (:use [midje.sweet]
        [swissknife.core]))


(facts
 (keep-last 3 (range 10))
 => [7 8 9]

 (keep-last 3 [0 1])
 => [0 1])
