(ns solution
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn parse-line
  [line]
  (mapv #(Integer/parseInt %) (str/split line #"\s+")))

(defn parse-tsv
  [filepath]
  (with-open [reader (io/reader filepath)]
    (doall (map parse-line (line-seq reader)))))

(defn calculate-pair-distance
  [total-dist pair]
  (+ total-dist (Math/abs (- (first pair) (second pair)))))

(defn count-occurrences
  [lst]
  (frequencies lst))

(defn accumulate-similarity
  [right-counts score num]
  (+ score (* num (get right-counts num 0))))

(defn calculate-similarity-score
  [left-list right-list]
  (let [right-counts (count-occurrences right-list)]
    (reduce (partial accumulate-similarity right-counts) 0 left-list)))

(defn calculate-total-distance
  [rows]
  (let [columns (apply map vector rows)
        sorted-cols (mapv sort columns)]
    (reduce calculate-pair-distance 0
            (map vector (first sorted-cols) (second sorted-cols)))))

(defn process-data
  [input-file]
  (let [rows (parse-tsv input-file)
        left-list (mapv first rows)
        right-list (mapv second rows)
        total-distance (calculate-total-distance rows)
        total-similarity (calculate-similarity-score left-list right-list)]
    {:total-distance total-distance
     :total-similarity total-similarity}))

(defn -main
  []
  (let [input-file "input"
        {:keys [total-distance total-similarity]} (process-data input-file)]
    (println total-distance)
    (println total-similarity)))

(-main)
