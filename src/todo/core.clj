(ns todo.core
  (:use [clojure.string :only [split]] [clj-time.core :only [date-time month year]])
)

(defstruct todo :title :date)



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
