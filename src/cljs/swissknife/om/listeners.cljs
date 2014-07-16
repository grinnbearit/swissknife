(ns swissknife.om.listeners
  (:refer-clojure :exclude [integer?])
  (:require [om.core :as om :include-macros true]))


(defn handler
  [e]
  {:valid? true :value (.. e -target -value)})


(defn commit!
  [handler app key]
  (fn [e]
    (om/update! app key (handler e))))


(defn before
  [handler task]
  (fn [e]
    (task)
    (handler e)))


(defn after
  [handler task]
  (fn [e]
    (handler e)
    (task)))


(defn validator
  [valid? default-error]
  (fn [handler & {:keys [error] :or {error default-error}}]
    (fn [e]
      (let [res (handler e)]
        (if (valid? (:value res))
          (assoc-in res [:error error] false)
          (-> (assoc-in res [:error error] true)
              (assoc :valid? false)))))))


(defn validate-and-parser
  [valid? parser default-error]
  (fn [handler & {:keys [parse? error] :or {parse? true error default-error}}]
    (fn [e]
      (let [res (handler e)]
        (if (valid? (:value res))
          (cond-> (assoc-in res [:error error] false)

                  parse?
                  (update-in [:value] parser))
          (-> (assoc-in res [:error error] true)
              (assoc :valid? false)))))))


(def required?
  (validator seq :required?))


(defn matches?
  [handler pattern & {:keys [error] :or {error :matches?}}]
  (fn [e]
    (let [res (handler e)]
      (if (re-matches pattern (:value res))
        (assoc-in res [:error error] false)
        (-> (assoc-in res [:error error] true)
            (assoc :valid? false))))))


(def email?
  (validator (partial re-matches #"(?i)^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$") :email?))


(def integer?
  (validate-and-parser (partial re-matches #"(\+|-)?\d+") js/parseInt :integer?))


(def rational?
  (validate-and-parser (partial re-matches #"(\+|-)?\d+(\.\d+)?") js/parseFloat :rational?))


(def positive?
  (validator pos? :positive?))


(def negative?
  (validator neg? :negative?))


(def not-negative?
  (validator (comp not neg?) :not-negative?))


(def not-positive?
  (validator (comp not pos?) :not-positive?))


(def probability?
  (validator #(<= 0 % 1) :probability?))
