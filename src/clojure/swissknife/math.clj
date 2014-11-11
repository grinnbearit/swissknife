(ns swissknife.math)

;;; constants

(def TAU (* 2 Math/PI))

;;; functions

(defn factorial
  [n]
  (reduce * (bigint 1) (range 1 (inc n))))


(defn factors
  [n]
  (->> (range 1 (inc (Math/sqrt n)))
       (filter #(= 0 (rem n %)))
       (mapcat #(vector % (/ n %)))))


(defn P
  [n k]
  (if (= n k)
    (factorial n)
    (reduce * (bigint 1) (range (- (inc  n) k) (inc n)))))


(defn C
  [n k]
  (/ (P n k) (factorial k)))


(defn geometric-sum
  "geometric sum of n terms of a series with first term a and common ratio r"
  [a r n]
  (* a (/ (dec (Math/pow r n)) (dec r))))


(defn degrees->radians
  [d]
  (* d TAU 1/360))


(defn radians->degrees
  [r]
  (* r 360 (/ TAU)))


(defn quadratic
  [a b c]
  (let [discriminant (- (* b b) (* 4 a c))]
    (if (neg? discriminant)
      (throw (ArithmeticException. "No real solutions"))
      [(/ (+ (- b) (Math/sqrt discriminant)) (* 2 a))
       (/ (- (- b) (Math/sqrt discriminant)) (* 2 a))])))
