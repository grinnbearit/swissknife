(ns swissknife.core-test
  (:use [midje.sweet]
        [swissknife.collections]))


(facts
 (queue) => []

 (queue :a :b :c) => [:a :b :c]

 (peek (queue :a :b :c)) => :a

 (pop (queue :a :b :c)) => [:b :c]

 (conj (queue :a :b :c) :d) => [:a :b :c :d])


(facts
 (distinct-queue) => []

 (distinct-queue :a :b :c) => [:a :b :c]

 (distinct-queue :a :a :b :b :c :c) => [:a :b :c]

 (peek (distinct-queue :a :a :c)) => :a

 (pop (distinct-queue :a :a :c)) => [:c]

 (conj (distinct-queue :a :b :c) :b) => [:a :b :c]

 (conj (distinct-queue :a :b :c) :d) => [:a :b :c :d])


(facts
 (priority-queue)
 => []

 (priority-queue {:a 1 :b 2 :c 3} < :a :b :c)
 => [:a :b :c]

 (priority-queue {:a 1 :b 2 :c 3} > :a :b :c)
 => [:c :b :a]

 (seq (conj (priority-queue) 3 1 2))
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
 => 1

 (count (conj (priority-queue) 2 3 2 2))
 => 4)


(facts
 "Persistent Distinct Priority Queue"

 (distinct-priority-queue)
 => []

 (distinct-priority-queue {:a 1 :b 2 :c 3} < :a :b :c)
 => [:a :b :c]

 (distinct-priority-queue {:a 1 :b 2 :c 3} < :a :a :b :b :c :c)
 => [:a :b :c]

 (distinct-priority-queue {:a 1 :b 2 :c 3} > :a :b :c)
 => [:c :b :a]

 (distinct-priority-queue {:a 1 :b 2 :c 3} > :a :a :b :b :c :c)
 => [:c :b :a]

 (seq (conj (distinct-priority-queue) 3 1 2))
 => [1 2 3]

 (seq (conj (distinct-priority-queue) 3 3 1 1 2 2))
 => [1 2 3]

 (conj (distinct-priority-queue) 2 3 1)
 => [1 2 3]

 (conj (distinct-priority-queue) 2 2 3 3 1 1)
 => [1 2 3]

 (empty? (distinct-priority-queue))
 => true

 (peek (distinct-priority-queue))
 => nil

 (peek (conj (distinct-priority-queue) 2 3 1))
 => 1

 (pop (conj (distinct-priority-queue) 2 3 1))
 => [2 3]

 (peek (conj (distinct-priority-queue) 2 3 1 2))
 => 1

 (pop (conj (distinct-priority-queue) 2 3 1 2))
 => [2 3]

 (count (conj (distinct-priority-queue) 2 3 2 2))
 => 2)


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
