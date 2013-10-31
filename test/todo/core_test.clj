(ns todo.core-test
  (:require [clojure.test :refer :all]
            [todo.core :refer :all])
  (:use     [midje.sweet :refer :all])
)

(def t1 (struct todo "work1" "01/01/2000"))
(def t2 (struct todo "work2" "01/01/2000"))
(def t3 (struct todo "work3" "02/01/2000"))
(def t4 (struct todo "work4" "02/02/2000"))

(fact
  (todos-in-month [t1] "07/2000") => []
  (todos-in-month [t1] "01/2000") => [t1]
  (todos-in-month [t1 t2] "01/2000") => [t1 t2]
  (todos-in-month [t1 t2 t3] "01/2000") => [t1 t2 t3]
  (todos-in-month [t1 t2 t3 t4] "01/2000") => [t1 t2 t3]
)
