(ns advent-of-code-2016.day06
  (:require [clojure.string :refer [split]]))

;; -- part 1

(defn run [input]
  (map
   (fn [m] (first (first (reverse (sort-by val m)))))
   (vals (reduce
          (fn [acc word]
            (loop [acc acc
                   index 0]
              (if (= index (count word))
                acc
                (let [letter (str (nth word index))]
                  (if-let [prev-val (get-in acc [index letter])]
                    (recur (assoc-in acc [index letter] (inc prev-val))
                           (inc index))
                    (recur (assoc-in acc [index letter] 1)
                           (inc index)))))))
          {}
          (split input #"\n")))))

;; -- part 2

(defn run-bonus [input]
  (map
   (fn [m] (first (first (sort-by val m))))
   (vals (reduce
          (fn [acc word]
            (loop [acc acc
                   index 0]
              (if (= index (count word))
                acc
                (let [letter (str (nth word index))]
                  (if-let [prev-val (get-in acc [index letter])]
                    (recur (assoc-in acc [index letter] (inc prev-val))
                           (inc index))
                    (recur (assoc-in acc [index letter] 1)
                           (inc index)))))))
          {}
          (split input #"\n")))))
