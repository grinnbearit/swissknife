(ns swissknife.math.vectors)


(defrecord Vector2D [m theta])


(defn angle->vector
  [magnitude angle]
  (Vector2D. magnitude angle))


(defn components->vector
  [x-component y-component]
  (letfn [(sqr [x] (* x x))]
    (angle->vector (Math/sqrt (+ (sqr x-component) (sqr y-component)))
                   (Math/atan2 y-component x-component))))


(defn x-component
  [v]
  (* (:m v) (Math/cos (:theta v))))


(defn y-component
  [v]
  (* (:m v) (Math/sin (:theta v))))


(defn add
  [v1 v2]
  (components->vector
   (+ (x-component v1) (x-component v2))
   (+ (y-component v1) (y-component v2))))


(defn subtract
  [v1 v2]
  (components->vector
   (- (x-component v1) (x-component v2))
   (- (y-component v1) (y-component v2))))
