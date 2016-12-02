(ns advent-of-code-2016.day01
  (:require [clojure.set :as set]))

;; -- shared fn's

(defn determine-new-facing [instruction-direction currently-facing]
  (case currently-facing
    :north (case instruction-direction \L :west \R :east)
    :south (case instruction-direction \L :east \R :west)
    :east (case instruction-direction \L :north \R :south)
    :west (case instruction-direction \L :south \R :north)))

(defn determine-new-x [old-x currently-facing instruction-direction travel-amount]
  (case currently-facing
    :north (case instruction-direction \L (- old-x travel-amount) \R (+ old-x travel-amount))
    :south (case instruction-direction \L (+ old-x travel-amount) \R (- old-x travel-amount))
    :east old-x
    :west old-x))

(defn determine-new-y [old-y currently-facing instruction-direction travel-amount]
  (case currently-facing
    :north old-y
    :south old-y
    :east (case instruction-direction \L (+ old-y travel-amount) \R (- old-y travel-amount))
    :west (case instruction-direction \L (- old-y travel-amount) \R (+ old-y travel-amount))))

;; -- part 1

(defn run
  [input]
  (println
    (reduce
      (fn [acc instruction]
        (let [{:keys [facing x y]} acc
              direction (first instruction)
              amount (Integer. (clojure.string/trim-newline (apply str (rest instruction))))]
          {:x (determine-new-x x facing direction amount)
           :y (determine-new-y y facing direction amount)
           :facing (determine-new-facing direction facing)}))
      {:x 0
       :y 0
       :facing :north}
      input)))

;; -- part 2

(defn determine-visited-grid-positions [facing current-x current-y instruction-direction travel-amount]
  (let [distance-bumps (map inc (range travel-amount))
        new-points (mapv (fn [distance-to-bump]
                          [(determine-new-x current-x facing instruction-direction distance-to-bump)
                           (determine-new-y current-y facing instruction-direction distance-to-bump)])
                        distance-bumps)]
    new-points))

(defn run-bonus
  [input]
  (let [visited (atom #{})]
    (println
       (reduce
         (fn [acc instruction]
           (let [{:keys [facing x y]} acc
                 direction (first instruction)
                 amount (Integer. (clojure.string/trim-newline (apply str (rest instruction))))
                 new-state {:x (determine-new-x x facing direction amount)
                            :y (determine-new-y y facing direction amount)
                            :facing (determine-new-facing direction facing)}
                 visited-coords-this-instruction (determine-visited-grid-positions facing x y direction amount)]
             (doseq [position visited-coords-this-instruction]
               (if (contains? @visited position)
                 (do (println "Found first double-visited grid position: " position)
                     (System/exit 0))
                 (swap! visited into [position])))
             new-state))
         {:x 0
          :y 0
          :facing :north}
         input))))
