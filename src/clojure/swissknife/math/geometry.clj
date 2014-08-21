(ns swissknife.math.geometry)


(defrecord Point2D [x y])
(defrecord Line2D [m c])


(defn point
  [x y]
  (Point2D. x y))


(defn intercept->line
  "If slope is positive infinity (line parallel to the y axis), the intercept is the x intercept
otherwise, its the y intercept as usual"
  [slope intercept]
  (Line2D. slope intercept))


(defn point->line
  "Instantiates a line from a slope and a point"
  [slope point]
  (if (= slope Double/POSITIVE_INFINITY)
    (intercept->line slope (:x point))
    (intercept->line slope (+ (:y point) (* slope (- (:x point)))))))


(defn points->line
  "Instantiates a line from 2 points"
  [point-1 point-2]
  (if (not= (:x point-2) (:x point-1))
    (point->line (/ (- (:y point-2) (:y point-1))
                    (- (:x point-2) (:x point-1)))
                 point-1)
    (intercept->line Double/POSITIVE_INFINITY point-1)))


(defn y
  "Given a line and an x-coordinate, solves for y"
  [line x]
  (condp = (:m line)
    Double/POSITIVE_INFINITY nil
    0 (:c line)
    (+ (* (:m line) x) (:c line))))


(defn x
  "Given a line and a y-coordinate, solves for x"
  [line y]
  (condp = (:m line)
    Double/POSITIVE_INFINITY (:c line)
    0 nil
    (/ (- y (:c line)) (:m line))))


(defn distance-sqr
  "The squared distance between 2 points (prevents conversion to floating point)"
  [point-1 point-2]
  (letfn [(square [x] (* x x))]
    (+ (square (- (:y point-1) (:y point-2)))
       (square (- (:x point-1) (:x point-2))))))


(defn distance
  "The distance between 2 points"
  [point-1 point-2]
  (Math/sqrt (distance-sqr point-1 point-2)))


(defn invert-slope
  "The slope perpendicular to this one"
  [m]
  (condp = m
    0 Double/POSITIVE_INFINITY
    Double/POSITIVE_INFINITY 0
    (/ -1 m)))


(defn parallel?
  "Tests if 2 lines are parallel"
  [line1 line2]
  (= (:m line1) (:m line2)))


(defn perpendicular?
  "Tests if 2 lines are perpendicular"
  [line1 line2]
  (= (:m line1) (invert-slope (:m line2))))


(defn intersects-at
  "Find the intersection point between 2 lines, returns nil if it doesn't exist (lines are parallel)"
  [line1 line2]
  (when-not (parallel? line1 line2)
    (cond (= line1 line2)
          nil

          (= (:m line1) Double/POSITIVE_INFINITY)
          (point (:c line1) (y line2 (:c line1)))

          (= (:m line2) Double/POSITIVE_INFINITY)
          (point (:c line2) (y line1 (:c line2)))

          :else
          (let [x (/ (- (:c line1) (:c line2))
                     (- (:m line2) (:m line1)))]
            (point x (y line1 x))))))
