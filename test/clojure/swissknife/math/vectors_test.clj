(ns swissknife.math.vectors-test
  (:use [midje.sweet]
        [swissknife.math.vectors])
  (:require [swissknife.math :refer [TAU]]))


(facts
 "angle->vector"

 (angle->vector (Math/sqrt 2) (/ TAU 8))
 => {:m (Math/sqrt 2) :theta (/ TAU 8)})


(facts
 "components->vector"

 (components->vector 1 1)
 => {:m (Math/sqrt 2) :theta (/ TAU 8)})


(facts
 "vector components"

 (->> (x-component (components->vector 3 4))
      (Math/round))
 => 3

 (->> (y-component (components->vector 3 4))
      (Math/round))
 => 4)


(facts
 "vector addition"

 (add (components->vector 4 3)
      (components->vector 0 1))
 => {:m (* 4 (Math/sqrt 2)) :theta (/ TAU 8)})


(facts
 "vector subtraction"

 (let [v (subtract (components->vector 4 4)
                   (components->vector 0 1))]

   (Math/round (x-component v))
   => 4

   (Math/round (y-component v))
   => 3))
