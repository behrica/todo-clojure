(ns todo.core-test
  (:require [todo.core :refer :all]
            [midje.checkers :refer [throws]]
            [midje.sweet :refer [fact]]
            [clj-time.core :refer [date-time]]))

(def t1 (new-todo "work1" (date-time 2000 1 1) "1"))
(def t2 (new-todo "work2" (date-time 2000 1 1) "2"))
(def t3 (new-todo "work3" (date-time 2000 1 2) "3"))
(def t4 (new-todo "work4" (date-time 2000 2 2) "4"))
(def m1 (date-time 2000 1 ))

(fact
  (todos-in-month [t1] (date-time 2000 7 )) => []
  (todos-in-month [t1] (date-time 2000 1 )) => [t1]
  (todos-in-month [t1 t2] m1) => [t1 t2]
  (todos-in-month [t1 t2 t3] m1) => [t1 t2 t3]
  (todos-in-month [t1 t2 t3 t4] m1) => [t1 t2 t3]
  (todos-in-month [] m1) => [])

(fact "new-todo allows only date"
  (new-todo "" "10/2/2012" "1") => (throws AssertionError)
  (:date (new-todo "" (date-time 2012 1 1) "1")) =>  (date-time 2012 1 1))
