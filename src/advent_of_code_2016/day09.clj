(ns advent-of-code-2016.day09
  (:require [clojure.string :refer [split index-of]]))

(defn run [input]
  (reduce
   (fn [i l]
     (if (= l \()
       (reduced i)
       (inc i)))
   0
   input))

;; (index-of "abcbad(f(g(a)))sdfg" \()

;; (run "ADVE(NT")

(defn run-bonus [input] input)


;; (map #(println (run %)) ["ADVENT" "A(1x5)BC" "(3x3)XYZ" "A(2x2)BCD(2x2)EFG" "(6x1)(1x3)A" "X(8x2)(3x3)ABCY"])
