(ns advent-of-code-2016.core
  (:require [advent-of-code-2016.day01 :as day01]
            [advent-of-code-2016.day02 :as day02]
            [advent-of-code-2016.day03 :as day03]
            [advent-of-code-2016.day04 :as day04]
            [advent-of-code-2016.day05 :as day05]
            [advent-of-code-2016.day06 :as day06]
            [advent-of-code-2016.day07 :as day07])
  (:gen-class))

(defn -main
  [& args]
  (try
    (let [input (slurp "inputs/07.txt")]
      (day07/run input))
    (catch Throwable e (println "sad times") (println e))))

;; (-main)
