(ns todo.core
  (:use [clojure.string :only [split]])
)

(defstruct todo :title :date)

(defn- split-date [date pos]
  (nth (split date #"/") pos)

)

(defn- month-from-date [date]
  (split-date date 1)
)

(defn- year-from-date [date]
  (split-date date 2)
)

(defn- month-from-month [date]
  (split-date date 0)
)

(defn- year-from-month [date]
  (split-date date 1)
)


(defn- month-equal [todo month]
  (let [todo-date (:date todo)]
    (and
      (= (month-from-date todo-date) (month-from-month month))
      (= (year-from-date  todo-date) (year-from-month month))
      )
    )
)

(defn todos-in-month [todos month]
  (filter #(month-equal % month) todos)
)
