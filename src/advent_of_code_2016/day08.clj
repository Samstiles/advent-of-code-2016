(ns advent-of-code-2016.day08
  (:require [clojure.string :refer [split join]]
            [clojure.pprint :refer [pprint]]
            [clojure.tools.namespace.repl :refer [refresh]]))

(def board-height 6)
(def board-width 50)
(def board (vec (repeat board-height (vec (repeat board-width \_)))))

(defn draw [b]
  (doseq [row b]
    (println (join row))))

(defn rect [b w h]
  (reduce
   (fn [acc row]
     (if (> (+ row 1) h)
       (conj acc (nth b row))
       (conj acc (into (vec (repeat w \█))
                       (second (split-at w (nth b row)))))))
   []
   (range board-height)))

(defn do-shift [row amount]
  (into (vec (take-last amount row))
        (vec (drop-last amount row))))

(defn shift-row [b index amount]
  (reduce
   (fn [acc row]
     (if (= row index)
       (conj acc (do-shift (nth b index) amount))
       (conj acc (nth b row))))
   []
   (range board-height)))

(defn shift-col [b index amount]
  (let [updated-cols (do-shift
                      (map #(nth % index) b)
                      (mod amount board-height))]
    (vec (map-indexed (fn [idx row]
                        (assoc row index (nth updated-cols idx))) b))))

(defn run-instruction [b instruction]
  (let [parts (split instruction #" ")]
    (if (= (count parts) 2)
      (let [[_ size] (vec parts)
            [w h] (split size #"x")]
        (rect b (Integer. w) (Integer. h)))
      (let [[_ act idx _ amt] parts
            idx (second (split idx #"="))
            index (Integer. idx)
            action (keyword act)
            amount (Integer. amt)]
        (case action
          :row (shift-row b index amount)
          :column (shift-col b index amount))))))

(defn run [input]
  (let [instructions (split input #"\n")]
    (count (filter #(not= % \_) (flatten (reduce run-instruction board instructions))))))

(defn run-bonus [input]
  (let [instructions (split input #"\n")]
    (draw (reduce run-instruction board instructions))))
