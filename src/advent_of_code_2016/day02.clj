(ns advent-of-code-2016.day02)

;; -- part 1

(def noops {1 #{\U \L}
            2 #{\U}
            3 #{\U \R}
            4 #{\L}
            5 #{}
            6 #{\R}
            7 #{\D \L}
            8 #{\D}
            9 #{\D \R}})

(defn run-ins [acc ins]
  (let [moves (seq (char-array ins))
        pos (atom (:pos acc))
        ending-position (do (doseq [move moves]
                             (if (contains? (get noops @pos) move)
                              (reset! pos @pos)
                              (do
                               (case (str move)
                                 "U" (swap! pos - 3)
                                 "D" (swap! pos + 3)
                                 "L" (swap! pos - 1)
                                 "R" (swap! pos + 1)))))
                            @pos)]
    (assoc acc :pos ending-position
               :numbaz (conj (:numbaz acc) ending-position))))

(defn run
  [input]
  (let [instructions (clojure.string/split input #"\n")]
    (println
      (reverse (:numbaz (reduce
                          run-ins
                          {:pos 5}
                          instructions))))))

;; -- part 2

(def noops2 {3 #{\U \L \R}
             7 #{\L \U}
             9 #{\R \U}
             11 #{\U \D \L}
             15 #{\R \D \U}
             17 #{\L \D}
             19 #{\R \D}
             23 #{\D \L \R}})

(defn run-ins2 [acc ins]
  (let [moves (seq (char-array ins))
        pos (atom (:pos acc))
        ending-position (do (doseq [move moves]
                             (if (contains? (get noops2 @pos) move)
                              (reset! pos @pos)
                              (do
                               (case (str move)
                                 "U" (swap! pos - 5)
                                 "D" (swap! pos + 5)
                                 "L" (swap! pos - 1)
                                 "R" (swap! pos + 1)))))
                            @pos)]
    (assoc acc :pos ending-position
               :numbaz (conj (:numbaz acc) ending-position))))

(defn run-bonus
  [input]
  (let [instructions (clojure.string/split input #"\n")]
    (println
      (map #(case %
             3 1
             7 2
             8 3
             9 4
             11 5
             12 6
             13 7
             14 8
             15 9
             17 \A
             18 \B
             19 \C
             23 \D)
        (reverse (:numbaz (reduce
                           run-ins2
                           {:pos 11}
                           instructions)))))))
