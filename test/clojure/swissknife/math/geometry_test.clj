(ns swissknife.math.geometry-test
  (:use [midje.sweet]
        [swissknife.math.geometry]))


(facts

 (point 1 2)
 => {:x 1 :y 2})


(facts

 (intercept->line 1 0)
 => {:c 0 :m 1}

 (point->line 1 (point 0 0))
 => {:c 0 :m 1}

 (points->line (point 0 0) (point 1 1))
 => {:c 0 :m 1})


(facts

 (x (intercept->line 0 1) 5)
 => nil

 (x (intercept->line Double/POSITIVE_INFINITY 1) 5)
 => 1

 (x (intercept->line 1 1) 5)
 => 4)


(facts

 (y (intercept->line 0 1) 5)
 => 1

 (y (intercept->line Double/POSITIVE_INFINITY 1) 5)
 => nil

 (y (intercept->line 1 1) 4)
 => 5)


(facts

 (distance-sqr (point 0 0) (point 1 1))
 => 2

 (distance (point 0 0) (point 1 1))
 => (Math/sqrt 2))


(facts

 (parallel? (intercept->line Double/POSITIVE_INFINITY 0)
            (intercept->line Double/POSITIVE_INFINITY 1))
 => true)


(facts

 (perpendicular? (intercept->line Double/POSITIVE_INFINITY 0)
                 (intercept->line 0 0))
 => true

 (perpendicular? (intercept->line 1 0)
                 (intercept->line 0 0))
 => false

 (perpendicular? (intercept->line 1 0)
                 (intercept->line -1 0))
 => true)


(facts

 (intersects-at (intercept->line Double/POSITIVE_INFINITY 0)
                (intercept->line Double/POSITIVE_INFINITY 0))
 => nil

 (intersects-at (intercept->line Double/POSITIVE_INFINITY 0)
                (intercept->line 1 1))
 => (point 0 1)

 (intersects-at (intercept->line 1 0)
                (intercept->line Double/POSITIVE_INFINITY 1))
 => (point 1 1)

 (intersects-at (intercept->line 1 0)
                (intercept->line -1 2))
 => (point 1 1))


(facts
 "convex hull"

 ;; degenerate case
 (convex-hull [(point 0 0) (point 1 1)])
 => [(point 0 0) (point 1 1)]

 ;; 3 point hull
 (convex-hull [(point 0 0) (point 2 2) (point 0 2)])
 => [(point 0 0) (point 2 2) (point 0 2)]

 ;; right turn from (1, 1) to (0, 2)
 (convex-hull [(point 0 0) (point 2 2) (point 1 1) (point 0 2)])
 => [(point 0 0) (point 2 2) (point 0 2)]

 ;; negative points
 (convex-hull [(point 0 0) (point 2 2) (point 0 2) (point -2 2)])
 => [(point 0 0) (point 2 2) (point 0 2) (point -2 2)]

 ;; right turn from (-1, 1) to (-2, 2)
 (convex-hull [(point 0 0) (point 2 2) (point 0 2) (point -1 1) (point -2 2)])
 => [(point 0 0) (point 2 2) (point 0 2) (point -2 2)]

 ;; 2 right turns and a negative origin
 (convex-hull [(point 0 -1) (point 2 2) (point 1 1)
               (point 0 2) (point -1 1) (point -2 2)])
 => [(point 0 -1) (point 2 2) (point 0 2) (point -2 2)])
