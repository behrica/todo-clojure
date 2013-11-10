(ns todo.core-test
  (:require [clojure.test :refer :all]
            [todo.core :refer :all]
             [midje.sweet :refer :all]
            [clj-time.core :refer [date-time]]))

(def t1 (struct todo "work1" (date-time 2000 1 1)))
(def t2 (struct todo "work2" (date-time 2000 1 1)))
(def t3 (struct todo "work3" (date-time 2000 1 2)))
(def t4 (struct todo "work4" (date-time 2000 2 2)))
(def m1 (date-time 2000 1 ))

(fact
  (todos-in-month [t1] (date-time 2000 7 )) => []
  (todos-in-month [t1] (date-time 2000 1 )) => [t1]
  (todos-in-month [t1 t2] m1) => [t1 t2]
  (todos-in-month [t1 t2 t3] m1) => [t1 t2 t3]
  (todos-in-month [t1 t2 t3 t4] m1) => [t1 t2 t3]
  (todos-in-month [] m1) => []
)

