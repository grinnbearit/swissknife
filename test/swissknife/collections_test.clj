(ns swissknife.core-test
  (:use [midje.sweet]
        [swissknife.collections]))


(facts
 (queue) => []

 (queue 1 2 3) => [1 2 3]

 (peek (queue 1 2 3)) => 1

 (pop (queue 1 2 3)) => [2 3]

 (conj (queue 1 2 3) 4) => [1 2 3 4])


(facts
 (priority-queue)
 => []

 (priority-queue identity < 2 3 1)
 => [1 2 3]

 (priority-queue identity > 2 3 1)
 => [3 2 1]

 (seq (conj (priority-queue) 2 3 1))
 => [1 2 3]

 (conj (priority-queue) 2 3 1)
 => [1 2 3]

 (empty? (priority-queue))
 => true

 (peek (priority-queue))
 => nil

 (peek (conj (priority-queue) 2 3 1))
 => 1

 (pop (conj (priority-queue) 2 3 1))
 => [2 3]

 (-> (conj (priority-queue) 2 3 1) pop peek)
 => 2

 (-> (conj (priority-queue) 2 1 3 1) pop peek)
 => 1)


(facts
 "MultiAssociative insert"

 (insert {} :a 1)
 => {:a 1}

 (insert {:a 1} :b 2)
 => {:a 1 :b 2}

 (insert {:a 1} :a 1)
 => {:a 1}

 (insert {:a 1} :a 2)
 => {:a #{1 2}}

 (insert {:a 1} :a 2 :a 3)
 => {:a #{1 2 3}})


(facts
 "MultiAssociative delete"

 (delete {} :a 1)
 => {}

 (delete {:a 1} :a 2)
 => {:a 1}

 (delete {:a 1} :a 1)
 => {}

 (-> (insert {:a 1} :a 2)
     (delete :a 1))
 => {:a 2}

 (-> (insert {:a 1} :a 2 :a 3)
     (delete :a 1 :a 2))
 => {:a 3})


(facts
 "MultiAssociative get"

 (get-all {} :a)
 => #{}

 (get-all {} :a nil)
 => nil

 (get-all {:a 1} :a)
 => #{1}

 (-> (insert {:a 1} :a 2)
     (get-all :a))
 => #{1 2})
