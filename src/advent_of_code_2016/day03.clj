(ns advent-of-code-2016.day03)

;; -- part 1

(defn run [input]
  (->
   (sequence
    (comp
     (map sort)
     (filter (fn [[a b c]] (< c (+ a b)))))
    (partition 3 (read-string (str "[" input "]"))))
   (count)))

;; -- part 2

(defn run-bonus [input]
  (->
   (sequence
    (comp
     (map sort)
     (filter (fn [[a b c]] (< c (+ a b)))))
    (partition 3 (flatten (map #(partition 1 3 (drop % (read-string (str "[" input "]")))) (range 3)))))
   (count)))
