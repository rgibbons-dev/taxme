(ns taxme.core 
  (:gen-class)
  (:require [clojure.java.io :as io]))

(defn read-stdin 
  "This function reads prompts the user for their total income."
  []
  (print "Enter your gross income for the tax year 2024: ")
  (flush)
  (let[input (io/reader *in*)]
   (.readLine input)))

; the tax brackets are defined in constants
(def positive-infinity Double/POSITIVE_INFINITY)
(def ^:const tax-brackets 
  "The 2024 U.S. Federal tax brackets."
  [{:from 0
    :upto 11000
    :rate 0.1}
   {:from 11001
    :upto 44725
    :rate 0.12}
   {:from 44726
    :upto 95375
    :rate 0.22}
   {:from 95376
    :upto 182100
    :rate 0.24}
   {:from 182101
    :upto 231250
    :rate 0.32}
   {:from 231251
    :upto 578125
    :rate 0.35}
   {:from 578126
    :upto positive-infinity
    :rate 0.37}])

(defn calculate-tax 
  "This function uses a reduction operation to calculate the tax owed
   given the amount of income passed as argument."
  [income]
  (reduce (fn [acc bracket]
            (let [lower (:from bracket)
                  upper (min (:upto bracket) income)
                  taxable (- (min income upper) (min income lower))]
              (+ acc (* taxable (:rate bracket)))))
          0
          tax-brackets))

(defn -main []
  (let [income (read-stdin)]
    (println "Your tax will be: "(calculate-tax (parse-double income)))))