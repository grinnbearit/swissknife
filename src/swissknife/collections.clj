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
  [priority-fn & xs]
  (-> (PersistentPriorityQueue. priority-fn (sorted-map))
      (into xs)))
