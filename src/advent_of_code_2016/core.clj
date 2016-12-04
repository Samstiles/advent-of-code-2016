(ns advent-of-code-2016.core
  (:require [advent-of-code-2016.day01 :as day01]
            [advent-of-code-2016.day02 :as day02]
            [advent-of-code-2016.day03 :as day03])
  (:gen-class))

(defn -main
  [& args]
  (try
    (let [input (slurp "inputs/03.txt")]
      (day03/run-bonus input))
    (catch Throwable e (println "sad times") (println e))))
