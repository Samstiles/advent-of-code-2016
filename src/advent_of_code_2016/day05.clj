(ns advent-of-code-2016.day05
  (:require [digest :as d]
            [clojure.string :refer [starts-with? index-of join]]))

;; -- part 1

(defn run [input]
  (loop [password ""
         index 0]
    (if (= 8 (count password))
      password
      (let [hash (d/md5 (str input index))]
        (if (starts-with? hash "00000")
          (recur (str password (nth hash 5)) (inc index))
          (recur password (inc index)))))))

;; -- part 2

(defn char-to-number-or-nil [c]
  (let [alphabitz "abcdefghijklmnopqrstuvwxyz"]
    (when-not (index-of alphabitz c)
      (Integer. (str c)))))

(defn run-bonus [input]
  (loop [index 0
         found-letters {}]
    (if (= 8 (count (vals found-letters)))
      (join (vals (sort found-letters)))
      (let [hash (d/md5 (str input index))
            location (char-to-number-or-nil (nth hash 5))
            letter (str (nth hash 6))]
        (if (and (starts-with? hash "00000")
                 (= nil (get found-letters location))
                 (not= location nil)
                 (< location 8))
          (recur
           (inc index)
           (assoc found-letters location letter))
          (recur
           (inc index)
           found-letters))))))

;; (run-bonus "cxdnnyjw")
