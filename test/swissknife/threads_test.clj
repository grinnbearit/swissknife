(ns swissknife.threads-test
  (:use [midje.sweet]
        [swissknife.threads]))


(facts
 (let [start (System/nanoTime)]
   (sleep 100)
   => 100

   (< (- 1e8 1) (- (System/nanoTime) start))
   => true

   (sleep 100)
   => :mocked

   (provided
    (sleep 100) => :mocked)))
