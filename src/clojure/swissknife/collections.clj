(ns swissknife.collections)


(defn queue
  "Returns a clojure.lang.PersistentQueue initialized with xs"
  [& xs]
  (reduce conj (clojure.lang.PersistentQueue/EMPTY) xs))


(deftype PersistentPriorityQueue [f m]

  clojure.lang.IPersistentList
  (seq [this]
    (seq (mapcat second m)))

  (cons [this o]
    (->> (fn [q] (if q (conj q o) (queue o)))
         (update-in m [(f o)])
         (PersistentPriorityQueue. f)))

  (empty [this]
    (PersistentPriorityQueue. f (sorted-map)))

  (equiv [this o]
    (= (or (seq this) ()) o))

  (peek [this]
    (-> m first second first))

  (pop [this]
    (let [[p q] (first m)]
      (if (< (count q) 2)
        (PersistentPriorityQueue. f (dissoc m p))
        (PersistentPriorityQueue. f (update-in m [p] pop))))))


(defn priority-queue
  ([]
     (PersistentPriorityQueue. identity (sorted-map)))
  ([priority-fn]
     (PersistentPriorityQueue. priority-fn (sorted-map)))
  ([priority-fn comparator & xs]
     (-> (PersistentPriorityQueue. priority-fn (sorted-map-by comparator))
         (into xs))))


;;; From The Clojure Cookbook 2014
;;; https://github.com/clojure-cookbook/clojure-cookbook/tree/master/02_composite-data/2-22_multiple-values
(defprotocol MultiAssociative
  (-insert [m k v]
    "Insert a value into a MultiAssociative")
  (-delete [m k v]
    "Delete a value from a MultiAssociative")
  (-get-all [m k]
    "Returns a set of all values stored at key in a MultiAssociative.
     Returns the empty set if there are no values."))


(defn- value-set?
  [v]
  (and (set? v) (::multi-value (meta v))))


(defn- value-set
  [& xs]
  (let [x (set xs)]
    (case (count x)
      0 nil
      1 (first x)
      (with-meta (set xs) {::multi-value true}))))


(extend-protocol MultiAssociative
  clojure.lang.Associative
  (-insert [this k v]
    (if (contains? this k)
      (let [x (get this k)]
        (if (value-set? x)
          (assoc this k (apply value-set (conj x v)))
          (assoc this k (value-set x v))))
      (assoc this k v)))

  (-delete [this k v]
    (let [x (get this k)]
      (cond (value-set? x)
            (assoc this k (apply value-set (disj x v)))

            (= x v)
            (dissoc this k v)

            :else
            this)))

  (-get-all [this k]
    (if (contains? this k)
      (let [x (get this k)]
        (if (value-set? x)
          x
          #{x}))
      #{})))


(defn insert
  "Inserts one or more values into a MultiAssociative"
  [map key val & kvs]
  (let [ret (-insert map key val)]
    (if kvs
      (if (next kvs)
        (recur ret (first kvs) (second kvs) (nnext kvs))
        (throw (IllegalArgumentException.
                "insert expects even number of arguments after map, found odd number")))
      ret)))


(defn delete
  "Deletes one or more values from a MultiAssociative"
  [map key val & kvs]
  (let [ret (-delete map key val)]
    (if kvs
      (if (next kvs)
        (recur ret (first kvs) (second kvs) (nnext kvs))
        (throw (IllegalArgumentException.
                "delete expects even number of arguments after map, found odd number")))
      ret)))


(defn get-all
  "Returns a set of all values stored at key in a MultiAssociative.
   Returns the empty set if there are no values or not-found if provided."
  ([map key]
     (-get-all map key))
  ([map key not-found]
     (if (contains? map key)
       (-get-all map key)
       not-found)))
