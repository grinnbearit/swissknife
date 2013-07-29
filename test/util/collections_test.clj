(ns util.core-test
  (:use [midje.sweet]
        [util.collections]))


(facts
 (queue) => []

 (queue 1 2 3) => [1 2 3]

 (peek (queue 1 2 3)) => 1

 (pop (queue 1 2 3)) => [2 3]

 (conj (queue 1 2 3) 4) => [1 2 3 4])


(facts
 (priority-queue identity)
 => []

 (priority-queue identity 2 3 1)
 => [1 2 3]

 (seq (priority-queue identity 2 3 1))
 => [1 2 3]

 (= (priority-queue identity) [])
 => true

 (= (priority-queue identity 2 3 1) [1 2 3])
 => true

 (conj (priority-queue identity) 2 3 1)
 => [1 2 3]

 (empty? (priority-queue identity))
 => true

 (peek (priority-queue identity))
 => nil

 (peek (priority-queue identity 2 3 1))
 => 1

 (pop (priority-queue identity 2 3 1))
 => [2 3]

 (-> (priority-queue identity 2 3 1) pop peek)
 => 2

 (-> (priority-queue identity 2 1 3 1) pop peek)
 => 1)
