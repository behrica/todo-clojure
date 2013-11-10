(ns todo.core
  (:require
    [clojure.string :refer [split]]
    [clj-time.core :refer [date-time month year]])
)

(defstruct todo :uuid :title :date)



(defn- month-equal [todo a-month]
  (let [todo-date (:date todo)]
    (and
      (= (month todo-date) (month a-month))
      (= (year  todo-date) (year a-month))
      )
    )
)

(defn todos-in-month [todos month]
  (filter #(month-equal % month) todos)
)
